package com.zenika.flightlate.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zenika.flightlate.service.CarrierService;
import com.zenika.flightlate.web.rest.util.HeaderUtil;
import com.zenika.flightlate.service.dto.CarrierDTO;
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
 * REST controller for managing Carrier.
 */
@RestController
@RequestMapping("/api")
public class CarrierResource {

    private final Logger log = LoggerFactory.getLogger(CarrierResource.class);

    private static final String ENTITY_NAME = "carrier";
        
    private final CarrierService carrierService;

    public CarrierResource(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    /**
     * POST  /carriers : Create a new carrier.
     *
     * @param carrierDTO the carrierDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carrierDTO, or with status 400 (Bad Request) if the carrier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carriers")
    @Timed
    public ResponseEntity<CarrierDTO> createCarrier(@RequestBody CarrierDTO carrierDTO) throws URISyntaxException {
        log.debug("REST request to save Carrier : {}", carrierDTO);
        if (carrierDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carrier cannot already have an ID")).body(null);
        }
        CarrierDTO result = carrierService.save(carrierDTO);
        return ResponseEntity.created(new URI("/api/carriers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carriers : Updates an existing carrier.
     *
     * @param carrierDTO the carrierDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carrierDTO,
     * or with status 400 (Bad Request) if the carrierDTO is not valid,
     * or with status 500 (Internal Server Error) if the carrierDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carriers")
    @Timed
    public ResponseEntity<CarrierDTO> updateCarrier(@RequestBody CarrierDTO carrierDTO) throws URISyntaxException {
        log.debug("REST request to update Carrier : {}", carrierDTO);
        if (carrierDTO.getId() == null) {
            return createCarrier(carrierDTO);
        }
        CarrierDTO result = carrierService.save(carrierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carrierDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carriers : get all the carriers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carriers in body
     */
    @GetMapping("/carriers")
    @Timed
    public List<CarrierDTO> getAllCarriers() {
        log.debug("REST request to get all Carriers");
        return carrierService.findAll();
    }

    /**
     * GET  /carriers/:id : get the "id" carrier.
     *
     * @param id the id of the carrierDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carrierDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carriers/{id}")
    @Timed
    public ResponseEntity<CarrierDTO> getCarrier(@PathVariable Long id) {
        log.debug("REST request to get Carrier : {}", id);
        CarrierDTO carrierDTO = carrierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carrierDTO));
    }

    /**
     * DELETE  /carriers/:id : delete the "id" carrier.
     *
     * @param id the id of the carrierDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carriers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarrier(@PathVariable Long id) {
        log.debug("REST request to delete Carrier : {}", id);
        carrierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
