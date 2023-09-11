package com.example.cookrecipeappilication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.cookrecipeappilication.model.Recipe;
import com.example.cookrecipeappilication.model.RecipeVersion;
import com.example.cookrecipeappilication.repository.RecipeRepository;
import com.example.cookrecipeappilication.repository.RecipeVersionRepository;
import com.example.cookrecipeappilication.service.impl.RecipeServiceImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

class RecipeServiceTest {
    private static final String FIRST_RECIPE_DESCRIPTION = "Potato";
    private static final String SECOND_RECIPE_DESCRIPTION = "Meat with vegetable";
    private Recipe firstTestRecipe;
    private Recipe secondTestRecipe;
    private RecipeVersion recipeVersion;
    private RecipeService recipeService;
    private RecipeRepository recipeRepository;
    private RecipeVersionRepository versionRepository;

    @BeforeEach
    void setUp() {
        recipeRepository = Mockito.mock(RecipeRepository.class);
        versionRepository = Mockito.mock(RecipeVersionRepository.class);
        recipeService = new RecipeServiceImpl(recipeRepository, versionRepository);
        firstTestRecipe = new Recipe();
        firstTestRecipe.setDescription(FIRST_RECIPE_DESCRIPTION);
        firstTestRecipe.setId(1L);
        secondTestRecipe = new Recipe();
        secondTestRecipe.setDescription(SECOND_RECIPE_DESCRIPTION);
        secondTestRecipe.setId(2L);
        recipeVersion = new RecipeVersion();
    }

    @Test
    public void save_ok() {
        when(recipeRepository.save(firstTestRecipe)).thenReturn(firstTestRecipe);
        when(versionRepository.save(recipeVersion)).thenReturn(recipeVersion);
        Recipe savedRecipe = recipeService.save(firstTestRecipe);
        assertEquals(FIRST_RECIPE_DESCRIPTION, savedRecipe.getDescription());
        assertEquals(1L, savedRecipe.getId());
    }

    @Test
    public void get_ok() {
        Long id = 2L;
        when(recipeRepository.findById(id)).thenReturn(Optional.of(secondTestRecipe));
        Recipe actualdRecipe = recipeService.get(id);
        verify(recipeRepository).findById(id);
        assertEquals(SECOND_RECIPE_DESCRIPTION, actualdRecipe.getDescription());
        assertEquals(id, actualdRecipe.getId());
    }

    @Test
    public void get_RecipeNotFound_notOk() {
        Long id = 100L;
        String expectedMassage = "Recipe not found with id: " + id;
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> recipeService.get(id));
        assertEquals(expectedMassage, exception.getMessage());
    }

    @Test
    public void update_ok() {
        Long id = 1L;
        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setId(id);
        updatedRecipe.setDescription("Updated Recipe");
        when(recipeRepository.findById(id)).thenReturn(Optional.of(firstTestRecipe));
        when(versionRepository.save(recipeVersion)).thenReturn(recipeVersion);
        recipeService.update(updatedRecipe, id);
        assertEquals("Updated Recipe", firstTestRecipe.getDescription());
    }

    @Test
    public void delete_ok() {
        Long id = 2L;
        when(recipeRepository.findById(id)).thenReturn(Optional.of(secondTestRecipe));
        recipeService.delete(id);
        verify(recipeRepository).deleteById(id);
    }

    @Test
    public void delete_RecipeNotFound_notOk() {
        Long id = 999L;
        String expectedMassage = "Recipe not found with id: " + id;
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> recipeService.get(id));
        assertEquals(expectedMassage, exception.getMessage());
    }

    @Test
    public void testGetAllByCreatedDateAndDescription_withDefaultDate_ok() {
        String description = "";
        LocalDate createdDate = LocalDate.of(1970, 1, 1);
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(recipeRepository.findRecipesByCreatedDateAndDescriptionContainsAndSorted(
                any(LocalDateTime.class), eq(description), eq(pageRequest)))
                .thenReturn(List.of(firstTestRecipe, secondTestRecipe));

        List<Recipe> recipes = recipeService.getAllByCreatedDateAndDescription(createdDate, description, pageRequest);
        assertEquals(2, recipes.size());
    }

    @Test
    public void testGetAllByCreatedDateAndDescription_withDefaultDateSetValueDescription_ok() {
        String description = "Meat";
        LocalDate createdDate = LocalDate.of(1970, 1, 1);
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(recipeRepository.findRecipesByCreatedDateAndDescriptionContainsAndSorted(
                any(LocalDateTime.class), eq(description), eq(pageRequest)))
                .thenReturn(List.of(secondTestRecipe));

        List<Recipe> recipes = recipeService.getAllByCreatedDateAndDescription(createdDate, description, pageRequest);
        assertEquals(1, recipes.size());
        assertTrue(recipes.get(0).getDescription().contains(description));
    }

    @Test
    public void testGetAllByCreatedDateAndDescription_ok() {
        String description = "Meat";
        LocalDate fromThisCreatedDate = LocalDate.of(2020, 9, 10);
        firstTestRecipe.setCreatedDate(LocalDateTime.of(2010, 9, 10,
                11, 54, 25));
        secondTestRecipe.setCreatedDate(LocalDateTime.of(2021, 9, 10,
                11, 54, 25));
        PageRequest pageRequest = PageRequest.of(0, 10);
        when(recipeRepository.findRecipesByCreatedDateAndDescriptionContainsAndSorted(
                any(LocalDateTime.class), eq(description), eq(pageRequest)))
                .thenReturn(List.of(secondTestRecipe));

        List<Recipe> recipes = recipeService.getAllByCreatedDateAndDescription(fromThisCreatedDate, description, pageRequest);
        assertEquals(1, recipes.size());
        assertTrue(recipes.get(0).getDescription().contains(description));
        assertTrue(recipes.get(0).getCreatedDate().isAfter(fromThisCreatedDate.atStartOfDay()));
    }
}
