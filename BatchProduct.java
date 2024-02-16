package com.example.bakkery.Modellen;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class BatchProduct {

    @Id
    @GeneratedValue
    private Long batchProductId;

    public Long getBatchProductId() {
        return batchProductId;
    }

    public void setBatchProductId(Long batchProductId) {
        this.batchProductId = batchProductId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getBaktijd() {
        return baktijd;
    }

    public void setBaktijd(LocalDateTime baktijd) {
        this.baktijd = baktijd;
    }

    public LocalDateTime getEindbakken() {
        return eindbakken;
    }

    public void setEindbakken(LocalDateTime eindbakken) {
        this.eindbakken = eindbakken;
    }

    @OneToOne
    private Product product;

    private LocalDateTime baktijd;

    //als bakker geef ik aan wanneer het product gebakken is.
    private LocalDateTime eindbakken;


}
