package com.metro.bus.repository.bus;

import com.metro.bus.model.bus.Agency;
import com.metro.bus.model.bus.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
    Bus findByCode(String busCode);
    Bus findByCodeAndAgencies(String busCode, Agency agency);
}
