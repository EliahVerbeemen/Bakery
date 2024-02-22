package kdg.be.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static kdg.be.Models.ProductState.Nieuw;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long ProductId;

    private String name;

    @ElementCollection
    private List<String> Stappenplan=new ArrayList<>();

    @ElementCollection
private Map<Ingredient, Double>Samenstelling=new HashMap<>();

    public Map<Ingredient, Double> getSamenstelling() {
        return (Map<Ingredient, Double>) Samenstelling;
    }

    public void setSamenstelling(Map<Ingredient, Double> samenstelling) {
        Samenstelling = samenstelling;
    }

    public ProductState get_ProductStatus() {
        return _ProductStatus;
    }

    public void set_ProductStatus(ProductState _ProductStatus) {
        this._ProductStatus = _ProductStatus;
    }

    private ProductState _ProductStatus= Nieuw;


    public String getName() {
        return name;
    }

    public Product(){

    }


    public Product(String name, List<String> stappenplan) {
        this.name = name;
        Stappenplan = stappenplan;

    }
    public Product(String name, List<String> stappenplan, Map<Ingredient,Double> recepy) {
        this.name = name;
        Stappenplan = stappenplan;
this.Samenstelling=recepy;
    }
    public ProductState getProductStatus() {
        return _ProductStatus;
    }

    public void setProductStatus(ProductState productStatus) {
        _ProductStatus = productStatus;
    }




    public Long getProductId() {
        return ProductId;
    }


    public void setProductId(Long productId) {
        ProductId = productId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStappenplan() {
        return Stappenplan;
    }

    public void setStappenplan(List<String> stappenplan) {
        Stappenplan = stappenplan;
    }







}
