package com.metro.bus.service;

import com.metro.bus.dto.model.bus.StopDto;
import com.metro.bus.repository.bus.StopRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BusReservationService {

    private final StopRepository stopRepository;
    private final ModelMapper modelMapper;

    public Set<StopDto> getAllStops() {
        return stopRepository.findAll()
                .stream()
                .map(stop -> modelMapper.map(stop, StopDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

}
