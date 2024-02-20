package kdg.be.Managers;

import kdg.be.Models.Ingredient;

import java.util.List;


public interface IIngredientManager {
    public List<Ingredient> getAllIngredients();
    public Ingredient saveIngredient(Ingredient ingredient);
}
