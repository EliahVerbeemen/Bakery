package kdg.be.Managers;

import kdg.be.Models.Ingredient;
import kdg.be.Repositories.IIngredientRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientManager implements IIngredientManager {
    private IIngredientRepository repo;

    public IngredientManager(IIngredientRepository repository){
        repo = repository;
    }
    public List<Ingredient> getAllIngredients(){
        return repo.findAll();
    }

    public Ingredient saveIngredient(Ingredient ingredient){
        return repo.save(ingredient);
    }
}
