package org.example.salesservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.salesservice.model.PolicyStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;

@Component
public class SalesResultListener {

    private final SalesService salesService;

    public SalesResultListener(SalesService salesService) {
        this.salesService = salesService;
    }

    @KafkaListener(topics = "${insurance.kafka.topic.policy-result}", groupId = "sales-group")
    public void handleResult(ConsumerRecord<String, Object> record) {
        LinkedHashMap<?, ?> payload = (LinkedHashMap<?, ?>) record.value();

        // Odbieramy dane (musimy rzutować na odpowiednie typy)
        Integer idInteger = (Integer) payload.get("policyId");
        Long policyId = idInteger.longValue();
        String status = (String) payload.get("status");

        // Logika Sagi
        if ("APPROVED".equals(status)) {
            // Ścieżka pozytywna
            salesService.updateStatus(policyId, PolicyStatus.COMPLETED);
            System.out.println("✅ Polisa aktywna. Proces zakończony pomyślnie.");
        } else if ("REJECTED".equals(status)) {
            // Ścieżka negatywna (KOMPENSACJA)
            // Musimy "zwrócić pieniądze", bo wcześniej ustawiliśmy PAID
            System.out.println("⚠️ [Sales] Lot odrzucony. Uruchamiam procedurę zwrotu.");
            salesService.refundPayment(policyId);
        }
    }
}