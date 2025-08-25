package com.internaltools.demo1.services;

import com.internaltools.demo1.entities.Categories;
import com.internaltools.demo1.repositories.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    // Récupérer toutes les catégories
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    // Récupérer une catégorie par ID
    public Optional<Categories> getCategoryById(Integer id) {
        return categoriesRepository.findById(id);
    }

    // Créer une nouvelle catégorie
    public Categories createCategory(Categories category) {
        if (categoriesRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category already exists with name: " + category.getName());
        }
        return categoriesRepository.save(category);
    }

    // Mettre à jour une catégorie
    public Optional<Categories> updateCategory(Integer id, Categories categoryDetails) {
        return categoriesRepository.findById(id).map(category -> {
            category.setName(categoryDetails.getName());
            category.setDescription(categoryDetails.getDescription());
            category.setColorHex(categoryDetails.getColorHex());
            return categoriesRepository.save(category);
        });
    }

    // Supprimer une catégorie
    public boolean deleteCategory(Integer id) {
        if (categoriesRepository.existsById(id)) {
            categoriesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
