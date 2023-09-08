package com.example.cookrecipeappilication.service;

import com.example.cookrecipeappilication.model.Recipe;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface RecipeService {
    Recipe save(Recipe recipe);

    Recipe get(Long id);

    Recipe update(Recipe recipe, Long id);

    void delete(Long id);

    List<Recipe> getAllRecipes(PageRequest pageRequest);

    List<Recipe> getAllByCreatedDateAndDescription(LocalDateTime createdDate,
                                                   String description, PageRequest pageRequest);
}
