package kdg.be.Managers;

import kdg.be.DTO.OrdersFromClientDTO;
import kdg.be.Models.Batch;
import kdg.be.Repositories.IBatchRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public Batch save(Batch batch) {
        return iBatchRepository.save(batch);
    }


    public Batch AddToBatch(OrdersFromClientDTO ordersFromClientDTO) {

        // De batch wordt 's avonds afgesloten status gebakken
        //Shrijf in JPArepositoy  een query of er een openstaande batch is...
        if (LocalTime.now().isBefore(LocalTime.of(22, 0))) {
            Optional<Batch> batchOptional = findBatchByDate(LocalDate.now());
            if (batchOptional.isPresent()) {
                Batch dailyBatch = batchOptional.get();
                ordersFromClientDTO.getProducts().forEach((product, amount) -> {
                    //dailyBatch.getProductsToPrepare().add()
                });
                return dailyBatch;
            } else {
                Batch batch = new Batch(LocalDate.now());
                ordersFromClientDTO.getProducts().forEach((product, amount) -> {
                    //batch.getProductsToPrepare().add();
                });
                return batch;
            }

        } else {
            Optional<Batch> batchOptional = findBatchByDate(LocalDate.now().plusDays(1));
            if (batchOptional.isPresent()) {
                Batch dailyBatch = batchOptional.get();
                ordersFromClientDTO.getProducts().forEach((product, amount) -> {
                    //dailyBatch.getProductsToPrepare().add()
                });
                return dailyBatch;
            } else {
                Batch batch = new Batch(LocalDate.now().plusDays(1));
                ordersFromClientDTO.getProducts().forEach((product, amount) -> {
                    //batch.getProductsToPrepare().add();
                });
                return batch;
            }
        }
    // TODO: Optimize method to avoid duplicate code?
    }
}
