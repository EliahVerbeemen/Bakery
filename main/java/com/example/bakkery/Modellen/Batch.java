package com.example.bakkery.Modellen;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Map;


//Welke producten moet de bakkerij vandaag maken?
@Entity
public class Batch {

    @Id
    @GeneratedValue
    private Long batchId;


    private LocalDate batchDatum;

    @ElementCollection
    private Map<BatchProduct,Integer>teMakenProducten;
}
