package com.example.cookrecipeappilication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import com.example.cookrecipeappilication.dto.request.RecipeRequestDto;
import com.example.cookrecipeappilication.dto.RecipeResponseDto;
import com.example.cookrecipeappilication.dto.RecipeVersionResponseDto;
import com.example.cookrecipeappilication.mapper.RecipeRequestDtoMapper;
import com.example.cookrecipeappilication.mapper.RecipeResponseDtoMapper;
import com.example.cookrecipeappilication.mapper.RecipeVersionResponseDtoMapper;
import com.example.cookrecipeappilication.model.Recipe;
import com.example.cookrecipeappilication.model.RecipeVersion;
import com.example.cookrecipeappilication.service.RecipeService;
import com.example.cookrecipeappilication.service.RecipeVersionService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

class RecipeControllerTest {
    private RecipeVersionService recipeVersionService;
    private RecipeService recipeService;
    private RecipeRequestDtoMapper recipeRequestDtoMapper;
    private RecipeResponseDtoMapper recipeResponseDtoMapper;
    private RecipeVersionResponseDtoMapper recipeVersionResponseDtoMapper;
    private RecipeController recipeController;
    private RecipeRequestDto recipeRequestDto;
    private Recipe recipe;
    private RecipeResponseDto recipeResponseDto;

    @BeforeEach
    void setUp() {
        recipeService = Mockito.mock(RecipeService.class);
        recipeVersionService = Mockito.mock(RecipeVersionService.class);
        recipeResponseDtoMapper = Mockito.mock(RecipeResponseDtoMapper.class);
        recipeRequestDtoMapper = Mockito.mock(RecipeRequestDtoMapper.class);
        recipeVersionResponseDtoMapper = Mockito.mock(RecipeVersionResponseDtoMapper.class);
        recipeController = new RecipeController(recipeService, recipeVersionService,
                recipeRequestDtoMapper, recipeResponseDtoMapper, recipeVersionResponseDtoMapper);
        recipeRequestDto = new RecipeRequestDto();
        recipe = new Recipe();
        recipe.setId(1L);
        recipeResponseDto = new RecipeResponseDto();
    }

    @Test
    void create_ok() {
        when(recipeRequestDtoMapper.mapToModel(recipeRequestDto)).thenReturn(recipe);
        when(recipeService.save(recipe)).thenReturn(recipe);
        when(recipeResponseDtoMapper.mapToDto(recipe)).thenReturn(recipeResponseDto);
        RecipeResponseDto result = recipeController.create(recipeRequestDto);
        assertNotNull(result);
        assertEquals(recipeResponseDto, result);
    }

    @Test
    void createNewVersion_ok() {
        Long parentId = 3L;
        Recipe parentRecipe = new Recipe();
        parentRecipe.setId(parentId);
        when(recipeRequestDtoMapper.mapToModel(recipeRequestDto)).thenReturn(recipe);
        when(recipeService.get(parentId)).thenReturn(parentRecipe);
        when(recipeService.save(recipe)).thenReturn(recipe);
        when(recipeResponseDtoMapper.mapToDto(recipe)).thenReturn(recipeResponseDto);
        RecipeResponseDto result = recipeController.createNewRecipeByParent(recipeRequestDto, parentId);
        assertNotNull(result);
        assertEquals(recipeResponseDto, result);
    }

    @Test
    void getById_ok() {
        Long id = 1L;
        when(recipeService.get(id)).thenReturn(recipe);
        when(recipeResponseDtoMapper.mapToDto(recipe)).thenReturn(recipeResponseDto);
        RecipeResponseDto result = recipeController.getById(id);
        assertNotNull(result);
        assertEquals(recipeResponseDto, result);
    }

    @Test
    void delete_ok() {
        Long id = 1L;
        Mockito.doNothing().when(recipeService).delete(id);
        recipeController.delete(id);
        Mockito.verify(recipeService, Mockito.times(1)).delete(id);
    }

    @Test
    void update_ok() {
        Long id = 1L;
        when(recipeRequestDtoMapper.mapToModel(recipeRequestDto)).thenReturn(recipe);
        when(recipeService.update(recipe, id)).thenReturn(recipe);
        when(recipeResponseDtoMapper.mapToDto(recipe)).thenReturn(recipeResponseDto);
        RecipeResponseDto result = recipeController.update(id, recipeRequestDto);
        assertNotNull(result);
        assertEquals(recipeResponseDto, result);
    }

    @Test
    void getAllPreviousRecipeVersions_ok() {
        Long id = 1L;
        String sortBy = "createdDate";
        RecipeVersion firstVersion = new RecipeVersion();
        firstVersion.setRecipe(recipe);
        RecipeVersion secondVersion = new RecipeVersion();
        secondVersion.setRecipe(recipe);
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(sortBy).descending());
        List<RecipeVersion> recipeVersions = List.of(firstVersion, secondVersion);
        List<RecipeVersionResponseDto> recipeVersionsResponseDto = List
                .of(new RecipeVersionResponseDto(), new RecipeVersionResponseDto());
        when(recipeVersionService.getAllRecipeVersionsByRecipeId(id, pageRequest))
                .thenReturn(recipeVersions);
        when(recipeVersionResponseDtoMapper.mapToDto(firstVersion))
                .thenReturn(recipeVersionsResponseDto.get(0));
        when(recipeVersionResponseDtoMapper.mapToDto(secondVersion))
                .thenReturn(recipeVersionsResponseDto.get(1));
        List<RecipeVersionResponseDto> result = recipeController
                .getAllPreviousRecipeVersions(id, 10, 0, sortBy);
        assertNotNull(result);
        assertEquals(recipeVersionsResponseDto, result);
        assertEquals(recipeVersions.size(), result.size());
    }

    @Test
    void getAllByDateAndDescription_ok() {
        LocalDate createdDate = LocalDate.of(2023, 9, 10);
        String description = "potato";
        recipe.setDescription("potato");
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setDescription("meat");
        Recipe recipe3 = new Recipe();
        recipe3.setId(3L);
        recipe3.setDescription("potato with meat");
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Recipe> recipeList = List.of(recipe, recipe3);
        RecipeResponseDto responseDto1 = new RecipeResponseDto();
        responseDto1.setId(recipe.getId());
        responseDto1.setDescription(recipe.getDescription());
        RecipeResponseDto responseDto3 = new RecipeResponseDto();
        responseDto3.setId(recipe3.getId());
        responseDto3.setDescription(recipe3.getDescription());
        when(recipeService.getAllByCreatedDateAndDescription(createdDate, description, pageRequest))
                .thenReturn(recipeList);
        when(recipeResponseDtoMapper.mapToDto(recipe)).thenReturn(responseDto1);
        when(recipeResponseDtoMapper.mapToDto(recipe3)).thenReturn(responseDto3);
        List<RecipeResponseDto> result = recipeController
                .getAllByDateAndDescription(10, 0, createdDate, description);
        assertEquals(2, result.size());
        assertEquals(responseDto1, result.get(0));
        assertEquals(responseDto3, result.get(1));
    }
}
