package com.example.bakkery.Repositories;

import com.example.bakkery.Modellen.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {


}
