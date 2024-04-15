package kdg.be.Managers;

import kdg.be.Models.Product;
import kdg.be.Repositories.IProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductManager implements IProductManager {
    private IProductRepository repo;
    private IIngredientManager repository2;

    public ProductManager(IProductRepository repository, IIngredientManager repository2) {
        repo = repository;
        this.repository2=repository2;
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return repo.findById(id);
    }

    public Product saveProduct(Product product) {

        return repo.save(product);
    }

    public Product saveOrUpdate(Product product) {
        if (product.getProductId() != null && getProductById(product.getProductId()).isPresent()) {
            Product product1 = getProductById(product.getProductId()).get();
            product1.setAmounts(product.getAmounts());
            product1.setComposition(product.getComposition());
            product1.setName(product.getName());
            product1.setSteps(product.getSteps());
            System.out.println("reedsAanwezig");
            product.getComposition().forEach(p->{
              if(!p.getProduct().contains(product)) {
                  p.getProduct().add(product);
              }

                });

            System.out.println(product.getComposition().size());
          // Product product2= repo.save(product1);
            System.out.println("na het saven");

         //   System.out.println(product2.getComposition().size());
          // return
            return repo.save(product1);
        } else {
            System.out.println("saveOrUpdate");

            System.out.println(product.getComposition().size());
         return   repo.save(product);

        }
    }
}