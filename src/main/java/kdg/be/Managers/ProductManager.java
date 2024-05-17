package kdg.be.Managers;

import kdg.be.Managers.Repositories.IIngredientManager;
import kdg.be.Managers.Repositories.IProductManager;
import kdg.be.Models.Product;
import kdg.be.Repositories.IProductRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductManager implements IProductManager {
    private final IProductRepository productRepository;
    private final IIngredientManager ingredientManager;

    public ProductManager(IProductRepository repository, IIngredientManager repository2) {
        productRepository = repository;
        this.ingredientManager =repository2;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

    public Product saveOrUpdate(Product product) {

        if (product.getProductId() != null && getProductById(product.getProductId()).isPresent()) {
            Product product1 = getProductById(product.getProductId()).get();
            product1.setName(product.getName());
            product1.setSteps(product.getSteps());
            product1.setComposition(product.getComposition());
            product1.setAmounts(product.getAmounts());
            product1.getComposition().forEach(e->e.getProduct().remove(product1));
            product1.setComposition(new ArrayList<>());
            product1.setComposition(product.getComposition());
            product.getComposition().forEach(e->{e.getProduct().add(product1);
                ingredientManager.saveIngredient(e) ;
            });

            return productRepository.save(product1);
        } else {
       Product p1=     productRepository.save(product);
       product.getComposition().forEach(e->{
        e.getProduct().add(product);
        ingredientManager.saveIngredient(e);

});

         return   p1;

        }
    }
}
