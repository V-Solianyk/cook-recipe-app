package com.example.cookrecipeappilication.repository;

import com.example.cookrecipeappilication.model.RecipeVersion;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeVersionRepository extends JpaRepository<RecipeVersion, Long> {
    List<RecipeVersion> findAllRecipeVersionsByRecipeId(Long id, Pageable pageable);
}
