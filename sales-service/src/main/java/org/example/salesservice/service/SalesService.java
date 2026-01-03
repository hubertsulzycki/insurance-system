package org.example.salesservice.service;

import org.example.salesservice.model.PolicyRequest;
import org.example.salesservice.model.PolicyStatus;
import org.example.salesservice.repository.PolicyRequestRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class SalesService {

    private final PolicyRequestRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topicName;
    private final Random random = new Random();

    // Wstrzykujemy repozytorium, kafkę i nazwę tematu z properties
    public SalesService(PolicyRequestRepository repository,
                        KafkaTemplate<String, Object> kafkaTemplate,
                        @Value("${insurance.kafka.topic.policy-request}") String topicName) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Transactional
    public PolicyRequest createPolicy(String customerName, String flightNumber) {
        // 1. Rejestracja wniosku (Status NEW)
        PolicyRequest request = new PolicyRequest();
        request.setCustomerName(customerName);
        request.setFlightNumber(flightNumber);
        request.setStatus(PolicyStatus.NEW);
        PolicyRequest savedRequest = repository.save(request);

        System.out.println(">>> [Sales] Otrzymano zamówienie ID: " + savedRequest.getId());

        // 2. Symulacja Płatności (Fail Fast)
        if (shouldFailPayment()) {
            savedRequest.setStatus(PolicyStatus.PAYMENT_FAILED);
            repository.save(savedRequest);
            System.out.println("❌ [Sales] Płatność odrzucona dla ID: " + savedRequest.getId());
            // WAŻNE: Przerywamy Sagę. Nie wysyłamy nic na Kafkę.
            return savedRequest;
        }

        // 3. Płatność OK -> Status PAID -> Wyślij do Policy Service
        savedRequest.setStatus(PolicyStatus.PAID);
        repository.save(savedRequest); // Zapisz "zabrano pieniądze"

        // Wysyłamy ID i numer lotu
        kafkaTemplate.send(topicName, String.valueOf(savedRequest.getId()), savedRequest);

        System.out.println("💰 [Sales] Płatność przyjęta. Wysłano do weryfikacji ID: " + savedRequest.getId());
        return savedRequest;
    }

    // Metoda pomocnicza do aktualizacji statusu (używana przez Listener)
    @Transactional
    public void updateStatus(Long policyId, PolicyStatus newStatus) {
        repository.findById(policyId).ifPresent(policy -> {
            policy.setStatus(newStatus);
            repository.save(policy);
            System.out.println("📝 [Sales] Aktualizacja statusu ID " + policyId + " -> " + newStatus);
        });
    }

    // KOMPENSACJA: Zwrot środków
    @Transactional
    public void refundPayment(Long policyId) {
        repository.findById(policyId).ifPresent(policy -> {
            // Sprawdzamy, czy faktycznie pobraliśmy kasę
            if (policy.getStatus() == PolicyStatus.PAID) {
                policy.setStatus(PolicyStatus.REFUNDED);
                repository.save(policy);
                System.out.println("💸 [Sales] KOMPENSACJA: Zwrócono środki za wniosek ID: " + policyId);
            }
        });
    }

    // Logika losowania (30% szans na błąd)
    private boolean shouldFailPayment() {
        return random.nextInt(100) < 30; // Zwraca true w 30% przypadków
    }

    public PolicyRequest getPolicyDetails(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono polisy o ID: " + id));
    }
}