package com.metro.bus.service;

import com.metro.bus.dto.model.bus.AgencyDto;
import com.metro.bus.dto.model.bus.BusDto;
import com.metro.bus.dto.model.bus.StopDto;
import com.metro.bus.dto.model.user.UserDto;
import com.metro.bus.exception.BUSException;
import com.metro.bus.exception.EntityType;
import com.metro.bus.exception.ExceptionType;
import com.metro.bus.model.bus.Agency;
import com.metro.bus.model.user.User;
import com.metro.bus.repository.bus.AgencyRepository;
import com.metro.bus.repository.bus.StopRepository;
import com.metro.bus.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.metro.bus.exception.EntityType.AGENCY;
import static com.metro.bus.exception.EntityType.USER;
import static com.metro.bus.exception.ExceptionType.ENTITY_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class BusReservationService {

    private final StopRepository stopRepository;
    private final UserRepository userRepository;
    private final AgencyRepository agencyRepository;
    private final ModelMapper modelMapper;

    public Set<StopDto> getAllStops() {
        return stopRepository.findAll()
                .stream()
                .map(stop -> modelMapper.map(stop, StopDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /*
    * Updates the agency with given Bus information
    * */
    @Transactional
    public AgencyDto updateAgency(AgencyDto agencyDto, BusDto busDto) {
        return null;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    private Agency getAgency(String agencyCode) {
        return agencyRepository.findByCode(agencyCode);
    }

    public AgencyDto getAgency(UserDto userDto) {
        User user = getUser(userDto.getEmail());
        if (user != null) {
            Optional<Agency> agency = Optional.ofNullable(agencyRepository.findByUser(user));
            if (agency.isPresent()) {
                return modelMapper.map(agency.get(), AgencyDto.class);
            }
            throw exceptionWithId(AGENCY, ENTITY_NOT_FOUND, "2", user.getEmail());
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BUSException.throwException(entityType, exceptionType, args);
    }

    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, String id, String... args) {
        return BUSException.throwExceptionWithId(entityType, exceptionType, id, args);
    }

}
