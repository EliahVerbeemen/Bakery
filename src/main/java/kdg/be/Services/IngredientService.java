package kdg.be.Services;

import kdg.be.Models.Ingredient;
import kdg.be.Repositories.IIngredientRepository;
import kdg.be.Services.Interfaces.IIngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class IngredientService implements IIngredientService {
    private final IIngredientRepository repo;

    Logger logger = LoggerFactory.getLogger(IngredientService.class);

    public IngredientService(IIngredientRepository repository) {
        repo = repository;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return repo.findAll();
    }

    @Override
    public Optional<Ingredient> getIngredientById(Long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Ingredient saveIngredient(Ingredient ingredient) {
        return repo.save(ingredient);
    }

    @Override
    @Transactional
    public void deleteIngredient(Long id) {
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void updateIngredient(Long id, Ingredient nieuwIngredient) {

        Optional<Ingredient> oudOptioneelIngredient = repo.findById(id);
        if (oudOptioneelIngredient.isPresent()) {


            Ingredient upTeDatenIngedient = oudOptioneelIngredient.get();
            upTeDatenIngedient.setBeschrijving(nieuwIngredient.getBeschrijving());
            upTeDatenIngedient.setName(nieuwIngredient.getName());
            repo.save(upTeDatenIngedient);

        } else {
            logger.warn("There is tried to save an ingredient which doesn't exist");
        }

    }
}
