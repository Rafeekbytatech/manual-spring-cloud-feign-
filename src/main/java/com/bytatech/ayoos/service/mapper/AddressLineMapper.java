package com.bytatech.ayoos.service.mapper;

import com.bytatech.ayoos.domain.*;
import com.bytatech.ayoos.service.dto.AddressLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AddressLine and its DTO AddressLineDTO.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface AddressLineMapper extends EntityMapper<AddressLineDTO, AddressLine> {

    @Mapping(source = "patient.id", target = "patientId")
    AddressLineDTO toDto(AddressLine addressLine);

    @Mapping(source = "patientId", target = "patient")
    AddressLine toEntity(AddressLineDTO addressLineDTO);

    default AddressLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        AddressLine addressLine = new AddressLine();
        addressLine.setId(id);
        return addressLine;
    }
}
