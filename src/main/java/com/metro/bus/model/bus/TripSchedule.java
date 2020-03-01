package com.metro.bus.model.bus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TripSchedule {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    // Relation
    private Trip tripDetail;

    // Lazy
    private List<Ticket> ticketsSold;

    private String tripDate;

    private int availableSeats;
}
