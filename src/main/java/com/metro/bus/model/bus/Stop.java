package com.metro.bus.model.bus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Stop {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(unique = true)
    private String code;

    private String name;

    private String detail;

//    @OneToMany(mappedBy = "tripSchedule")
//    private List<Trip> trips = new ArrayList<>();
}