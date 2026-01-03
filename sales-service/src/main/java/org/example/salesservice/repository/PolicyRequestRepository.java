package org.example.salesservice.repository;

import org.example.salesservice.model.PolicyRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRequestRepository extends JpaRepository<PolicyRequest, Long> {
}