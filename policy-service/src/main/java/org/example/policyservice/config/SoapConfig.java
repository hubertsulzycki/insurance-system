package org.example.policyservice.config;

import org.example.policyservice.adapter.FlightClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // Wskazujemy pakiet, w którym są klasy Request/Response
        marshaller.setPackagesToScan("org.example.policyservice.model");
        return marshaller;
    }

    @Bean
    public FlightClient flightClient(Jaxb2Marshaller marshaller) {
        FlightClient client = new FlightClient();
        client.setDefaultUri("http://localhost:8081/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}