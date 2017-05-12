package com.zenika.flightlate.web.rest;

import com.zenika.flightlate.domain.Flight;
import com.zenika.flightlate.domain.FlightPrediction;
import com.zenika.flightlate.domain.FlightView;
import com.zenika.flightlate.service.FlightislateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by geraud on 28/04/17.
 */
@RestController
@RequestMapping("/api")
public class FlightisLateResource {
    private final FlightislateService service;

    public FlightisLateResource(FlightislateService service) {
        this.service = service;
    }

    @PostMapping("/flight")
    public FlightPrediction islate(@RequestBody FlightView flight) {
        return service.predictFlight(flight);
    }
}
