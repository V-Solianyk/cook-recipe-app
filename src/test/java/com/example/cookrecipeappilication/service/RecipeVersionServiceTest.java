package com.example.cookrecipeappilication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.cookrecipeappilication.model.Recipe;
import com.example.cookrecipeappilication.model.RecipeVersion;
import com.example.cookrecipeappilication.repository.RecipeVersionRepository;
import com.example.cookrecipeappilication.service.impl.RecipeVersionServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

class RecipeVersionServiceTest {
    private static final String DESCRIPTION = "Meat with vegetable";
    private RecipeVersionRepository repository;
    private RecipeVersionService recipeVersionService;
    private Recipe recipe;
    private RecipeVersion recipeVersion;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(RecipeVersionRepository.class);
        recipeVersionService = new RecipeVersionServiceImpl(repository);
        recipeVersion = new RecipeVersion();
        recipe = new Recipe();
        recipe.setId(1L);
        recipeVersion.setId(1L);
        recipeVersion.setRecipe(recipe);
        recipeVersion.setDescription(DESCRIPTION);
    }

    @Test
    void save_ok() {
        when(repository.save(recipeVersion)).thenReturn(recipeVersion);
        RecipeVersion savedVersion = recipeVersionService.save(recipeVersion);
        assertEquals(DESCRIPTION, savedVersion.getDescription());
        assertEquals(1L, savedVersion.getId());
        assertEquals(recipe, savedVersion.getRecipe());
    }

    @Test
    void getAllRecipeVersionsByRecipeId() {
        final PageRequest pageRequest = PageRequest.of(0, 10);
        RecipeVersion firstRecipeVersion = new RecipeVersion();
        firstRecipeVersion.setRecipe(recipe);
        firstRecipeVersion.setDescription(DESCRIPTION);
        RecipeVersion secondRecipeVersion = new RecipeVersion();
        secondRecipeVersion.setId(2L);
        secondRecipeVersion.setRecipe(recipe);
        secondRecipeVersion.setDescription("Meat with vegetable + salt");
        RecipeVersion thirdRecipeVersion = new RecipeVersion();
        thirdRecipeVersion.setRecipe(recipe);
        thirdRecipeVersion.setId(3L);
        thirdRecipeVersion.setDescription("Meat with vegetable + salt + bread");

        when(repository.findAllRecipeVersionsByRecipeId(1L, pageRequest))
                .thenReturn(List.of(firstRecipeVersion, secondRecipeVersion, thirdRecipeVersion));
        List<RecipeVersion> versions = recipeVersionService
                .getAllRecipeVersionsByRecipeId(1L, pageRequest);
        assertEquals(3, versions.size());
        assertEquals("Meat with vegetable + salt", versions.get(1).getDescription());
        assertEquals(recipe, versions.get(1).getRecipe());
        assertEquals(recipe, versions.get(1).getRecipe());
        assertEquals(2L, versions.get(1).getId());
    }
}
