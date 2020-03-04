package com.metro.bus.repository.bus;

import com.metro.bus.model.bus.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository extends JpaRepository<Stop, Long> {
    Stop findByCode(String code);
}
