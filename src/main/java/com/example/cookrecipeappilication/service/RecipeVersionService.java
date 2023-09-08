package com.example.cookrecipeappilication.service;


import com.example.cookrecipeappilication.model.RecipeVersion;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RecipeVersionService {
    RecipeVersion save(RecipeVersion recipeVersion);

    List<RecipeVersion> getAllRecipeVersionsByRecipeId(Long id, PageRequest pageRequest);
}
