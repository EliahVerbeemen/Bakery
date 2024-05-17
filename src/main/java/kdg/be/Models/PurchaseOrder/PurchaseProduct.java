package kdg.be.Models.PurchaseOrder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import kdg.be.Models.Ingredient;
import kdg.be.Services.PurchaseProductDeserializer;

import java.util.HashMap;
import java.util.Map;

@Entity
@JsonDeserialize(using = PurchaseProductDeserializer.class)
public class PurchaseProduct {

    @Id
    private  int purchaseOrderId;

   private String productNumber;

   private String specialInstruction;

    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private int quantity;

    @ElementCollection
    private Map<Ingredient,Double>ingredientMap=new HashMap<>();

    public Map<Ingredient, Double> getIngredientMap() {
        return ingredientMap;
    }

    public void setIngredientMap(Map<Ingredient, Double> ingredientMap) {
        this.ingredientMap = ingredientMap;
    }

    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(int purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PurchaseProduct() {
    }

    public PurchaseProduct(int purchaseOrderId, String productNumber, String specialInstruction, int quantity) {
        this.purchaseOrderId = purchaseOrderId;
        this.productNumber = productNumber;
        this.specialInstruction = specialInstruction;
        this.quantity = quantity;
    }
}
