package com.internaltools.demo1.controllers;

import com.internaltools.demo1.entities.UsageLogs;
import com.internaltools.demo1.services.UsageLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usage-logs")
@RequiredArgsConstructor
public class UsageLogsController {

    private final UsageLogsService service;

    // ✅ GET tous les logs
    @GetMapping
    public List<UsageLogs> getAllLogs() {
        return service.getAllLogs();
    }

    // ✅ GET un log par ID
    @GetMapping("/{id}")
    public ResponseEntity<UsageLogs> getLogById(@PathVariable Integer id) {
        return service.getLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST créer un log
    @PostMapping
    public ResponseEntity<UsageLogs> createLog(@RequestBody UsageLogs log) {
        UsageLogs saved = service.createLog(log);
        return ResponseEntity.ok(saved);
    }

    // ✅ PUT mettre à jour un log
    @PutMapping("/{id}")
    public ResponseEntity<UsageLogs> updateLog(
            @PathVariable Integer id,
            @RequestBody UsageLogs updatedLog
    ) {
        return service.updateLog(id, updatedLog)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE supprimer un log
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Integer id) {
        if (service.deleteLog(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ GET tous les logs d’un utilisateur
    @GetMapping("/user/{userId}")
    public List<UsageLogs> getLogsByUser(@PathVariable Long userId) {
        return service.getLogsByUser(userId);
    }

    // ✅ GET tous les logs d’un outil
    @GetMapping("/tool/{toolId}")
    public List<UsageLogs> getLogsByTool(@PathVariable Integer toolId) {
        return service.getLogsByTool(toolId);
    }
}
