package com.metro.bus.controller.api;

import com.metro.bus.dto.response.Response;
import com.metro.bus.service.BusReservationService;
import com.metro.bus.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author halfdev
* @since 2020-03-04
* Operations pertaining to agency management and ticket issue in the Metro Bus application"
*/
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
@Api(value = "bus-application")
public class BusReservationController {

    private final BusReservationService busReservationService;
    private final UserService userService;

    @GetMapping("/stops")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response getAllStops() {
        return Response
                .ok()
                .setPayload(busReservationService.getAllStops());
    }

}
