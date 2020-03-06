package com.metro.bus.repository.bus;

import com.metro.bus.model.bus.Agency;
import com.metro.bus.model.bus.Bus;
import com.metro.bus.model.bus.Stop;
import com.metro.bus.model.bus.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip findByStopAndBuses(Stop way, Bus bus);
//    List<Trip> findAllBySourceStopAndDestStop(Stop source, Stop destination);
    List<Trip> findByAgencies(Agency agency);

}
