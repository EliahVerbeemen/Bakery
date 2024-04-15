package kdg.be;

import kdg.be.Managers.IBatchManager;
import kdg.be.Managers.IBatchproductManager;
import kdg.be.Managers.IProductManager;
import kdg.be.Managers.IngredientManager;
import kdg.be.Models.*;
import kdg.be.RabbitMQ.RabbitSender;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
@Component
public class Configuratie implements CommandLineRunner {

    private IngredientManager ingredientManager;
    private IProductManager productManager;
    private IBatchManager iBatchManager;

    private IBatchproductManager IbatchproductManager;
    private RabbitSender rabbitSender;

    public Configuratie(IngredientManager ingredientRepository, IProductManager iProductManager,
                        IBatchManager iBatchManager, IBatchproductManager IBatchproductManager
            , RabbitSender rabbitSender) {
        this.ingredientManager = ingredientRepository;
        this.productManager = iProductManager;
        this.iBatchManager = iBatchManager;
        this.IbatchproductManager = IBatchproductManager;
        this.rabbitSender = rabbitSender;

    }


    @Override
    public void run(String... args) throws Exception {
        Product nieuwProduct = new Product();
        nieuwProduct.setName("testNaam");
        ArrayList<String> stappenplan = new ArrayList<>();
        stappenplan.add("Voeg dan nu de bloem toe");
        nieuwProduct.setSteps(stappenplan);
     /*   Ingredient nieuwIngredient = new Ingredient();
        nieuwIngredient.setNaam("testIngredient");
        nieuwIngredient.setBeschrijving("Ik ben en testIngredient");*/
        Ingredient ingredient = new Ingredient("test", "testB");
        Ingredient ingredientTwee = new Ingredient("test2", "testBB");
        ingredientManager.saveIngredient(ingredientTwee);
       Ingredient ingredient1=ingredientManager.saveIngredient(ingredient);
        List<Ingredient> ingredients = ingredientManager.getAllIngredients();
      //nieuwProduct.getComposition().add(ingredient1);
       //nieuwProduct.getAmounts().add(3.1);

        System.out.println(nieuwProduct.getComposition().size());
//nieuwProduct.getSamenstelling().add(new Product_Ingredient(nieuwProduct,ing,50));

        productManager.saveOrUpdate(nieuwProduct);
        ArrayList<BatchProduct> batchproduct = new ArrayList<>();
        BatchProduct batchProduct = IbatchproductManager.save(new BatchProduct(nieuwProduct));
        batchproduct.add(batchProduct);

        //  rabbitSender.sendNewRecipe();
    }
}

;

