package com.example.cookrecipeappilication.controller;

import com.example.cookrecipeappilication.dto.RecipeRequestDto;
import com.example.cookrecipeappilication.dto.RecipeResponseDto;
import com.example.cookrecipeappilication.dto.RecipeVersionResponseDto;
import com.example.cookrecipeappilication.mapper.RequestDtoMapper;
import com.example.cookrecipeappilication.mapper.ResponseDtoMapper;
import com.example.cookrecipeappilication.model.Recipe;
import com.example.cookrecipeappilication.model.RecipeVersion;
import com.example.cookrecipeappilication.service.RecipeService;
import com.example.cookrecipeappilication.service.RecipeVersionService;
import com.example.cookrecipeappilication.util.SortUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private static final String DEFAULT_CREATED_DATE = "1970-01-01";
    private final RecipeService recipeService;
    private final RecipeVersionService recipeVersionService;
    private final ResponseDtoMapper<RecipeResponseDto, Recipe> recipeResponseDtoMapper;
    private final RequestDtoMapper<RecipeRequestDto, Recipe> recipeRequestDtoMapper;
    private final ResponseDtoMapper<RecipeVersionResponseDto,
            RecipeVersion> recipeVersionResponseDtoMapper;

    @PostMapping
    public RecipeResponseDto create(@RequestBody RecipeRequestDto recipeRequestDto) {
        Recipe recipe = recipeService.save(recipeRequestDtoMapper.mapToModel(recipeRequestDto));
        return recipeResponseDtoMapper.mapToDto(recipe);
    }

    @PostMapping("/{parentId}")
    public RecipeResponseDto createNewRecipeByParent(@RequestBody RecipeRequestDto recipeRequestDto,
                                    @PathVariable Long parentId) {
        Recipe recipe = recipeRequestDtoMapper.mapToModel(recipeRequestDto);
        recipe.setRecipeParent(recipeService.get(parentId));
        Recipe recipeResult = recipeService.save(recipe);
        return recipeResponseDtoMapper.mapToDto(recipeResult);
    }

    @GetMapping("/{id}")
    public RecipeResponseDto getById(@PathVariable Long id) {
        Recipe recipe = recipeService.get(id);
        return recipeResponseDtoMapper.mapToDto(recipe);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recipeService.delete(id);
    }

    @PutMapping("/{id}")
    public RecipeResponseDto update(@PathVariable Long id,
                                    @RequestBody RecipeRequestDto recipeRequestDto) {
        Recipe recipe = recipeService.update(recipeRequestDtoMapper
                .mapToModel(recipeRequestDto), id);
        return recipeResponseDtoMapper.mapToDto(recipe);
    }

    @GetMapping("/versions/{id}")
    public List<RecipeVersionResponseDto> getAllPreviousRecipeVersions(
            @PathVariable Long id, @RequestParam(defaultValue = "10") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "createdDate") String sortBy) {
        Sort sort = SortUtils.createSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        List<RecipeVersion> recipeVersions = recipeVersionService.getAllRecipeVersionsByRecipeId(
                id, pageRequest);
        return recipeVersions.stream()
                .map(recipeVersionResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-date-and-description")
    public List<RecipeResponseDto> getAllByDateAndDescription(
            @RequestParam(defaultValue = "10") Integer count,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = DEFAULT_CREATED_DATE) LocalDate createdDate,
            @RequestParam(defaultValue = "") String description) {
        PageRequest pageRequest = PageRequest.of(page, count);
        List<Recipe> recipes = recipeService.getAllByCreatedDateAndDescription(
                createdDate, description, pageRequest);
        return recipes.stream()
                .map(recipeResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
