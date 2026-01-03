package org.example.salesservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Data
public class PolicyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String flightNumber;
    @Enumerated(EnumType.STRING)
    private PolicyStatus status;
}