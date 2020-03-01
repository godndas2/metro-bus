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
public class Bus {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bus_id")
    private String id;

    @Column(unique = true)
    private String code;

    private int capacity;

    private String make;

    // Lazy
    @OneToMany(mappedBy = "buses")
    private List<Agency> agencies = new ArrayList<>();

    @OneToMany(mappedBy = "buses")
    private List<Trip> trips = new ArrayList<>();

}
