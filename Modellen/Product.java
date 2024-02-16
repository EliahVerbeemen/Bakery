package com.example.bakkery.Modellen;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class Product {


    @Id
    @GeneratedValue
    private Long ProductId;

    private String Naam;


    @ElementCollection
    private List<String> Stappenplan=new ArrayList<>();

    @ElementCollection
    private Map<Ingredient,Double> Recept;

    private ReceptStatus Receptstatus;

    public Product(String naam) {
this.Naam=naam;
    }
public Product(){

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

    public Map<Ingredient, Double> getRecept() {
        return Recept;
    }

    public void setRecept(Map<Ingredient, Double> recept) {
        Recept = recept;
    }

    public ReceptStatus getReceptstatus() {
        return Receptstatus;
    }

    public void setReceptstatus(ReceptStatus receptstatus) {
        Receptstatus = receptstatus;
        Stappenplan=new ArrayList<>();
    }

    //Een niet finaal recept mag geen prijs krijgen door de klantenbeheerder
    //Van een finaal ecept mag het recept niet meer gewijzigd worden
    public enum ReceptStatus{
        Nieuw,
        Finaal,

        Gedeactiveerd



    }

}
