package org.example.policyservice.service;

import org.example.policyservice.adapter.FlightClient;
import org.example.policyservice.model.PolicyStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PolicyService {

    private final FlightClient flightClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String resultTopic;

    // Konstruktor wstrzykuje zależności
    public PolicyService(FlightClient flightClient,
                         KafkaTemplate<String, Object> kafkaTemplate,
                         @Value("${insurance.kafka.topic.policy-result}") String resultTopic) {
        this.flightClient = flightClient;
        this.kafkaTemplate = kafkaTemplate;
        this.resultTopic = resultTopic;
    }

    // Metoda biznesowa - czysta i czytelna
    public void processPolicyRequest(Long policyId, String flightNumber) {
        System.out.println(">>> [Service] Otrzymano płatność. Weryfikacja lotu: " + flightNumber);

        // Uderzamy do SOAP
        boolean flightExists = flightClient.checkFlight(flightNumber);

        // Decyzja
        PolicyStatus status = flightExists ? PolicyStatus.APPROVED : PolicyStatus.REJECTED;

        // Budujemy zdarzenie zwrotne (PolicyCreated lub PolicyRejected)
        Map<String, Object> result = new HashMap<>();
        result.put("policyId", policyId);
        result.put("status", status);

        // Wysyłamy wynik, który zadecyduje o losie pieniędzy
        kafkaTemplate.send(resultTopic, String.valueOf(policyId), result);
    }
}