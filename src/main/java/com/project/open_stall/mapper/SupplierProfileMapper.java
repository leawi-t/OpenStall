package com.project.open_stall.mapper;

import com.project.open_stall.dto.supplierProfileDto.*;
import com.project.open_stall.model.SupplierProfile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface SupplierProfileMapper {

    SupplierProfileResponseDto toResponse(SupplierProfile supplierProfile);

    List<SupplierProfileResponseDto> toResponseList(List<SupplierProfile> supplierProfiles);

    SupplierProfileDetailsDto toDetails(SupplierProfile supplierProfile);

    SupplierProfile toEntity(SupplierProfileRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(SupplierProfileUpdateDto dto, @MappingTarget SupplierProfile supplierProfile);
}
