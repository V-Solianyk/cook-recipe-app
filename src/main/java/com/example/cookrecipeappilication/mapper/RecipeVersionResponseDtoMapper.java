package com.example.cookrecipeappilication.mapper;

import com.example.cookrecipeappilication.config.MapperConfig;
import com.example.cookrecipeappilication.dto.RecipeVersionResponseDto;
import com.example.cookrecipeappilication.model.RecipeVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface RecipeVersionResponseDtoMapper {
    @Mapping(target = "recipeId", source = "recipe.id")
    RecipeVersionResponseDto mapToDto(RecipeVersion recipeVersion);
}

