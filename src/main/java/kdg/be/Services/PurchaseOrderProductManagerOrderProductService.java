package kdg.be.Services;

import kdg.be.Services.Repositories.IPurchaseOrderProduct;
import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import kdg.be.Models.PurchaseOrder.PurchaseProduct;
import kdg.be.Repositories.IPurchaseProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Component
public class PurchaseOrderProductManager implements IPurchaseOrderProduct {


    private final IPurchaseProductRepository iPurchaseProductRepository;

    public PurchaseOrderProductManager(IPurchaseProductRepository iPurchaseProductRepository) {
        this.iPurchaseProductRepository = iPurchaseProductRepository;
    }

@Transactional
@Override
public PurchaseProduct saveOrUpdate(PurchaseProduct purchaseProductToSave){

      Optional<PurchaseProduct> optionalPurchaseProduct= iPurchaseProductRepository.findPurchaseProductByProductNumber(purchaseProductToSave.getProductNumber());
        if(optionalPurchaseProduct.isPresent()){
            PurchaseProduct purchaseProduct=optionalPurchaseProduct.get();
            purchaseProduct.setSpecialInstruction(purchaseProduct.getSpecialInstruction());
            purchaseProduct.set_ProductStatus(purchaseProduct.get_ProductStatus());
            purchaseProduct.setComposition(purchaseProduct.getComposition());
            purchaseProduct.setSteps(purchaseProduct.getSteps());
            purchaseProduct.setName(purchaseProduct.getName());
           return iPurchaseProductRepository.save(purchaseProduct);
        }

        else{
            return iPurchaseProductRepository.save(purchaseProductToSave);
        }

    }
@Transactional
@Override
    public PurchaseOrder saveOrUpdateAllProductsOfOrder(PurchaseOrder purchaseOrder){

     purchaseOrder.getProducts().forEach(this::saveOrUpdate);
    return purchaseOrder;
    }

    public List<PurchaseProduct> findAll(){


        return iPurchaseProductRepository.findAll();
    }



}
