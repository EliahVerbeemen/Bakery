package kdg.be.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private Long productId;


    private String Naam;



    private String Beschrijving;




    public Ingredient(String naam,String beschrijving){
this.Naam=naam;
this.Beschrijving=beschrijving;

    }
    public Ingredient(){}



    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getNaam() {
        return Naam;
    }

    public void setNaam(String naam) {
        Naam = naam;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        Beschrijving = beschrijving;
    }




}
