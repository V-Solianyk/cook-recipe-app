package com.example.cookrecipeappilication.repository;

import com.example.cookrecipeappilication.model.Recipe;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE r.createdDate > :createdDate AND LOWER(r.description)"
            + " LIKE CONCAT('%', LOWER(:description), '%') ORDER BY r.description ASC")
    List<Recipe> findRecipesByCreatedDateAndDescriptionContainsAndSorted(
            @Param("createdDate") LocalDateTime createdDate,
            @Param("description") String description,
            Pageable pageable);
}
