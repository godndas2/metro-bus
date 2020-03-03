package com.metro.bus.model.bus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TripSchedule {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tripSchedule_id")
    private String id;

    @OneToMany(mappedBy = "tripSchedule")
    private List<Trip> trips = new ArrayList<>();

    // Lazy
    @OneToMany(mappedBy = "tripSchedule")
    private List<Ticket> tickets = new ArrayList<>();

    private String tripDate;

    private int availableSeats;
}
