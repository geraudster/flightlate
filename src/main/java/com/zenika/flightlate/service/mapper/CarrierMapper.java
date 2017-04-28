package com.zenika.flightlate.service.mapper;

import com.zenika.flightlate.domain.*;
import com.zenika.flightlate.service.dto.CarrierDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Carrier and its DTO CarrierDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CarrierMapper {

    CarrierDTO carrierToCarrierDTO(Carrier carrier);

    List<CarrierDTO> carriersToCarrierDTOs(List<Carrier> carriers);

    Carrier carrierDTOToCarrier(CarrierDTO carrierDTO);

    List<Carrier> carrierDTOsToCarriers(List<CarrierDTO> carrierDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Carrier carrierFromId(Long id) {
        if (id == null) {
            return null;
        }
        Carrier carrier = new Carrier();
        carrier.setId(id);
        return carrier;
    }
    

}
