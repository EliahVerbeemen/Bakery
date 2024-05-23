package kdg.be.Services.Repositories;

import kdg.be.Models.PurchaseOrder.PurchaseOrder;


public interface IPurchaseOrder {


 PurchaseOrder   saveOrUpdate(PurchaseOrder purchaseOrder);

}
