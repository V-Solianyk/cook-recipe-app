package com.example.cookrecipeappilication.service;

import com.example.cookrecipeappilication.model.Recipe;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface RecipeService {
    Recipe save(Recipe recipe);

    Recipe get(Long id);

    Recipe update(Recipe recipe, Long id);

    void delete(Long id);

    List<Recipe> getAllByCreatedDateAndDescription(LocalDate createdDate,
                                                   String description, PageRequest pageRequest);
}
