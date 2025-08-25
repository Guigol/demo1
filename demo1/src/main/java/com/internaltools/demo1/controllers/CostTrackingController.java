package com.internaltools.demo1.controllers;

import com.internaltools.demo1.entities.CostTracking;
import com.internaltools.demo1.services.CostTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cost-tracking")
@RequiredArgsConstructor
public class CostTrackingController {

    private final CostTrackingService service;

    // ✅ Récupérer toutes les entrées
    @GetMapping
    public List<CostTracking> getAllCosts() {
        return service.getAllCosts();
    }

    // ✅ Récupérer une entrée par ID
    @GetMapping("/{id}")
    public ResponseEntity<CostTracking> getCostById(@PathVariable Integer id) {
        return service.getCostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Créer une nouvelle entrée
    @PostMapping
    public ResponseEntity<CostTracking> createCost(@RequestBody CostTracking cost) {
        CostTracking saved = service.createCost(cost);
        return ResponseEntity.ok(saved);
    }

    // ✅ Mettre à jour une entrée
    @PutMapping("/{id}")
    public ResponseEntity<CostTracking> updateCost(
            @PathVariable Integer id,
            @RequestBody CostTracking updatedCost
    ) {
        return service.updateCost(id, updatedCost)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Supprimer une entrée
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCost(@PathVariable Integer id) {
        if (service.deleteCost(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
