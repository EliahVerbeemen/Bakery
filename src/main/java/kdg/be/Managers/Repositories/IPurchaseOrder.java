package kdg.be.Managers.Repositories;

import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import org.springframework.stereotype.Service;


public interface IPurchaseOrder {


 PurchaseOrder   saveOrUpdate(PurchaseOrder purchaseOrder);

}
