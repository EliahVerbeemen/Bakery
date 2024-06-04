package kdg.be.Services.Interfaces;

import kdg.be.Models.PurchaseOrder.PurchaseOrder;


public interface IPurchaseOrderService {
    PurchaseOrder saveOrUpdate(PurchaseOrder purchaseOrder);

}
