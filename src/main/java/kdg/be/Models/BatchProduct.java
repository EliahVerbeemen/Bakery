package kdg.be.Models;

import jakarta.persistence.*;

import java.util.Map;
import java.util.Objects;

@Entity
public class
BatchProduct {

    @Id
    @GeneratedValue
    private Long BatchProductId;

    @OneToOne
    private Product product;

    @ElementCollection
    private Map<PreparationState, Integer> productPreparation = Map.of(PreparationState.NOT_BAKED, 0, PreparationState.BAKING_STARTED, 0, PreparationState.BAKED, 0);


    public BatchProduct(Product product) {
        this.product = product;
    }

    public BatchProduct() {

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<PreparationState, Integer> getProductPreparation() {
        return productPreparation;
    }

    public void setProductPreparation(Map<PreparationState, Integer> bereidingVanProducten) {
        this.productPreparation = bereidingVanProducten;
    }

    //test
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BatchProduct that)) return false;
        return Objects.equals(BatchProductId, that.BatchProductId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BatchProductId);
    }
}
