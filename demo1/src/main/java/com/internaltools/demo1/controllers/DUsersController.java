package com.internaltools.demo1.controllers;

import com.internaltools.demo1.entities.DUsers;
import com.internaltools.demo1.services.DUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class DUsersController {

    private final DUsersService service;

    // ✅ GET tous les utilisateurs
    @GetMapping
    public List<DUsers> getAllUsers() {
        return service.getAllUsers();
    }

    // ✅ GET un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<DUsers> getUserById(@PathVariable Long id) {
        return service.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST créer un nouvel utilisateur
    @PostMapping
    public ResponseEntity<DUsers> createUser(@RequestBody DUsers user) {
        DUsers saved = service.createUser(user);
        return ResponseEntity.ok(saved);
    }

    // ✅ PUT mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<DUsers> updateUser(
            @PathVariable Long id,
            @RequestBody DUsers updatedUser
    ) {
        return service.updateUser(id, updatedUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (service.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
