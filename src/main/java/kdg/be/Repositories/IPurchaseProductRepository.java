package kdg.be.Repositories;

import kdg.be.Models.PurchaseOrder.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseProductRepository extends JpaRepository<PurchaseProduct,Long> {
}
