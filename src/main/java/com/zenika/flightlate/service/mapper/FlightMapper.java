package com.zenika.flightlate.service.mapper;

import com.zenika.flightlate.domain.*;
import com.zenika.flightlate.service.dto.FlightDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Flight and its DTO FlightDTO.
 */
@Mapper(componentModel = "spring", uses = {AirportMapper.class, CarrierMapper.class, })
public interface FlightMapper {

    @Mapping(source = "origin.id", target = "originId")
    @Mapping(source = "dest.id", target = "destId")
    @Mapping(source = "uniqueCarrier.id", target = "uniqueCarrierId")
    FlightDTO flightToFlightDTO(Flight flight);

    List<FlightDTO> flightsToFlightDTOs(List<Flight> flights);

    @Mapping(source = "originId", target = "origin")
    @Mapping(source = "destId", target = "dest")
    @Mapping(source = "uniqueCarrierId", target = "uniqueCarrier")
    Flight flightDTOToFlight(FlightDTO flightDTO);

    List<Flight> flightDTOsToFlights(List<FlightDTO> flightDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Flight flightFromId(Long id) {
        if (id == null) {
            return null;
        }
        Flight flight = new Flight();
        flight.setId(id);
        return flight;
    }
    

}
