package kdg.be.Services;

import kdg.be.Models.Product;
import kdg.be.Repositories.IProductRepository;
import kdg.be.Services.Interfaces.IIngredientService;
import kdg.be.Services.Interfaces.IProductService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final IIngredientService ingredientService;

    public ProductService(IProductRepository repository, IIngredientService repository2) {
        productRepository = repository;
        this.ingredientService = repository2;
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

    @Transactional
    @Override
    public Product saveOrUpdate(Product product) {

        if (product.getProductId() != null && getProductById(product.getProductId()).isPresent()) {
            Product product1 = getProductById(product.getProductId()).get();
            product1.setName(product.getName());
            product1.setSteps(product.getSteps());
            product1.setComposition(product.getComposition());
            product1.setAmounts(product.getAmounts());
            product1.getComposition().forEach(e -> e.getProduct().remove(product1));
            product1.setComposition(new ArrayList<>());
            product1.setComposition(product.getComposition());
            product.getComposition().forEach(e -> {
                e.getProduct().add(product1);
                ingredientService.saveIngredient(e);
            });

            return productRepository.save(product1);
        } else {
            Product p1 = productRepository.save(product);
            product.getComposition().forEach(e -> {
                e.getProduct().add(product);
                ingredientService.saveIngredient(e);
            });
            return p1;
        }
    }
}
