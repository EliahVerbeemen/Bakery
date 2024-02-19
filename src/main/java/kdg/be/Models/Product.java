package kdg.be.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static kdg.be.Models.ProductStatus.Finaal;
import static kdg.be.Models.ProductStatus.Nieuw;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long ProductId;

    private String Naam;

    @ElementCollection
    private List<String> Stappenplan=new ArrayList<>();

    @ElementCollection
private Map<Ingredient, Double>Samenstelling=new HashMap<>();

    public Map<Ingredient, Double> getSamenstelling() {
        return Samenstelling;
    }

    public void setSamenstelling(Map<Ingredient, Double> samenstelling) {
        Samenstelling = samenstelling;
    }

    public ProductStatus get_ProductStatus() {
        return _ProductStatus;
    }

    public void set_ProductStatus(ProductStatus _ProductStatus) {
        this._ProductStatus = _ProductStatus;
    }

    private ProductStatus _ProductStatus= Nieuw;




    public Product(){

    }


    public Product(String naam, List<String> stappenplan) {
        Naam = naam;
        Stappenplan = stappenplan;

    }

    public ProductStatus getProductStatus() {
        return _ProductStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        _ProductStatus = productStatus;
    }




    public Long getProductId() {
        return ProductId;
    }


    public void setProductId(Long productId) {
        ProductId = productId;
    }

    public String getNaam() {
        return Naam;
    }

    public void setNaam(String naam) {
        Naam = naam;
    }

    public List<String> getStappenplan() {
        return Stappenplan;
    }

    public void setStappenplan(List<String> stappenplan) {
        Stappenplan = stappenplan;
    }







}
