package com.internaltools.demo1.controllers;

import com.internaltools.demo1.entities.AccessRequests;
import com.internaltools.demo1.services.AccessRequestsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access-requests")
@RequiredArgsConstructor
public class AccessRequestsController {

    private final AccessRequestsService accessRequestsService;

    // ✅ GET toutes les demandes
    @GetMapping
    public List<AccessRequests> getAllRequests() {
        return accessRequestsService.getAllRequests();
    }

    // ✅ GET demandes d’un utilisateur
    @GetMapping("/user/{userId}")
    public List<AccessRequests> getRequestsByUser(@PathVariable Long userId) {
        return accessRequestsService.getRequestsByUser(userId);
    }

    // ✅ POST créer une nouvelle demande
    @PostMapping
    public ResponseEntity<AccessRequests> createRequest(@RequestBody AccessRequests request) {
        AccessRequests saved = accessRequestsService.createRequest(request);
        return ResponseEntity.ok(saved);
    }

    // ✅ POST approuver une demande
    @PostMapping("/{requestId}/approve")
    public ResponseEntity<AccessRequests> approveRequest(
            @PathVariable Long requestId,
            @RequestParam Long processedBy,
            @RequestParam(required = false) String notes
    ) {
        return accessRequestsService.approveRequest(requestId, processedBy, notes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST rejeter une demande
    @PostMapping("/{requestId}/reject")
    public ResponseEntity<AccessRequests> rejectRequest(
            @PathVariable Long requestId,
            @RequestParam Long processedBy,
            @RequestParam(required = false) String notes
    ) {
        return accessRequestsService.rejectRequest(requestId, processedBy, notes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
