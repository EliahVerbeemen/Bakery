package be.kdg.bakery.Models;

import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

import java.util.Map;

public class Product {
    //Properties
    @Id
    @GeneratedValue
    public long productId;
    private String Name;
    private String Recept;
    private Map<Ingredient, String> Ingredients;
}
