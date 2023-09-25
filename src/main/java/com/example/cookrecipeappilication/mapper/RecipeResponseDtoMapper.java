package com.example.cookrecipeappilication.mapper;

import com.example.cookrecipeappilication.config.MapperConfig;
import com.example.cookrecipeappilication.dto.RecipeResponseDto;
import com.example.cookrecipeappilication.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface RecipeResponseDtoMapper {
    @Mapping(target = "recipeParentId", source = "recipeParent.id")
    RecipeResponseDto mapToDto(Recipe recipe);
}
