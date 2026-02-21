package com.project.open_stall.mapper;

import com.project.open_stall.dto.userDto.*;
import com.project.open_stall.model.User;
import org.mapstruct.*;

import java.util.List;

// TODO: Maybe add after mapping to ensure that the supplierProfile is associated with the user
// TODO: Make a way to delete the supplierProfile if it is null

@Mapper(componentModel = "spring", uses = {SupplierProfileMapper.class})
public interface UserMapper {

    List<UserResponseDto> toResponseList(List<User> users);

    UserResponseDto toResponse(User user);

    UserDetailDto toDetail(User user);

    User toEntity(UserRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UserUpdateDto dto, @MappingTarget User user);

}
