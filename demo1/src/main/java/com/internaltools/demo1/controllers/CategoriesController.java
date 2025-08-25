package com.internaltools.demo1.controllers;

import com.internaltools.demo1.entities.Categories;
import com.internaltools.demo1.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {

    private final CategoriesService categoriesService;

    // ✅ GET toutes les catégories
    @GetMapping
    public List<Categories> getAllCategories() {
        return categoriesService.getAllCategories();
    }

    // ✅ GET catégorie par ID
    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Integer id) {
        return categoriesService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST créer une catégorie
    @PostMapping
    public ResponseEntity<Categories> createCategory(@RequestBody Categories category) {
        return ResponseEntity.ok(categoriesService.createCategory(category));
    }

    // ✅ PUT mettre à jour une catégorie
    @PutMapping("/{id}")
    public ResponseEntity<Categories> updateCategory(
            @PathVariable Integer id,
            @RequestBody Categories categoryDetails
    ) {
        return categoriesService.updateCategory(id, categoryDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE supprimer une catégorie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        return categoriesService.deleteCategory(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
