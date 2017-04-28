package com.zenika.flightlate.repository;

import com.zenika.flightlate.domain.Carrier;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Carrier entity.
 */
@SuppressWarnings("unused")
public interface CarrierRepository extends JpaRepository<Carrier,Long> {

}
