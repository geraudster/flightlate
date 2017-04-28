package com.zenika.flightlate.service;

import com.zenika.flightlate.service.dto.FlightDTO;
import java.util.List;

/**
 * Service Interface for managing Flight.
 */
public interface FlightService {

    /**
     * Save a flight.
     *
     * @param flightDTO the entity to save
     * @return the persisted entity
     */
    FlightDTO save(FlightDTO flightDTO);

    /**
     *  Get all the flights.
     *  
     *  @return the list of entities
     */
    List<FlightDTO> findAll();

    /**
     *  Get the "id" flight.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FlightDTO findOne(Long id);

    /**
     *  Delete the "id" flight.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
