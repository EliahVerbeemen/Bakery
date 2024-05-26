package kdg.be.Services.Interfaces;

import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import kdg.be.Models.PurchaseOrder.PurchaseProduct;

public interface IPurchaseOrderProductService {

    PurchaseProduct saveOrUpdate(PurchaseProduct purchaseProductToSave);

    PurchaseOrder saveOrUpdateAllProductsOfOrder(PurchaseOrder purchaseOrder);

}
