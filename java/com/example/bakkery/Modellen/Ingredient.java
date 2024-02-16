package com.example.bakkery.Modellen;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;




@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNaam() {
        return Naam;
    }

    public Ingredient(String naam) {
        Naam = naam;
    }

    public void setNaam(String naam) {
        Naam = naam;
    }

    private String Naam;




}
