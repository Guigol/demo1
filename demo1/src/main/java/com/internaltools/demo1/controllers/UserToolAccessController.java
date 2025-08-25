package com.internaltools.demo1.controllers;

// package com.internaltools.demo1.controllers;

import com.internaltools.demo1.entities.UserToolAccess;
import com.internaltools.demo1.repositories.UserToolAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/access")
@RequiredArgsConstructor
public class UserToolAccessController {

    private final UserToolAccessRepository repository;

    // ✅ Récupérer tous les accès
    @GetMapping
    public List<UserToolAccess> getAllAccesses() {
        return repository.findAll();
    }

    // ✅ Récupérer un accès par son ID
    @GetMapping("/{id}")
    public ResponseEntity<UserToolAccess> getAccessById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Créer un accès
    @PostMapping
    public UserToolAccess createAccess(@RequestBody UserToolAccess access) {
        return repository.save(access);
    }

    // ✅ Mettre à jour un accès
    @PutMapping("/{id}")
    public ResponseEntity<UserToolAccess> updateAccess(
            @PathVariable Integer id,
            @RequestBody UserToolAccess updated
    ) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setStatus(updated.getStatus());
                    existing.setRevokedAt(updated.getRevokedAt());
                    existing.setRevokedBy(updated.getRevokedBy());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Supprimer un accès
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccess(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Récupérer uniquement les accès ACTIFS et REVOQUES
    @GetMapping("/active")
    public List<UserToolAccess> getActiveAccesses() {
        return repository.findByStatus(UserToolAccess.Status.ACTIVE);
    }

    @GetMapping("/revoked")
    public List<UserToolAccess> getRevokeAccesses() {
        return repository.findByStatus(UserToolAccess.Status.REVOKED);
    }
}
