package com.zenika.flightlate.service.impl;

import com.zenika.flightlate.service.FlightService;
import com.zenika.flightlate.domain.Flight;
import com.zenika.flightlate.repository.FlightRepository;
import com.zenika.flightlate.service.dto.FlightDTO;
import com.zenika.flightlate.service.mapper.FlightMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Flight.
 */
@Service
@Transactional
public class FlightServiceImpl implements FlightService{

    private final Logger log = LoggerFactory.getLogger(FlightServiceImpl.class);
    
    private final FlightRepository flightRepository;

    private final FlightMapper flightMapper;

    public FlightServiceImpl(FlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    /**
     * Save a flight.
     *
     * @param flightDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FlightDTO save(FlightDTO flightDTO) {
        log.debug("Request to save Flight : {}", flightDTO);
        Flight flight = flightMapper.flightDTOToFlight(flightDTO);
        flight = flightRepository.save(flight);
        FlightDTO result = flightMapper.flightToFlightDTO(flight);
        return result;
    }

    /**
     *  Get all the flights.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FlightDTO> findAll() {
        log.debug("Request to get all Flights");
        List<FlightDTO> result = flightRepository.findAll().stream()
            .map(flightMapper::flightToFlightDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one flight by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FlightDTO findOne(Long id) {
        log.debug("Request to get Flight : {}", id);
        Flight flight = flightRepository.findOne(id);
        FlightDTO flightDTO = flightMapper.flightToFlightDTO(flight);
        return flightDTO;
    }

    /**
     *  Delete the  flight by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Flight : {}", id);
        flightRepository.delete(id);
    }
}
