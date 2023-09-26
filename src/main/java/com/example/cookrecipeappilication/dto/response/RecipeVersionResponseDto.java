package com.example.cookrecipeappilication.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeVersionResponseDto {
    private Long id;
    private String description;
    private LocalDateTime createdDate;
    private Long recipeId;
}
