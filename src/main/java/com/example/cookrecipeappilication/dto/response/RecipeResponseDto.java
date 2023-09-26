package com.example.cookrecipeappilication.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeResponseDto {
    private Long id;
    private String description;
    private Long recipeParentId;
}
