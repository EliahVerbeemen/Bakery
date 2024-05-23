package kdg.be.Services;

import kdg.be.Services.Repositories.IBatchproductManager;
import kdg.be.Models.BatchProduct;
import kdg.be.Repositories.IBatchproductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component

public class BatchProductManager implements IBatchproductManager {

    private final IBatchproductRepository IBatchproductRepository;

    public BatchProductManager(IBatchproductRepository IBatchepository) {
        this.IBatchproductRepository = IBatchepository;
    }

    @Override
    @Transactional
    public BatchProduct save(BatchProduct batchProduct) {

        return this.IBatchproductRepository.save(batchProduct);
    }


}
