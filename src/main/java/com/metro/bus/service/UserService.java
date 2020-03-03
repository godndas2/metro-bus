package com.metro.bus.service;

import com.metro.bus.dto.mapper.UserMapper;
import com.metro.bus.dto.model.user.UserDto;
import com.metro.bus.exception.BUSException;
import com.metro.bus.exception.EntityType;
import com.metro.bus.exception.ExceptionType;
import com.metro.bus.model.user.Role;
import com.metro.bus.model.user.User;
import com.metro.bus.model.user.UserRoles;
import com.metro.bus.repository.user.RoleRepository;
import com.metro.bus.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.metro.bus.exception.EntityType.USER;
import static com.metro.bus.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.metro.bus.exception.ExceptionType.ENTITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDto signup(UserDto userDto) {
        Role userRole;
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            if (userDto.isAdmin()) {
                userRole = roleRepository.findByRole(UserRoles.ADMIN.name());
            } else {
                userRole = roleRepository.findByRole(UserRoles.PASSENGER.name());
            }
            user = new User()
                    .setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setRoles(new ArrayList<>(Arrays.asList(userRole)))
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(userRepository.save(user));
        }
        throw exception(USER, DUPLICATE_ENTITY, userDto.getEmail());
    }

    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }
        throw exception(USER, ENTITY_NOT_FOUND, email);
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BUSException.throwException(entityType, exceptionType, args);
    }

}
