package kdg.be.Managers;

import kdg.be.Models.Batch;
import kdg.be.Repositories.IBatchRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
@Component
public class BatchManager implements IBatchManager{

  private IBatchRepository iBatchRepository;

  public BatchManager(IBatchRepository iBatchRepository){
      this.iBatchRepository=iBatchRepository;

  }

    @Override
    public Optional<Batch> VindBatchOpDatum(LocalDate localDate) {
        return iBatchRepository.findBatchByBatchdatum(localDate);
    }

    @Override
    public Batch save(Batch batch) {
        return iBatchRepository.save(batch);
    }
}
