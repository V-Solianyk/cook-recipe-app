package com.example.cookrecipeappilication.service;

import com.example.cookrecipeappilication.model.RecipeVersion;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface RecipeVersionService {
    RecipeVersion save(RecipeVersion recipeVersion);

    List<RecipeVersion> getAllRecipeVersionsByRecipeId(Long id, PageRequest pageRequest);
}
