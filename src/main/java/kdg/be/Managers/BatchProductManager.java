package kdg.be.Managers;

import kdg.be.Managers.Repositories.IBatchproductManager;
import kdg.be.Models.BatchProduct;
import kdg.be.Repositories.IBatchproductRepository;
import org.springframework.stereotype.Component;

@Component

public class BatchProductManager implements IBatchproductManager {

    private IBatchproductRepository IBatchproductRepository;

    public BatchProductManager(IBatchproductRepository IBatchepository) {
        this.IBatchproductRepository = IBatchepository;
    }

    @Override
    public BatchProduct save(BatchProduct batchProduct) {

        return this.IBatchproductRepository.save(batchProduct);
    }


}
