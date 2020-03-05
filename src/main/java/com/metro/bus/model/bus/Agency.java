package com.metro.bus.model.bus;

import com.metro.bus.model.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "AGENCY")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agency_id")
    private Long id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    private String name;

    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Bus.class)
    @JoinColumn(name = "bus_id", referencedColumnName = "bus_id")
    private Set<Bus> buses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
