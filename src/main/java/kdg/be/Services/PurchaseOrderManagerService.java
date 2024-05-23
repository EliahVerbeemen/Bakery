package kdg.be.Services;

import kdg.be.Services.Repositories.IPurchaseOrder;
import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import kdg.be.Repositories.IPurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Component
public class PurchaseOrderManager implements IPurchaseOrder {

    Logger logger= LoggerFactory.getLogger(PurchaseOrderManager.class);


    private final IPurchaseOrderRepository IpurchaseOrderRepository;

    public PurchaseOrderManager(IPurchaseOrderRepository ipurchaseOrderRepository) {
        IpurchaseOrderRepository = ipurchaseOrderRepository;
    }

@Override
@Transactional
    public PurchaseOrder saveOrUpdate(PurchaseOrder purchaseOrder){
try{
       Optional<PurchaseOrder> optionalPurchaseOrder=IpurchaseOrderRepository.findPurchaseOrderByPurchaseOrderNumber( purchaseOrder.getPurchaseOrderNumber());
        if(optionalPurchaseOrder.isPresent()) {
            PurchaseOrder toUpdate = optionalPurchaseOrder.get();
            toUpdate.setPurchaseOrderNumber(purchaseOrder.getPurchaseOrderNumber());

            toUpdate.setOrderdate(purchaseOrder.getOrderdate());
            return IpurchaseOrderRepository.save(toUpdate);
        }      else return IpurchaseOrderRepository.save(purchaseOrder); }
        catch(Exception ex){

           logger.warn("Something went wrong while saving a purchasorder" + ex.getMessage());
   return new PurchaseOrder();
        }

    }







}
