package com.metro.bus;

import com.metro.bus.model.bus.Agency;
import com.metro.bus.model.bus.Bus;
import com.metro.bus.model.bus.Stop;
import com.metro.bus.model.bus.Trip;
import com.metro.bus.model.user.Role;
import com.metro.bus.model.user.User;
import com.metro.bus.model.user.UserRoles;
import com.metro.bus.repository.bus.AgencyRepository;
import com.metro.bus.repository.bus.BusRepository;
import com.metro.bus.repository.bus.StopRepository;
import com.metro.bus.repository.bus.TripRepository;
import com.metro.bus.repository.user.RoleRepository;
import com.metro.bus.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MetroBusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetroBusApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository,
                           UserRepository userRepository,
                           StopRepository stopRepository,
                           TripRepository tripRepository,
                           BusRepository busRepository,
                           AgencyRepository agencyRepository) {
        return args -> {
            //Create Admin and Passenger Roles
            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRole(String.valueOf(UserRoles.ADMIN));
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByRole("PASSENGER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setRole("PASSENGER");
                roleRepository.save(userRole);
            }

            //Create an Admin user
            User admin = userRepository.findByEmail("wearegang369@gmail.com");
            if (admin == null) {
                admin = new User()
                        .setEmail("wearegang369@gmail.com")
                        .setPassword("{bcrypt}$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                        .setFirstName("Half")
                        .setLastName("Dev")
                        .setMobileNumber("9425094250")
                        .setRoles(Collections.singletonList(adminRole));
                userRepository.save(admin);
            }

            //Create four stops
            Stop stopA = stopRepository.findByCode("STPA");
            if (stopA == null) {
                stopA = new Stop()
                        .setName("Stop A")
                        .setDetail("Sydney")
                        .setCode("STPA");
                stopRepository.save(stopA);
            }

            Stop stopB = stopRepository.findByCode("STPB");
            if (stopB == null) {
                stopB = new Stop()
                        .setName("Stop B")
                        .setDetail("Melbourne")
                        .setCode("STPB");
                stopRepository.save(stopB);
            }

            Stop stopC = stopRepository.findByCode("STPC");
            if (stopC == null) {
                stopC = new Stop()
                        .setName("Stop C")
                        .setDetail("GoldCoast")
                        .setCode("STPC");
                stopRepository.save(stopC);
            }

            Stop stopD = stopRepository.findByCode("STPD");
            if (stopD == null) {
                stopD = new Stop()
                        .setName("Stop D")
                        .setDetail("Canberra")
                        .setCode("STPD");
                stopRepository.save(stopD);
            }

            //Create an Agency
            Agency agencyA = agencyRepository.findByCode("AGENCYA");
            if (agencyA == null) {
                agencyA = new Agency()
                        .setName("HALFDEV Agency")
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
                        .setMake("SKY-BUS")
                        .setAgencies(Collections.singletonList(agencyA)) //
                        .setCapacity(60);
                busRepository.save(busA);
            }
            if (agencyA.getBuses() == null) {
                Set<Bus> buses = new HashSet<>();
                agencyA.setBuses(buses);
                agencyA.getBuses().add(busA);
                // TODO Can not set java.lang.Long field com.metro.bus.model.bus.Bus.id to java.util.HashSet
                agencyRepository.save(agencyA);
            }

            //Create a Trip
            Bus bus = busRepository.findByCode("AGGGGGTEST");
            Trip trip = tripRepository.findByStopAndBuses(stopA, bus);
            if (trip == null) {
                trip = new Trip()
//                        .setSourceStop(stopA)
//                        .setDestStop(stopB)
                        .setStop(stopA)
                        .setStop(stopB)
                        .setBuses(bus)
                        .setAgencies(Collections.singletonList(agencyA))
                        .setFare(100)
                        .setJourneyTime(60);
                tripRepository.save(trip);
            }
        };
    }
}
