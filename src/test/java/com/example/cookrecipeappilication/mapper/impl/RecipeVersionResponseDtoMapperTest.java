package com.example.cookrecipeappilication.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.cookrecipeappilication.dto.RecipeVersionResponseDto;
import com.example.cookrecipeappilication.model.Recipe;
import com.example.cookrecipeappilication.model.RecipeVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeVersionResponseDtoMapperTest {
    private static final String DESCRIPTION = "Test description";
    private RecipeVersion recipeVersion;
    private RecipeVersionResponseDtoMapper recipeVersionResponseDtoMapper;
    private Recipe recipe;

    RecipeVersionResponseDtoMapperTest() {
    }

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setId(1L);
        recipeVersion = new RecipeVersion();
        recipeVersionResponseDtoMapper = new RecipeVersionResponseDtoMapper();
        recipeVersion.setId(10L);
        recipeVersion.setDescription(DESCRIPTION);
        recipeVersion.setRecipe(recipe);
    }

    @Test
    void mapToDto_ok() {
        RecipeVersionResponseDto responseDto = recipeVersionResponseDtoMapper
                .mapToDto(recipeVersion);
        assertEquals(recipeVersion.getId(), responseDto.getId());
        assertEquals(recipeVersion.getDescription(), responseDto.getDescription());
        assertEquals(recipeVersion.getCreatedDate(), responseDto.getCreatedDate());
        assertEquals(recipe.getId(), responseDto.getRecipeId());
    }
}
