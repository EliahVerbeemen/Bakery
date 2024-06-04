package kdg.be.Repositories;

import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import kdg.be.Models.PurchaseOrder.PurchaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {

    public Optional<PurchaseOrder>findPurchaseOrderByPurchaseOrderNumber(Long purchaseOrderNumber);

}
