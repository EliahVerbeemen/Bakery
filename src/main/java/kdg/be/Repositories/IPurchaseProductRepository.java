package kdg.be.Repositories;

import kdg.be.Models.PurchaseOrder.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPurchaseProductRepository extends JpaRepository<PurchaseProduct, Long> {

    Optional<PurchaseProduct> findPurchaseProductByProductNumber(String productNumber);


}
