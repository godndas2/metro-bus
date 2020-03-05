package com.metro.bus.service;

import com.metro.bus.dto.mapper.TripMapper;
import com.metro.bus.dto.model.bus.AgencyDto;
import com.metro.bus.dto.model.bus.BusDto;
import com.metro.bus.dto.model.bus.StopDto;
import com.metro.bus.dto.model.bus.TripDto;
import com.metro.bus.dto.model.user.UserDto;
import com.metro.bus.exception.BUSException;
import com.metro.bus.exception.EntityType;
import com.metro.bus.exception.ExceptionType;
import com.metro.bus.model.bus.Agency;
import com.metro.bus.model.bus.Bus;
import com.metro.bus.model.bus.Stop;
import com.metro.bus.model.bus.Trip;
import com.metro.bus.model.user.User;
import com.metro.bus.repository.bus.AgencyRepository;
import com.metro.bus.repository.bus.BusRepository;
import com.metro.bus.repository.bus.StopRepository;
import com.metro.bus.repository.bus.TripRepository;
import com.metro.bus.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.metro.bus.exception.EntityType.*;
import static com.metro.bus.exception.ExceptionType.*;

@RequiredArgsConstructor
@Service
public class BusReservationService {

    private final StopRepository stopRepository;
    private final UserRepository userRepository;
    private final AgencyRepository agencyRepository;
    private final BusRepository busRepository;
    private final TripRepository tripRepository;

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
//    @Transactional
    public AgencyDto updateAgency(AgencyDto agencyDto, BusDto busDto) {
        Agency agency = getAgency(agencyDto.getCode());
        if (agency != null) {
            if (busDto != null) {
                Optional<Bus> bus = Optional.ofNullable(busRepository.findByCodeAndAgencies(busDto.getCode(), agency));
                if (!bus.isPresent()) {
                    Bus busModel = new Bus()
                            .setAgencies(Collections.singletonList(agency))
                            .setCode(busDto.getCode())
                            .setCapacity(busDto.getCapacity())
                            .setMake(busDto.getMake());
                    busRepository.save(busModel);
                    if (agency.getBuses() == null) {
                        agency.setBuses(new HashSet<>());
                    }
                    agency.getBuses().add(busModel);
                    return modelMapper.map(agencyRepository.save(agency), AgencyDto.class);
                }
                throw exceptionWithId(BUS, DUPLICATE_ENTITY, "2", busDto.getCode(), agencyDto.getCode());
            } else {
                // update agency details case
                agency.setName(agencyDto.getName())
                       .setDetails(agencyDto.getDetails());
                return modelMapper.map(agencyRepository.save(agency), AgencyDto.class);
            }
        }
        throw exceptionWithId(AGENCY, ENTITY_NOT_FOUND, "2", agencyDto.getOwner().getEmail());
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

    public List<TripDto> getAgencyTrips(String agencyCode) {
        Agency agency = getAgency(agencyCode);
        if (agency != null) {
            List<Trip> agencyTrips = tripRepository.findByAgencies(agency);
            if (!agencyTrips.isEmpty()) {
                return agencyTrips
                        .stream()
                        .map(trip -> TripMapper.toTripDto(trip))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        }
        throw exception(AGENCY, ENTITY_NOT_FOUND, agencyCode);
    }

    /**
     * Creates two new Trips with the given information in tripDto object
     *
     */
    @Transactional
    public List<TripDto> addTrip(TripDto tripDto) {
        Stop sourceStop = getStop(tripDto.getStop().getCode()); //
        if (sourceStop != null) {
            Stop destinationStop = getStop(tripDto.getStop().getCode()); //
            if (destinationStop != null) {
                if (!sourceStop.getCode().equalsIgnoreCase(destinationStop.getCode())) {
                    Agency agency = getAgency(tripDto.getAgencyCode());
                    if (agency != null) {
                        Bus bus = getBus(tripDto.getBusCode());
                        if (bus != null) {
                            //Each new trip creation results in a to and a fro trip
                            List<TripDto> trips = new ArrayList<>(2);
                            Trip toTrip = new Trip()
//                                    .setSourceStop(sourceStop)
//                                    .setDestStop(destinationStop)
                                    .setStop(sourceStop)
                                    .setStop(destinationStop)
                                    .setAgencies(Collections.singletonList(agency))
                                    .setBuses(bus)
                                    .setJourneyTime(tripDto.getJourneyTime())
                                    .setFare(tripDto.getFare());
                            trips.add(TripMapper.toTripDto(tripRepository.save(toTrip)));

                            Trip froTrip = new Trip()
//                                    .setSourceStop(destinationStop)
//                                    .setDestStop(sourceStop)
                                    .setStop(destinationStop)
                                    .setStop(sourceStop)
                                    .setAgencies(Collections.singletonList(agency))
                                    .setBuses(bus)
                                    .setJourneyTime(tripDto.getJourneyTime())
                                    .setFare(tripDto.getFare());
                            trips.add(TripMapper.toTripDto(tripRepository.save(froTrip)));
                            return trips;
                        }
                        throw exception(BUS, ENTITY_NOT_FOUND, tripDto.getBusCode());
                    }
                    throw exception(AGENCY, ENTITY_NOT_FOUND, tripDto.getAgencyCode());
                }
                throw exception(TRIP, ENTITY_EXCEPTION, "");
            }
            throw exception(STOP, ENTITY_NOT_FOUND, tripDto.getStop().getCode());
        }
        throw exception(STOP, ENTITY_NOT_FOUND, tripDto.getStop().getCode());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    private Agency getAgency(String agencyCode) {
        return agencyRepository.findByCode(agencyCode);
    }

    private Bus getBus(String busCode) {
        return busRepository.findByCode(busCode);
    }

    private Stop getStop(String stopCode) {
        return stopRepository.findByCode(stopCode);
    }


    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BUSException.throwException(entityType, exceptionType, args);
    }

    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, String id, String... args) {
        return BUSException.throwExceptionWithId(entityType, exceptionType, id, args);
    }

}
