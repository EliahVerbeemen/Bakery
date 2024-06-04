package kdg.be.Services.Interfaces;

import kdg.be.Models.Batch;
import kdg.be.Models.BatchState;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBatchService {

    Optional<Batch> findBatchByDate(LocalDate localDate);

    Batch save(Batch batch);

    List<Batch> findBatchByState(BatchState batchState);

    Batch saveOrUpdate(Batch batch);

    Optional<Batch> findBatchById(Long batchId);
}
