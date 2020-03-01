package com.metro.bus.model.bus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Trip {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private int fare;

    private int journeyTime;

    // Relation
    private Stop sourceStop;

    // Relation
    private Stop destStop;

    // Relation
    private Bus bus;

    // Relation
    private Agency agency;

}