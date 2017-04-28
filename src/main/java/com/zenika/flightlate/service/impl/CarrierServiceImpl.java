package com.zenika.flightlate.service.impl;

import com.zenika.flightlate.service.CarrierService;
import com.zenika.flightlate.domain.Carrier;
import com.zenika.flightlate.repository.CarrierRepository;
import com.zenika.flightlate.service.dto.CarrierDTO;
import com.zenika.flightlate.service.mapper.CarrierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Carrier.
 */
@Service
@Transactional
public class CarrierServiceImpl implements CarrierService{

    private final Logger log = LoggerFactory.getLogger(CarrierServiceImpl.class);
    
    private final CarrierRepository carrierRepository;

    private final CarrierMapper carrierMapper;

    public CarrierServiceImpl(CarrierRepository carrierRepository, CarrierMapper carrierMapper) {
        this.carrierRepository = carrierRepository;
        this.carrierMapper = carrierMapper;
    }

    /**
     * Save a carrier.
     *
     * @param carrierDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CarrierDTO save(CarrierDTO carrierDTO) {
        log.debug("Request to save Carrier : {}", carrierDTO);
        Carrier carrier = carrierMapper.carrierDTOToCarrier(carrierDTO);
        carrier = carrierRepository.save(carrier);
        CarrierDTO result = carrierMapper.carrierToCarrierDTO(carrier);
        return result;
    }

    /**
     *  Get all the carriers.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CarrierDTO> findAll() {
        log.debug("Request to get all Carriers");
        List<CarrierDTO> result = carrierRepository.findAll().stream()
            .map(carrierMapper::carrierToCarrierDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one carrier by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CarrierDTO findOne(Long id) {
        log.debug("Request to get Carrier : {}", id);
        Carrier carrier = carrierRepository.findOne(id);
        CarrierDTO carrierDTO = carrierMapper.carrierToCarrierDTO(carrier);
        return carrierDTO;
    }

    /**
     *  Delete the  carrier by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Carrier : {}", id);
        carrierRepository.delete(id);
    }
}
