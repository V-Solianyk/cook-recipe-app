package com.example.cookrecipeappilication.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RecipeVersionResponseDto {
    private Long id;
    private String description;
    private LocalDateTime createdDate;
    private Long recipeId;
}
