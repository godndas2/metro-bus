package com.metro.bus.model.bus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "BUS")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Long id;

    @Column
    private String code;

    private int capacity;

    private String make;

    // Lazy
    @OneToMany(mappedBy = "buses")
    private List<Agency> agencies = new ArrayList<>();

    @OneToMany(mappedBy = "buses", cascade = CascadeType.ALL)
    private List<Trip> trips = new ArrayList<>();

}
