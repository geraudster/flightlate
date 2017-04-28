package com.zenika.flightlate.service.impl;

import com.zenika.flightlate.service.AirportService;
import com.zenika.flightlate.domain.Airport;
import com.zenika.flightlate.repository.AirportRepository;
import com.zenika.flightlate.service.dto.AirportDTO;
import com.zenika.flightlate.service.mapper.AirportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Airport.
 */
@Service
@Transactional
public class AirportServiceImpl implements AirportService{

    private final Logger log = LoggerFactory.getLogger(AirportServiceImpl.class);
    
    private final AirportRepository airportRepository;

    private final AirportMapper airportMapper;

    public AirportServiceImpl(AirportRepository airportRepository, AirportMapper airportMapper) {
        this.airportRepository = airportRepository;
        this.airportMapper = airportMapper;
    }

    /**
     * Save a airport.
     *
     * @param airportDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AirportDTO save(AirportDTO airportDTO) {
        log.debug("Request to save Airport : {}", airportDTO);
        Airport airport = airportMapper.airportDTOToAirport(airportDTO);
        airport = airportRepository.save(airport);
        AirportDTO result = airportMapper.airportToAirportDTO(airport);
        return result;
    }

    /**
     *  Get all the airports.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AirportDTO> findAll() {
        log.debug("Request to get all Airports");
        List<AirportDTO> result = airportRepository.findAll().stream()
            .map(airportMapper::airportToAirportDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one airport by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AirportDTO findOne(Long id) {
        log.debug("Request to get Airport : {}", id);
        Airport airport = airportRepository.findOne(id);
        AirportDTO airportDTO = airportMapper.airportToAirportDTO(airport);
        return airportDTO;
    }

    /**
     *  Delete the  airport by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Airport : {}", id);
        airportRepository.delete(id);
    }
}
