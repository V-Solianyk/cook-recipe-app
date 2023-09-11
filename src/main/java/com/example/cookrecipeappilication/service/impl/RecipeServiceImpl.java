package com.example.cookrecipeappilication.service.impl;

import com.example.cookrecipeappilication.model.Recipe;
import com.example.cookrecipeappilication.model.RecipeVersion;
import com.example.cookrecipeappilication.repository.RecipeRepository;
import com.example.cookrecipeappilication.repository.RecipeVersionRepository;
import com.example.cookrecipeappilication.service.RecipeService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeVersionRepository versionRepository;

    @Override
    public Recipe save(Recipe recipe) {
        checkDescription(recipe);
        Recipe newRecipe = recipeRepository.save(recipe);
        createRecipeVersion(recipe);
        return newRecipe;
    }

    @Override
    public Recipe get(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found with id: " + id));
    }

    @Override
    public Recipe update(Recipe recipe, Long id) {
        Recipe oldRecipe = get(id);
        oldRecipe.setDescription(recipe.getDescription());
        createRecipeVersion(oldRecipe);
        return recipeRepository.save(oldRecipe);
    }

    @Override
    public void delete(Long id) {
        get(id);
        recipeRepository.deleteById(id);
    }

    @Override
    public List<Recipe> getAllByCreatedDateAndDescription(
            LocalDate createdDate, String description, PageRequest pageRequest) {
        return recipeRepository.findRecipesByCreatedDateAndDescriptionContainsAndSorted(
                createdDate.atStartOfDay(), description, pageRequest);
    }

    private void createRecipeVersion(Recipe recipe) {
        RecipeVersion recipeVersion = new RecipeVersion();
        recipeVersion.setRecipe(recipe);
        recipeVersion.setDescription(recipe.getDescription());
        versionRepository.save(recipeVersion);
    }

    private static void checkDescription(Recipe recipe) {
        if (recipe.getDescription() == null || recipe.getDescription().isBlank()) {
            throw new IllegalArgumentException("The field description can't be null or empty");
        }
    }
}
