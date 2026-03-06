package com.project.open_stall.supplierProfile;

import com.project.open_stall.supplierProfile.dto.SupplierProfileRequestDto;
import com.project.open_stall.supplierProfile.dto.SupplierProfileResponseDto;
import com.project.open_stall.supplierProfile.dto.SupplierProfileUpdateDto;
import com.project.open_stall.supplierProfile.model.SupplierProfile;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface SupplierProfileMapper {

    SupplierProfileResponseDto toResponse(SupplierProfile supplierProfile);

    List<SupplierProfileResponseDto> toResponseList(List<SupplierProfile> supplierProfiles);

    SupplierProfile toEntity(SupplierProfileRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(SupplierProfileUpdateDto dto, @MappingTarget SupplierProfile supplierProfile);
}
