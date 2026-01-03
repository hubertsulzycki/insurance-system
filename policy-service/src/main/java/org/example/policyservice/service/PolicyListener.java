package org.example.policyservice.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;

@Service
public class PolicyListener {

    private final PolicyService policyService;

    public PolicyListener(PolicyService policyService) {
        this.policyService = policyService;
    }

    @KafkaListener(topics = "${insurance.kafka.topic.policy-request}", groupId = "policy-group")
    public void handlePolicyRequest(ConsumerRecord<String, Object> record) {
        LinkedHashMap<?, ?> payload = (LinkedHashMap<?, ?>) record.value();

        // 1. Ekstrakcja danych (rola Listenera)
        String flightNumber = (String) payload.get("flightNumber");

        // Bezpieczne rzutowanie ID (jak ustaliliśmy wcześniej)
        Number idNumber = (Number) payload.get("id");
        Long policyId = idNumber.longValue();

        // 2. Delegacja zadania do serwisu (To jest ta zmiana!)
        policyService.processPolicyRequest(policyId, flightNumber);
    }
}