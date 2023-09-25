package com.example.cookrecipeappilication.mapper;

import com.example.cookrecipeappilication.config.MapperConfig;
import com.example.cookrecipeappilication.dto.request.RecipeRequestDto;
import com.example.cookrecipeappilication.model.Recipe;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface RecipeRequestDtoMapper {
    Recipe mapToModel(RecipeRequestDto dto);
}
