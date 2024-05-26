package kdg.be.Services;

import kdg.be.Services.Interfaces.IBatchProductService;
import kdg.be.Models.BatchProduct;
import kdg.be.Repositories.IBatchproductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component

public class BatchProductService implements IBatchProductService {

    private final IBatchproductRepository IBatchproductRepository;

    public BatchProductService(IBatchproductRepository IBatchepository) {
        this.IBatchproductRepository = IBatchepository;
    }

    @Override
    @Transactional
    public BatchProduct save(BatchProduct batchProduct) {
        return this.IBatchproductRepository.save(batchProduct);
    }


}
