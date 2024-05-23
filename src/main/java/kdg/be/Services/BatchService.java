package kdg.be.Services;

import kdg.be.Services.Repositories.IBatchManager;
import kdg.be.Models.Batch;
import kdg.be.Models.BatchState;
import kdg.be.Models.Product;
import kdg.be.Repositories.IBatchRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class BatchManager implements IBatchManager {

    private final IBatchRepository iBatchRepository;

    public BatchManager(IBatchRepository iBatchRepository) {
        this.iBatchRepository = iBatchRepository;

    }

    @Override
    public Optional<Batch> findBatchByDate(LocalDate localDate) {
        return iBatchRepository.findBatchBybatchDate(localDate);
    }

    @Override
    @Transactional
    public Batch save(Batch batch) {
        return iBatchRepository.save(batch);
    }

    @Override
    public List<Batch> findBatchByState(BatchState batchState) {
        return iBatchRepository.findBatchByBatchState(batchState);
    }

@Transactional
    public Batch saveOrUpdate(Batch batch) {
        if (iBatchRepository.existsById(batch.getBatchId())) {

            Batch bestaandebatch = iBatchRepository.findById(batch.getBatchId()).get();
            for(Map.Entry<Product, Integer> entry: batch.getProductsinBatch().entrySet()){
                if(bestaandebatch.getProductsinBatch().containsKey(entry.getKey())){

                    bestaandebatch.getProductsinBatch().replace(entry.getKey(),batch.getProductsinBatch().get(entry.getKey()));





                }
                else{

                    bestaandebatch.getProductsinBatch().put(entry.getKey(),entry.getValue());
                }




            }
            bestaandebatch.setProductsinBatch(batch.getProductsinBatch());
            bestaandebatch.setBatchState(batch.getBatchState());

           return iBatchRepository.save(bestaandebatch);

        } else {


            return iBatchRepository.save(batch);


        }


    }
    @Override
    public Optional<Batch> findBatchById(Long batchId){

        return iBatchRepository.findBatchByBatchId(batchId);



    }


}
