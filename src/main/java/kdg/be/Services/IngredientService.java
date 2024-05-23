package kdg.be.Services;

import kdg.be.Services.Repositories.IIngredientManager;
import kdg.be.Models.Ingredient;
import kdg.be.Repositories.IIngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class IngredientManager implements IIngredientManager {
    private final IIngredientRepository repo;

    Logger logger=LoggerFactory.getLogger(IngredientManager.class) ;

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
    @Transactional
    public Ingredient saveIngredient(Ingredient ingredient){
        return repo.save(ingredient);
    }

    @Override
    @Transactional
    public void deleteIngredient(Long id){
        repo.deleteById(id);
    }

@Override
@Transactional
    public void updateIngredient(Long id, Ingredient nieuwIngredient){

       Optional<Ingredient> oudOptioneelIngredient= repo.findById(id);
        if(oudOptioneelIngredient.isPresent()){


            Ingredient upTeDatenIngedient=oudOptioneelIngredient.get();
            upTeDatenIngedient.setBeschrijving(nieuwIngredient.getBeschrijving());
            upTeDatenIngedient.setName(nieuwIngredient.getName());
            repo.save(upTeDatenIngedient);

        }
        else{
            logger.warn("There is tried to save an ingredient which doesn't exist");
        }

    }
}
