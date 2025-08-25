package com.internaltools.demo1.repositories;

import com.internaltools.demo1.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    boolean existsByName(String name);
}
