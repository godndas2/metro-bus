package com.metro.bus.model.bus;

import com.metro.bus.model.user.User;
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
public class Ticket {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private int seatNumber;

    private boolean cancellable;

    private String journeyDate;

    // Relation
    private TripSchedule tripSchedule;

    // Relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
