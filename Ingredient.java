package kdg.be.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
//@JsonDeserialize(using= IngredientDeserializer.class)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @ManyToMany
   // @JoinColumn(name = "item_id")
    List<Product> product;
    private String name;

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    private String description;


    public Ingredient(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public Ingredient() {
    }


    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long productId) {
        this.ingredientId = productId;
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

/*     @Override
    public int hashCode() {
        return this.name.hashCode() + this.description.hashCode();
    }

   @Override
    public boolean equals(Object obj) {
        if(obj ==null)return false;
        if(obj.getClass()!= Ingredient.class)return false;
        return this.ingredientId ==((Ingredient) obj).ingredientId; /*(((Ingredient) obj).getNaam().equals(this.getNaam())
        && (((Ingredient) obj).getBeschrijving().equals(this.getBeschrijving())));*/

}



