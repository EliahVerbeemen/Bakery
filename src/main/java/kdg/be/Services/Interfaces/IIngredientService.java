package kdg.be.Services.Interfaces;

import kdg.be.Models.Ingredient;

import java.util.List;
import java.util.Optional;


public interface IIngredientService {
    List<Ingredient> getAllIngredients();

    Optional<Ingredient> getIngredientById(Long id);

    Ingredient saveIngredient(Ingredient ingredient);

    void deleteIngredient(Long id);

    void updateIngredient(Long id, Ingredient nieuwIngredient);
}
