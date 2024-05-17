package kdg.be.Managers;

import kdg.be.Managers.Repositories.IPurchaseOrderProduct;
import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import kdg.be.Repositories.IPurchaseProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class PurchaseOrderProductManager implements IPurchaseOrderProduct {


    private final IPurchaseProductRepository iPurchaseProductRepository;

    public PurchaseOrderProductManager(IPurchaseProductRepository iPurchaseProductRepository) {
        this.iPurchaseProductRepository = iPurchaseProductRepository;
    }



}
