package com.example.cookrecipeappilication.mapper.impl;

import com.example.cookrecipeappilication.dto.RecipeRequestDto;
import com.example.cookrecipeappilication.mapper.RequestDtoMapper;
import com.example.cookrecipeappilication.model.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeRequestDtoMapper implements RequestDtoMapper<RecipeRequestDto, Recipe> {
    @Override
    public Recipe mapToModel(RecipeRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setDescription(dto.getDescription());
        return recipe;
    }
}
