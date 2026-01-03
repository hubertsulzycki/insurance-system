package org.example.policyservice.model;

public enum PolicyStatus {
    NEW,            // Nowe zgłoszenie
    PAYMENT_FAILED, // ❌ Płatność odrzucona (Twoje 30%)
    PAID,           // 💰 Opłacone (Czekamy na weryfikację lotu)
    APPROVED,       // Lot istnieje (Info z PolicyService)
    REJECTED,       // Lot nie istnieje (Info z PolicyService)
    COMPLETED,      // ✅ Sukces (Mamy kasę i lot)
    REFUNDED        // 💸 Zwrot środków (Kompensacja)
}
