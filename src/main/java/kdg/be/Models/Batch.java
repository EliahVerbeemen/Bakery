package kdg.be.Models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Entity
public class Batch {

    @Id
    @GeneratedValue
    private Long BatchId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate batchdatum;

   //Moet minstens 3 toestanden kennem openstaan , in bereiding en  bereid.
    private static boolean InPreparation=false;

    public boolean isInPreparation() {
        return InPreparation;
    }

    public void setIsInPreperation(boolean inPreparation) {
        InPreparation = inPreparation;
    }

    @OneToMany

    private List<BatchProduct> ProducsToPrepare=new ArrayList<>();

    public Long getBatchId() {
        return BatchId;
    }

    public void setBatchId(Long batchId) {
        BatchId = batchId;
    }

    public LocalDate getBatchdatum() {
        return batchdatum;
    }

    public Batch(LocalDate batchdate, List<BatchProduct> producsToPrepare) {
        this.batchdatum = batchdate;
        ProducsToPrepare = producsToPrepare;
    }

    public Batch(){

    }

    public Batch(LocalDate localDate){

        this.batchdatum=localDate;
    }

    public void setBatchdatum(LocalDate batchdatum) {
        batchdatum = batchdatum;
    }

    public List<BatchProduct> getTeBereidenProducten() {
        return ProducsToPrepare;
    }

    public void productsToprepare(ArrayList<BatchProduct> productsToPrepare) {
        ProducsToPrepare = productsToPrepare;
    }
}
