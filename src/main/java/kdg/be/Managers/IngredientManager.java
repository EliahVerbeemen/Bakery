package kdg.be.Managers;

import kdg.be.Models.Ingredient;
import kdg.be.Repositories.IIngredientRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IngredientManager implements IIngredientManager {
    private IIngredientRepository repo;

    public IngredientManager(IIngredientRepository repository){
        repo = repository;
    }
    @Override
    public List<Ingredient> getAllIngredients(){
        return repo.findAll();
    }
    @Override
    public Optional<Ingredient> getIngredientById(Long id){
        return repo.findById(id);
    }
    @Override
    public Ingredient saveIngredient(Ingredient ingredient){
        return repo.save(ingredient);
    }

    @Override
    public void deleteIngredient(Long id){
        repo.deleteById(id);
    }
}
