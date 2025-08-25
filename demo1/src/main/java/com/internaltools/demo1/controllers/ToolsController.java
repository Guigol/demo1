package com.internaltools.demo1.controllers;

import com.internaltools.demo1.dtos.ToolCreateDto;
import com.internaltools.demo1.dtos.ToolDto;
import com.internaltools.demo1.entities.Tools;
import com.internaltools.demo1.services.ToolsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
@Tag(name = "Tools", description = "API pour gérer les outils internes") // ✅ Regroupe tous les endpoints dans Swagger
public class ToolsController {

    private final ToolsService service;

    // ✅ GET all tools avec filtres
    @GetMapping
    @Operation(
            summary = "Récupère tous les outils",
            description = "Permet de filtrer par département, status, coût et catégorie"
    )
    public ResponseEntity<Map<String, Object>> getAllTools(
            @RequestParam(required = false)
            @Parameter(description = "Filtre par département") String department,

            @RequestParam(required = false)
            @Parameter(description = "Filtre par status") String status,

            @RequestParam(required = false)
            @Parameter(description = "Coût minimum mensuel") BigDecimal minCost,

            @RequestParam(required = false)
            @Parameter(description = "Coût maximum mensuel") BigDecimal maxCost,

            @RequestParam(required = false, name = "category")
            @Parameter(description = "Filtre par nom de catégorie") String categoryName) {

        System.out.println("➡ Controller reçoit : category=" + categoryName +
                ", department=" + department +
                ", status=" + status +
                ", min=" + minCost +
                ", max=" + maxCost);

        List<Tools> allTools = service.getAllTools(department, status, minCost, maxCost, categoryName);

        List<ToolDto> toolDtos = allTools.stream().map(t -> {
            BigDecimal totalMonthlyCost = t.getMonthlyCost()
                    .multiply(BigDecimal.valueOf(t.getActiveUsersCount() != null ? t.getActiveUsersCount() : 0));

            Map<String, Object> last30Days = Map.of(
                    "total_sessions", 0,
                    "avg_session_minutes", 0
            );
            Map<String, Map<String, Object>> usageMetrics = Map.of("last_30_days", last30Days);

            return new ToolDto(
                    t.getId(),
                    t.getName(),
                    t.getVendor(),
                    t.getCategory() != null ? t.getCategory().getName() : null,
                    t.getDescription(),
                    t.getWebsiteUrl(),
                    t.getMonthlyCost(),
                    t.getOwnerDepartment() != null ? t.getOwnerDepartment().name() : null,
                    t.getStatus() != null ? t.getStatus().name() : null,
                    t.getActiveUsersCount(),
                    totalMonthlyCost,
                    t.getCreatedAt(),
                    t.getUpdatedAt(),
                    usageMetrics
            );
        }).toList();

        Map<String, Object> filtersApplied = new HashMap<>();
        if (department != null) filtersApplied.put("department", department);
        if (status != null) filtersApplied.put("status", status);
        if (minCost != null) filtersApplied.put("min_cost", minCost);
        if (maxCost != null) filtersApplied.put("max_cost", maxCost);
        if (categoryName != null) filtersApplied.put("category", categoryName);

        Map<String, Object> response = new HashMap<>();
        response.put("data", toolDtos);
        response.put("total", allTools.size());
        response.put("filtered", toolDtos.size());
        response.put("filters_applied", filtersApplied);

        return ResponseEntity.ok(response);
    }

    // ✅ GET by ID enrichi
    @GetMapping("/{id}")
    @Operation(
            summary = "Récupère un outil par son ID",
            description = "Renvoie le DTO complet avec métriques fictives"
    )
    public ResponseEntity<ToolDto> getToolById(
            @PathVariable
            @Parameter(description = "ID de l'outil à récupérer") Integer id) {

        return service.getToolById(id)
                .map(tool -> {
                    BigDecimal totalMonthlyCost = tool.getMonthlyCost()
                            .multiply(BigDecimal.valueOf(tool.getActiveUsersCount() != null ? tool.getActiveUsersCount() : 0));

                    Map<String, Object> last30Days = Map.of(
                            "total_sessions", 127,
                            "avg_session_minutes", 45
                    );
                    Map<String, Map<String, Object>> usageMetrics = Map.of(
                            "last_30_days", last30Days
                    );

                    return new ToolDto(
                            tool.getId(),
                            tool.getName(),
                            tool.getVendor(),
                            tool.getCategory() != null ? tool.getCategory().getName() : null,
                            tool.getDescription(),
                            tool.getWebsiteUrl(),
                            tool.getMonthlyCost(),
                            tool.getOwnerDepartment() != null ? tool.getOwnerDepartment().name() : null,
                            tool.getStatus() != null ? tool.getStatus().name() : null,
                            tool.getActiveUsersCount(),
                            totalMonthlyCost,
                            tool.getCreatedAt(),
                            tool.getUpdatedAt(),
                            usageMetrics
                    );
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST créer un outil
    @PostMapping
    @Operation(
            summary = "Crée un nouvel outil",
            description = "Reçoit un DTO de création et retourne l'outil enregistré"
    )
    public ResponseEntity<Tools> createTool(
            @RequestBody
            @Parameter(description = "DTO de création de l'outil") ToolCreateDto dto) {

        System.out.println("CategoryId reçu : " + dto.getCategoryId());
        Tools saved = service.createTool(dto);
        return ResponseEntity.ok(saved);
    }

    // ✅ PUT update
    @PutMapping("/{id}")
    @Operation(
            summary = "Met à jour un outil existant",
            description = "Reçoit l'ID et le DTO de mise à jour et retourne le DTO mis à jour"
    )
    public ResponseEntity<ToolDto> updateTool(
            @PathVariable
            @Parameter(description = "ID de l'outil à mettre à jour") Integer id,
            @RequestBody
            @Parameter(description = "DTO de mise à jour") ToolCreateDto dto) {

        return service.updateTool(id, dto)
                .map(tool -> {
                    BigDecimal totalMonthlyCost = tool.getMonthlyCost()
                            .multiply(BigDecimal.valueOf(tool.getActiveUsersCount() != null ? tool.getActiveUsersCount() : 0));

                    Map<String, Object> last30Days = Map.of(
                            "total_sessions", 127,
                            "avg_session_minutes", 45
                    );
                    Map<String, Map<String, Object>> usageMetrics = Map.of("last_30_days", last30Days);

                    return new ToolDto(
                            tool.getId(),
                            tool.getName(),
                            tool.getVendor(),
                            tool.getCategory() != null ? tool.getCategory().getName() : null,
                            tool.getDescription(),
                            tool.getWebsiteUrl(),
                            tool.getMonthlyCost(),
                            tool.getOwnerDepartment() != null ? tool.getOwnerDepartment().name() : null,
                            tool.getStatus() != null ? tool.getStatus().name() : null,
                            tool.getActiveUsersCount(),
                            totalMonthlyCost,
                            tool.getCreatedAt(),
                            tool.getUpdatedAt(),
                            usageMetrics
                    );
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprime un outil par son ID",
            description = "Retourne 204 si suppression réussie, 404 sinon"
    )
    public ResponseEntity<Void> deleteTool(
            @PathVariable
            @Parameter(description = "ID de l'outil à supprimer") Integer id) {

        if (service.deleteTool(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
