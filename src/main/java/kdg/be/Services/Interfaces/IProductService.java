package kdg.be.Services.Interfaces;

import kdg.be.Models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Optional<Product> getProductById(long id);

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product saveOrUpdate(Product product);
}
