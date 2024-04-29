package kdg.be.DTO;

import kdg.be.Models.Batch;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BatchForWarehouse {
    List<Ingredient> ingredients=new ArrayList<>();
    List<Double>Amounts=new ArrayList<>();

    Long batchId;

    public BatchForWarehouse() {
    }

    public BatchForWarehouse(List<Ingredient> ingredients, List<Double> amounts, Long batchId) {
        this.ingredients = ingredients;
        this.Amounts = amounts;
        this.batchId = batchId;
    }

    public BatchForWarehouse crateBathForWaehouse(Batch batch){


        Map<Ingredient,Double> toSend=new HashMap<>();

        Map<Product,Integer>products= batch.getProductsinBatch();
        //Voor elk product dat besteld is...
        products.forEach((product,numberOfProducts)->{
            List<Ingredient> ingredients=  product.getComposition();
            List<Double>  amounts=product.getAmounts();
            List<Double> ingredientMultiplyNumber=amounts.stream().map(i->i*numberOfProducts).toList();
            ingredients.forEach(ingr->{


                for(int i=0;i<ingredients.size();i++){

                    toSend.putIfAbsent(ingredients.get(i),0.0);

                    toSend.put(ingredients.get(i),
                            toSend.get(ingredients.get(i))+ingredientMultiplyNumber.get(i));


                }


            });



        });
        return new BatchForWarehouse(toSend.keySet().stream().toList(),toSend.values().stream().toList(),1L);





    }
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Double> getAmounts() {
        return Amounts;
    }

    public void setAmounts(List<Double> amounts) {
        Amounts = amounts;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }
}
