package kdg.be.Managers;

import kdg.be.Models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface IBatchManager{

    public  Optional<Batch> VindBatchOpDatum(LocalDate localDate);

public Batch save(Batch batch) ;



}
