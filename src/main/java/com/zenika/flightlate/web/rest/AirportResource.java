package com.zenika.flightlate.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenika.flightlate.service.AirportService;
import com.zenika.flightlate.web.rest.util.HeaderUtil;
import com.zenika.flightlate.service.dto.AirportDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Airport.
 */
@RestController
@RequestMapping("/api")
public class AirportResource {

    private final Logger log = LoggerFactory.getLogger(AirportResource.class);

    private static final String ENTITY_NAME = "airport";
        
    private final AirportService airportService;

    public AirportResource(AirportService airportService) {
        this.airportService = airportService;
    }

    /**
     * POST  /airports : Create a new airport.
     *
     * @param airportDTO the airportDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new airportDTO, or with status 400 (Bad Request) if the airport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/airports")
    @Timed
    public ResponseEntity<AirportDTO> createAirport(@RequestBody AirportDTO airportDTO) throws URISyntaxException {
        log.debug("REST request to save Airport : {}", airportDTO);
        if (airportDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new airport cannot already have an ID")).body(null);
        }
        AirportDTO result = airportService.save(airportDTO);
        return ResponseEntity.created(new URI("/api/airports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /airports : Updates an existing airport.
     *
     * @param airportDTO the airportDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated airportDTO,
     * or with status 400 (Bad Request) if the airportDTO is not valid,
     * or with status 500 (Internal Server Error) if the airportDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/airports")
    @Timed
    public ResponseEntity<AirportDTO> updateAirport(@RequestBody AirportDTO airportDTO) throws URISyntaxException {
        log.debug("REST request to update Airport : {}", airportDTO);
        if (airportDTO.getId() == null) {
            return createAirport(airportDTO);
        }
        AirportDTO result = airportService.save(airportDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, airportDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /airports : get all the airports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of airports in body
     */
    @GetMapping("/airports")
    @Timed
    public List<AirportDTO> getAllAirports() {
        log.debug("REST request to get all Airports");
        return airportService.findAll();
    }

    /**
     * GET  /airports/:id : get the "id" airport.
     *
     * @param id the id of the airportDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the airportDTO, or with status 404 (Not Found)
     */
    @GetMapping("/airports/{id}")
    @Timed
    public ResponseEntity<AirportDTO> getAirport(@PathVariable Long id) {
        log.debug("REST request to get Airport : {}", id);
        AirportDTO airportDTO = airportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(airportDTO));
    }

    /**
     * DELETE  /airports/:id : delete the "id" airport.
     *
     * @param id the id of the airportDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/airports/{id}")
    @Timed
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        log.debug("REST request to delete Airport : {}", id);
        airportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
