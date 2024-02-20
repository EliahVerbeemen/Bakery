package kdg.be.Repositories;

import kdg.be.Models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIngredientRepository extends JpaRepository<Ingredient, Long> {
}
