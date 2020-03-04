package com.metro.bus.model.bus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Trip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;

    private int fare;

    private int journeyTime;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "trip_id")
//    private Stop sourceStop;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "trip_id")
//    private Stop destStop;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "tripSchedule_id")
//    private TripSchedule tripSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    private Bus buses;

    @OneToMany(mappedBy = "trip")
    private List<Agency> agencies = new ArrayList<>();

}