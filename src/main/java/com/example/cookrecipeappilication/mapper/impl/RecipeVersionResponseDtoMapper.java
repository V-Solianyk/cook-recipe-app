package com.example.cookrecipeappilication.mapper.impl;

import com.example.cookrecipeappilication.dto.RecipeVersionResponseDto;
import com.example.cookrecipeappilication.mapper.ResponseDtoMapper;
import com.example.cookrecipeappilication.model.RecipeVersion;
import org.springframework.stereotype.Component;

@Component
public class RecipeVersionResponseDtoMapper implements ResponseDtoMapper<RecipeVersionResponseDto,
        RecipeVersion> {
    @Override
    public RecipeVersionResponseDto mapToDto(RecipeVersion recipeVersion) {
        RecipeVersionResponseDto recipeVersionResponseDto = new RecipeVersionResponseDto();
        recipeVersionResponseDto.setId(recipeVersion.getId());
        recipeVersionResponseDto.setDescription(recipeVersion.getDescription());
        recipeVersionResponseDto.setCreatedDate(recipeVersion.getCreatedDate());
        recipeVersionResponseDto.setRecipeId(recipeVersion.getRecipe().getId());
        return recipeVersionResponseDto;
    }
}
