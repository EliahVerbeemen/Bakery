package kdg.be.Managers;

import kdg.be.DTO.OrdersFromClient;
import kdg.be.Models.Batch;
import kdg.be.Repositories.IBatchRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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


    public Batch AddToBatch(OrdersFromClient ordersFromClient){

    // De batch wordt 's avonds afgesloten status gebakken
      //Shrijf in JPArepositoy  een query of er een openstaande batch is...
        if(true){

         }
        else {
            if (LocalTime.now().isBefore(LocalTime.of(22, 0))) {
Batch batch=new Batch(/*LocalDateTime.now()*/);
        ordersFromClient.getProducts().forEach((k,v)-> {

//   batch.getTeBereidenProducten().add();

        });}}
        return null;
    };}
