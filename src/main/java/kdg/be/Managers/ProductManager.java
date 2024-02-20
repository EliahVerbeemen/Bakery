package kdg.be.Managers;

import kdg.be.Models.Product;
import kdg.be.Repositories.IProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductManager implements IProductManager {
    private IProductRepository repo;

    public ProductManager(IProductRepository repository){
        repo = repository;
    }

    public List<Product> getAllProducts(){
        return repo.findAll();
    }
    public Optional<Product> getProductById(long id){
        return repo.findById(id);
    }

    public Product saveProduct(Product product){
        return repo.save(product);
    }
}
