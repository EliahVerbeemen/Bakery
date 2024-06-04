package kdg.be.Models.PurchaseOrder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import kdg.be.Deserializers.PurchaseProductDeserializer;
import kdg.be.Models.Product;

import java.util.Objects;

@Entity
@JsonDeserialize(using = PurchaseProductDeserializer.class)
public class PurchaseProduct extends Product {

    private String productNumber;

    private int quantity;

    public PurchaseProduct() {
    }

    public PurchaseProduct(String productNumber, String specialInstruction, int quantity) {
        //   this.purchaseOrderId = purchaseOrderId;
        this.productNumber = productNumber;
        this.setSpecialInstruction(specialInstruction);
        this.quantity = quantity;
    }

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
}
