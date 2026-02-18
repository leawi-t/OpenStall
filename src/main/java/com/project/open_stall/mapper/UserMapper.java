package com.project.open_stall.mapper;

import com.project.open_stall.dto.userDto.*;
import com.project.open_stall.model.User;
import org.mapstruct.*;

import java.util.List;

// TODO: Maybe add after mapping to ensure that the supplierProfile is associated with the user

@Mapper(componentModel = "spring", uses = {SupplierProfileMapper.class})
public interface UserMapper {

    List<UserResponseDto> toResponse(List<User> users);

    UserDetailDto toDetail(User user);

    User toEntity(UserRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(
            source = "supplierProfile",
            target = "supplierProfile",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
    )
    void updateEntity(UserUpdateDto dto, @MappingTarget User user);

}
