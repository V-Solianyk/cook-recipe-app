package com.example.cookrecipeappilication.repository;


import com.example.cookrecipeappilication.model.RecipeVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeVersionRepository extends JpaRepository<RecipeVersion, Long> {
    List<RecipeVersion> findAllRecipeVersionsByRecipeId(Long id, Pageable pageable);
}
