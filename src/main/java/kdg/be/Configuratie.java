package kdg.be;

import kdg.be.Models.*;
import kdg.be.RabbitMQ.RabbitSender;
import kdg.be.Services.IngredientService;
import kdg.be.Services.Interfaces.IBatchProductService;
import kdg.be.Services.Interfaces.IBatchService;
import kdg.be.Services.Interfaces.IProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@Component
public class Configuratie implements CommandLineRunner {

    private final IngredientService ingredientManager;
    private final IProductService productManager;
    private final IBatchService iBatchService;

    private final IBatchProductService ibatchproductManager;
    private final RabbitSender rabbitSender;

    public Configuratie(IngredientService ingredientRepository, IProductService iProductService,
                        IBatchService iBatchService, IBatchProductService IBatchProductService
            , RabbitSender rabbitSender) {
        this.ingredientManager = ingredientRepository;
        this.productManager = iProductService;
        this.iBatchService = iBatchService;
        this.ibatchproductManager = IBatchProductService;
        this.rabbitSender = rabbitSender;

    }


    @Override
    public void run(String... args) throws Exception {
        Product nieuwProduct = new Product();
        nieuwProduct.setName("testNaam");
        ArrayList<String> stappenplan = new ArrayList<>();
        stappenplan.add("Voeg dan nu de bloem toe");
        nieuwProduct.setSteps(stappenplan);
        Ingredient ingredient = new Ingredient("test", "testB");
        Ingredient ingredientTwee = new Ingredient("test2", "testBB");
        Ingredient ik = ingredientManager.saveIngredient(ingredientTwee);
        Ingredient ingredient1 = ingredientManager.saveIngredient(ingredient);
        List<Ingredient> ingredients = ingredientManager.getAllIngredients();
        Product product=new Product();
        product.setName("test");
        product.getComposition().add(ik);
        product.getComposition().add(ingredient1);
        product.setAmounts(List.of(2.0,3.2));

        product.setSteps(List.of("bijvoorbeeld"));

        Batch batch=new Batch();
        batch.setBatchId(1L);





    }
}

