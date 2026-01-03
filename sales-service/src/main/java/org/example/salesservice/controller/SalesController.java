package org.example.salesservice.controller;

import org.example.salesservice.model.PolicyRequest;
import org.example.salesservice.service.SalesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    // Prosty DTO do przyjęcia danych z JSON-a
    record CreatePolicyRequest(String customerName, String flightNumber) {}

    @PostMapping
    public PolicyRequest createPolicy(@RequestBody CreatePolicyRequest request) {
        return salesService.createPolicy(request.customerName(), request.flightNumber());
    }

    @GetMapping("/{id}")
    public PolicyRequest getPolicyStatus(@PathVariable Long id) {
        return salesService.getPolicyDetails(id);
    }
}