package kdg.be.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kdg.be.Deserializers.IngredientDeserializer;

@Entity
//@JsonDeserialize(using= IngredientDeserializer.class)
public class Ingredient {

    @Id
    @GeneratedValue
    private Long productId;


    private String name;


    private String description;


    public Ingredient(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public Ingredient() {
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschrijving() {
        return description;
    }

    public void setBeschrijving(String beschrijving) {
        description = beschrijving;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + this.description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj ==null)return false;
        if(obj.getClass()!= Ingredient.class)return false;
        return this.productId==((Ingredient) obj).productId; /*(((Ingredient) obj).getNaam().equals(this.getNaam())
        && (((Ingredient) obj).getBeschrijving().equals(this.getBeschrijving())));*/
    }
}



