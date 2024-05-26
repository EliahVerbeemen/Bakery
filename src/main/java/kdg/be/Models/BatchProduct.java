package kdg.be.Models;

import jakarta.persistence.*;

import java.util.Map;

@Entity
public class BatchProduct {

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
}
