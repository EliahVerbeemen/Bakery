package kdg.be.Managers;

import kdg.be.Models.Batch;

import java.time.LocalDate;
import java.util.Optional;

public interface IBatchManager{

    public  Optional<Batch> findBatchByDate(LocalDate localDate);

public Batch save(Batch batch) ;



}
