package com.zenika.flightlate.service;

import com.zenika.flightlate.service.dto.AirportDTO;
import java.util.List;

/**
 * Service Interface for managing Airport.
 */
public interface AirportService {

    /**
     * Save a airport.
     *
     * @param airportDTO the entity to save
     * @return the persisted entity
     */
    AirportDTO save(AirportDTO airportDTO);

    /**
     *  Get all the airports.
     *  
     *  @return the list of entities
     */
    List<AirportDTO> findAll();

    /**
     *  Get the "id" airport.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AirportDTO findOne(Long id);

    /**
     *  Delete the "id" airport.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
