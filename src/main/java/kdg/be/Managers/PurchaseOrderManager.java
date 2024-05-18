package kdg.be.Managers;

import kdg.be.Managers.Repositories.IPurchaseOrder;
import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import kdg.be.Repositories.IPurchaseOrderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class PurchaseOrderManager implements IPurchaseOrder {

    private final IPurchaseOrderRepository IpurchaseOrderRepository;

    public PurchaseOrderManager(IPurchaseOrderRepository ipurchaseOrderRepository) {
        IpurchaseOrderRepository = ipurchaseOrderRepository;
    }


    public PurchaseOrder saveOrUpdate(PurchaseOrder purchaseOrder){

        Optional<PurchaseOrder> optionalPurchaseOrder=IpurchaseOrderRepository.findPurchaseOrderByPurchaseOrderNumber( purchaseOrder.getPurchaseOrderNumber());
        if(optionalPurchaseOrder.isPresent()){
            PurchaseOrder toUpdate=optionalPurchaseOrder.get();
            toUpdate.setPurchaseOrderNumber(purchaseOrder.getPurchaseOrderNumber());
            toUpdate.setProducts(purchaseOrder.getProducts());
            toUpdate.setOrderdate(purchaseOrder.getOrderdate());
            return IpurchaseOrderRepository.save(toUpdate);

        }
        else return IpurchaseOrderRepository.save(purchaseOrder);
    }







}
