package kdg.be.Models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kdg.be.Models.ProductState.Nieuw;

@Entity
public class Product {
    //Properties
    @Id
    @GeneratedValue
    private Long productId;
    private String name;
    @ElementCollection
    private List<String> steps = new ArrayList<>();
    @ElementCollection
    private Map<Ingredient, Double> composition = new HashMap<>();
    private ProductState _ProductStatus = Nieuw;

    //Constructors
    public Product() {
    }

    public Product(String name, List<String> steps) {
        this.name = name;
        this.steps = steps;
    }

    public Product(String name, List<String> steps, Map<Ingredient, Double> recepy) {
        this.name = name;
        this.steps = steps;
        this.composition = recepy;
    }

    // @JsonDeserialize(keyUsing = IngredientDeserializer.class)
    //GET & SET
    public Map<Ingredient, Double> getComposition() {
        return composition;
    }

    public void setComposition(Map<Ingredient, Double> composition) {
        this.composition = composition;
    }

    public ProductState get_ProductStatus() {
        return _ProductStatus;
    }

    public void set_ProductStatus(ProductState _ProductStatus) {
        this._ProductStatus = _ProductStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductState getProductStatus() {
        return _ProductStatus;
    }

    public void setProductStatus(ProductState productStatus) {
        _ProductStatus = productStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }


}
