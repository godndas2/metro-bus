package com.metro.bus.repository.bus;

import com.metro.bus.model.bus.Agency;
import com.metro.bus.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Long> {
    Agency findByCode(String agencyCode);
    Agency findByUser(User owner);
    Agency findByName(String name);
}
