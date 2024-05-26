package kdg.be;

import kdg.be.Models.Batch;
import kdg.be.Models.BatchState;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
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


        System.out.println(nieuwProduct.getComposition().size());

        Batch batch = new Batch();
        batch.setBatchState(BatchState.NOT_YET_PREPARED);
        batch.setBatchId(1L);
        batch.setBatchDate(LocalDate.now());

        productManager.saveOrUpdate(nieuwProduct);
        nieuwProduct.getComposition().add(ingredientTwee);
        batch.getProductsinBatch().put(nieuwProduct, 3);

        iBatchService.saveOrUpdate(batch);

    }
}

