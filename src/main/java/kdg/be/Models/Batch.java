package kdg.be.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Batch {

    //Properties
    //Moet minstens 3 toestanden kennem openstaan , in bereiding en  bereid.
    private static boolean InPreparation = false;
    @Id
    @GeneratedValue
    private Long BatchId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate batchDate;
    @OneToMany

    private List<BatchProduct> productsToPrepare = new ArrayList<>();

    public Batch(LocalDate batchdate, List<BatchProduct> productsToPrepare) {
        this.batchDate = batchdate;
        this.productsToPrepare = productsToPrepare;
    }

    public Batch() {

    }

    public Batch(LocalDate localDate) {

        this.batchDate = localDate;
    }

    public boolean isInPreparation() {
        return InPreparation;
    }

    public void setIsInPreperation(boolean inPreparation) {
        InPreparation = inPreparation;
    }

    public Long getBatchId() {
        return BatchId;
    }

    public void setBatchId(Long batchId) {
        BatchId = batchId;
    }

    public LocalDate getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(LocalDate batchdatum) {
        batchDate = batchdatum;
    }

    public List<BatchProduct> getProductsToPrepare() {
        return productsToPrepare;
    }

    public void productsToPrepare(ArrayList<BatchProduct> productsToPrepare) {
        this.productsToPrepare = productsToPrepare;
    }
}
