package com.internaltools.demo1.services;

import com.internaltools.demo1.dtos.ToolCreateDto;
import com.internaltools.demo1.entities.Categories;
import com.internaltools.demo1.entities.Tools;
import com.internaltools.demo1.repositories.CategoriesRepository;
import com.internaltools.demo1.repositories.ToolsRepository;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToolsService {

    private final ToolsRepository toolsRepository;
    private final CategoriesRepository categoriesRepository;

    // ✅ GET tous les outils avec filtres optionnels
    public List<Tools> getAllTools(String department,
                                   String status,
                                   BigDecimal minCost,
                                   BigDecimal maxCost,
                                   String categoryName) {

        Specification<Tools> spec = Specification.allOf(); // équivalent à "aucun filtre"

        if (department != null && !department.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("ownerDepartment"), Tools.OwnerDepartment.valueOf(department.toUpperCase())));
        }

        if (status != null && !status.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), Tools.Status.valueOf(status.toUpperCase())));
        }

        if (minCost != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("monthlyCost"), minCost));
        }

        if (maxCost != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("monthlyCost"), maxCost));
        }

        if (categoryName != null && !categoryName.isEmpty()) {
            System.out.println("Filtre catégorie demandé : " + categoryName);
            spec = spec.and((root, query, cb) -> {
                Join<Tools, Categories> categoryJoin = root.join("category");
                return cb.equal(cb.lower(categoryJoin.get("name")), categoryName.toLowerCase());
            });
        }

        return toolsRepository.findAll(spec);
    }

    // ✅ GET un outil par ID
    public Optional<Tools> getToolById(Integer id) {
        return toolsRepository.findById(id);
    }

    // ✅ POST créer un outil
    public Tools createTool(ToolCreateDto dto) {
        Categories category = categoriesRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));

        Tools tool = Tools.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .vendor(dto.getVendor())
                .websiteUrl(dto.getWebsiteUrl())
                .category(category)
                .monthlyCost(dto.getMonthlyCost())
                .activeUsersCount(dto.getActiveUsersCount() != null ? dto.getActiveUsersCount() : 0)
                .ownerDepartment(dto.getOwnerDepartment())
                .status(dto.getStatus() != null ? dto.getStatus() : Tools.Status.ACTIVE)
                .build();

        return toolsRepository.save(tool);
    }

    // ✅ PUT mettre à jour un outil
    public Optional<Tools> updateTool(Integer id, ToolCreateDto dto) {
        return toolsRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setDescription(dto.getDescription());
                    existing.setVendor(dto.getVendor());
                    existing.setWebsiteUrl(dto.getWebsiteUrl());

                    Categories category = categoriesRepository.findById(dto.getCategoryId())
                            .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
                    existing.setCategory(category);

                    existing.setMonthlyCost(dto.getMonthlyCost());
                    existing.setActiveUsersCount(dto.getActiveUsersCount());
                    existing.setOwnerDepartment(dto.getOwnerDepartment());
                    existing.setStatus(dto.getStatus());
                    return toolsRepository.save(existing);
                });
    }

    // ✅ DELETE supprimer un outil
    public boolean deleteTool(Integer id) {
        if (toolsRepository.existsById(id)) {
            toolsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long countAllTools() {
        return toolsRepository.count();
    }
}
