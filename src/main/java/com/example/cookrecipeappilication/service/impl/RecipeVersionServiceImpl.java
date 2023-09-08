package com.example.cookrecipeappilication.service.impl;

import com.example.cookrecipeappilication.model.RecipeVersion;
import com.example.cookrecipeappilication.repository.RecipeVersionRepository;
import com.example.cookrecipeappilication.service.RecipeVersionService;

import java.util.List;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeVersionServiceImpl implements RecipeVersionService {
    private final RecipeVersionRepository versionRepository;

    @Override
    public RecipeVersion save(RecipeVersion recipeVersion) {
        return versionRepository.save(recipeVersion);
    }

    @Override
    public List<RecipeVersion> getAllRecipeVersionsByRecipeId(Long id, PageRequest pageRequest) {
        return versionRepository.findAllRecipeVersionsByRecipeId(id, pageRequest);
    }
}
