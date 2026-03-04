package com.project.open_stall.supplierProfile;

import com.project.open_stall.supplierProfile.dto.AddressDto;
import com.project.open_stall.supplierProfile.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto toResponse(Address address);
}
