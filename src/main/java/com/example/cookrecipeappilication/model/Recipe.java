package com.example.cookrecipeappilication.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeVersion> versions = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "recipe_parent_id")
    private Recipe recipeParent;
}
