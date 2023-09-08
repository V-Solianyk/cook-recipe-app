package com.example.cookrecipeappilication.mapper.impl;

import com.example.cookrecipeappilication.dto.RecipeResponseDto;
import com.example.cookrecipeappilication.mapper.ResponseDtoMapper;
import com.example.cookrecipeappilication.model.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeResponseDtoMapper implements ResponseDtoMapper<RecipeResponseDto, Recipe> {
    @Override
    public RecipeResponseDto mapToDto(Recipe recipe) {
        RecipeResponseDto responseDto = new RecipeResponseDto();
        responseDto.setId(recipe.getId());
        responseDto.setDescription(recipe.getDescription());
        if (recipe.getRecipeParent() != null) {
            responseDto.setRecipeParentId(recipe.getRecipeParent().getId());
        }
        return responseDto;
    }
}
