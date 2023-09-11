package com.example.cookrecipeappilication.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.cookrecipeappilication.dto.RecipeResponseDto;
import com.example.cookrecipeappilication.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeResponseDtoMapperTest {
    private static final String DESCRIPTION = "Test description";
    private Recipe recipe;
    private RecipeResponseDtoMapper recipeResponseDtoMapper;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setDescription(DESCRIPTION);
        recipeResponseDtoMapper = new RecipeResponseDtoMapper();
    }

    @Test
    public void mapToDto_ok() {
        RecipeResponseDto responseDto = recipeResponseDtoMapper.mapToDto(recipe);
        assertEquals(recipe.getId(), responseDto.getId());
        assertEquals(recipe.getDescription(), responseDto.getDescription());
    }

    @Test
    public void mapToDto_WithParentRecipe_ok() {
        Recipe parentRecipe = new Recipe();
        parentRecipe.setId(2L);
        parentRecipe.setDescription("Parent Recipe");
        recipe.setRecipeParent(parentRecipe);
        RecipeResponseDto responseDto = recipeResponseDtoMapper.mapToDto(recipe);
        assertEquals(recipe.getId(), responseDto.getId());
        assertEquals(recipe.getDescription(), responseDto.getDescription());
        assertEquals(parentRecipe.getId(), responseDto.getRecipeParentId());
    }
}
