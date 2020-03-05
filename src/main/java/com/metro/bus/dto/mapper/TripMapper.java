package com.metro.bus.dto.mapper;

import com.metro.bus.dto.model.bus.TripDto;
import com.metro.bus.model.bus.Trip;

public class TripMapper {
    public static TripDto toTripDto(Trip trip) {
        return new TripDto()
                .setId(String.valueOf(trip.getId())) //
                .setStop(trip.getStop())
//                .setAgencyCode(trip.getSourceStop().getCode())
//                .setSourceStopName(trip.getSourceStop().getName())
//                .setDestinationStopCode(trip.getDestStop().getCode())
//                .setDestinationStopName(trip.getDestStop().getName())
                .setBusCode(trip.getBuses().getCode())
                .setJourneyTime(trip.getJourneyTime())
                .setFare(trip.getFare());
    }
}
