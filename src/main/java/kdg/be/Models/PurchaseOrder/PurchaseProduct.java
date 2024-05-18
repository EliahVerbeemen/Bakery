package kdg.be.Models.PurchaseOrder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import kdg.be.Services.PurchaseProductDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@JsonDeserialize(using = PurchaseProductDeserializer.class)
public class PurchaseProduct extends Product {

  /*  @Id
    private  int purchaseOrderId;*/

   private String productNumber;



    private int quantity;

  /*  @ElementCollection
    private Map<Ingredient,Double>ingredientMap=new HashMap<>();

    public Map<Ingredient, Double> getIngredientMap() {
        return ingredientMap;
   }

public void setIngredientMap(Map<Ingredient, Double> ingredientMap) {
        this.ingredientMap = ingredientMap;
    }
*/
  /*  public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(int purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }
*/
    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PurchaseProduct() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseProduct that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(productNumber, that.productNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), productNumber);
    }

    public PurchaseProduct(String productNumber, String specialInstruction, int quantity) {
     //   this.purchaseOrderId = purchaseOrderId;
        this.productNumber = productNumber;
        this.setSpecialInstruction(specialInstruction);
        this.quantity = quantity;
    }
}
