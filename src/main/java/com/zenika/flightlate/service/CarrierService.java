package com.zenika.flightlate.service;

import com.zenika.flightlate.service.dto.CarrierDTO;
import java.util.List;

/**
 * Service Interface for managing Carrier.
 */
public interface CarrierService {

    /**
     * Save a carrier.
     *
     * @param carrierDTO the entity to save
     * @return the persisted entity
     */
    CarrierDTO save(CarrierDTO carrierDTO);

    /**
     *  Get all the carriers.
     *  
     *  @return the list of entities
     */
    List<CarrierDTO> findAll();

    /**
     *  Get the "id" carrier.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CarrierDTO findOne(Long id);

    /**
     *  Delete the "id" carrier.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
