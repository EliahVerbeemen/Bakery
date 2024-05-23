package kdg.be.Services.Repositories;

import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import kdg.be.Models.PurchaseOrder.PurchaseProduct;

public interface IPurchaseOrderProduct {

    PurchaseProduct saveOrUpdate(PurchaseProduct purchaseProductToSave);
    PurchaseOrder saveOrUpdateAllProductsOfOrder(PurchaseOrder purchaseOrder);

}
