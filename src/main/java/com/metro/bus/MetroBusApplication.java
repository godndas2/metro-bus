package com.metro.bus;

import com.metro.bus.model.bus.Agency;
import com.metro.bus.model.bus.Bus;
import com.metro.bus.model.user.Role;
import com.metro.bus.model.user.User;
import com.metro.bus.repository.bus.AgencyRepository;
import com.metro.bus.repository.bus.BusRepository;
import com.metro.bus.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class MetroBusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetroBusApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           AgencyRepository agencyRepository,
                           BusRepository busRepository
                           ) {
        return args -> {
            //Create an Admin user
            User admin = userRepository.findByEmail("admin.agencya@gmail.com");
            if (admin == null) {
                admin = new User()
                        .setEmail("admin.agencya@gmail.com")
                        .setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                        .setFirstName("John")
                        .setLastName("Doe")
                        .setMobileNumber("9425094250")
                        .setRoles(Collections.singletonList("ADMIN")); // hmm
                userRepository.save(admin);
            }

            //Create an Agency
            Agency agencyA = agencyRepository.findByCode("AGENCYA");
            if (agencyA == null) {
                agencyA = new Agency()
                        .setName("Green Mile Agency")
                        .setCode("AGENCYA")
                        .setDetails("Reaching desitnations with ease")
                        .setUser(admin);
                agencyRepository.save(agencyA);
            }

            //Create a bus
            Bus busA = busRepository.findByCode("AGENCYA-1");
            if (busA == null) {
                busA = new Bus()
                        .setCode("AGENCYA-1")
                        .setAgencies(Collections.singletonList(agencyA))
                        .setCapacity(60);
                busRepository.save(busA);
            }
        };
    }
}
