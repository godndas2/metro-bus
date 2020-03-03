package com.metro.bus;

import com.metro.bus.model.user.Role;
import com.metro.bus.model.user.User;
import com.metro.bus.model.user.UserRoles;
import com.metro.bus.repository.user.RoleRepository;
import com.metro.bus.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class MetroBusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetroBusApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository,
                           UserRepository userRepository) {
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
            User admin = userRepository.findByEmail("admin.agencya@gmail.com");
            if (admin == null) {
                admin = new User()
                        .setEmail("admin.agencya@gmail.com")
                        .setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                        .setFirstName("John")
                        .setLastName("Doe")
                        .setMobileNumber("9425094250")
                        .setRoles(new ArrayList<>(Arrays.asList(adminRole)));
                userRepository.save(admin);
            }
        };
    }
}
