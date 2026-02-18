package com.project.open_stall.mapper;

import com.project.open_stall.dto.addressDto.AddressDto;
import com.project.open_stall.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto toResponse(Address address);
}
