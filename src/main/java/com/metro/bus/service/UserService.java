package com.metro.bus.service;

import com.metro.bus.dto.mapper.UserMapper;
import com.metro.bus.dto.model.user.UserDto;
import com.metro.bus.exception.BUSException;
import com.metro.bus.exception.EntityType;
import com.metro.bus.exception.ExceptionType;
import com.metro.bus.model.user.Role;
import com.metro.bus.model.user.User;
import com.metro.bus.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.metro.bus.exception.ExceptionType.DUPLICATE_ENTITY;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    public UserDto signup(UserDto userDto) {
        User userRole;
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            if (userDto.isAdmin()) {
                userRole = userRepository.findByRoles(Role.ADMIN.name());
            } else {
                userRole = userRepository.findByRoles(Role.PASSENGER.name());
            }
            user = new User()
                    .setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setRoles(userRole.getRoles()) // Hmm..
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(userRepository.save(user));
        }
        throw exception(EntityType.USER, DUPLICATE_ENTITY, userDto.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BUSException.throwException(entityType, exceptionType, args);
    }

}
