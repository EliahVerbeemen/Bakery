package kdg.be.Models.PurchaseOrder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import kdg.be.Services.PurchaseOrderDeserializer;
import kdg.be.Services.PurchaseProductDeserializer;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@JsonDeserialize(using = PurchaseOrderDeserializer.class)

public class PurchaseOrder {

    @OneToMany( fetch = FetchType.EAGER)
    @JsonDeserialize(using =PurchaseProductDeserializer.class)
    private List<PurchaseProduct> products=new ArrayList<>();

    private Long purchaseOrderNumber;

    private LocalDate orderdate;
    @Id
    @GeneratedValue
    private Long purchaseOrderId;


    public List<PurchaseProduct> getProducts() {
        return products;
    }

    public void setProducts(List<PurchaseProduct> products) {
        this.products = products;
    }

    public Long getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(Long purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public LocalDate getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    public PurchaseOrder() {
    }

    public PurchaseOrder(List<PurchaseProduct> products, Long purchaseOrderNumber, LocalDate orderdate) {
        this.products = products;
        this.purchaseOrderNumber = purchaseOrderNumber;
        this.orderdate = orderdate;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }
}
