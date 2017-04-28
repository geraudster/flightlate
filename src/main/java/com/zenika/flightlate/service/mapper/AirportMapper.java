package com.zenika.flightlate.service.mapper;

import com.zenika.flightlate.domain.*;
import com.zenika.flightlate.service.dto.AirportDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Airport and its DTO AirportDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AirportMapper {

    AirportDTO airportToAirportDTO(Airport airport);

    List<AirportDTO> airportsToAirportDTOs(List<Airport> airports);

    Airport airportDTOToAirport(AirportDTO airportDTO);

    List<Airport> airportDTOsToAirports(List<AirportDTO> airportDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Airport airportFromId(Long id) {
        if (id == null) {
            return null;
        }
        Airport airport = new Airport();
        airport.setId(id);
        return airport;
    }
    

}
