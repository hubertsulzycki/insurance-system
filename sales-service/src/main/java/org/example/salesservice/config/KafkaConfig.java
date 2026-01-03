package org.example.salesservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    // Wczytujemy nazwę tematu z application.properties
    @Value("${insurance.kafka.topic.policy-request}")
    private String topicName;

    @Bean
    public NewTopic createPolicyRequestTopic() {
        // Tworzy temat:
        // name: nazwa z pliku properties
        // partitions: 1 (dla prostoty)
        // replicas: 1 (bo mamy tylko 1 brokera Kafki)
        return TopicBuilder.name(topicName)
                .partitions(1)
                .replicas(1)
                .build();
    }
}