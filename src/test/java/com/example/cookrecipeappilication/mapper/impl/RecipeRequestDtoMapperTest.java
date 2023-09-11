package com.example.cookrecipeappilication.mapper.impl;

import com.example.cookrecipeappilication.dto.RecipeRequestDto;
import com.example.cookrecipeappilication.model.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeRequestDtoMapperTest {
    private static final String DESCRIPTION = "Test description";
    private RecipeRequestDto recipeRequestDto;
    private RecipeRequestDtoMapper dtoMapper;

    @BeforeEach
    void setUp() {
        recipeRequestDto = new RecipeRequestDto();
        recipeRequestDto.setDescription(DESCRIPTION);
        dtoMapper = new RecipeRequestDtoMapper();
    }

    @Test
    void mapToModel_ok() {
        Recipe recipe = dtoMapper.mapToModel(recipeRequestDto);
        Assertions.assertEquals(DESCRIPTION, recipe.getDescription());
    }
}
