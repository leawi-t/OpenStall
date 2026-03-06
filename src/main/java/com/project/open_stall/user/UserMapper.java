package com.project.open_stall.user;

import com.project.open_stall.product.mapper.ProductMapper;
import com.project.open_stall.supplierProfile.SupplierProfileMapper;
import com.project.open_stall.user.dto.UserDetailDto;
import com.project.open_stall.user.dto.UserRequestDto;
import com.project.open_stall.user.dto.UserResponseDto;
import com.project.open_stall.user.dto.UserUpdateDto;
import org.mapstruct.*;

import java.util.List;

// TODO: Maybe add after mapping to ensure that the supplierProfile is associated with the user
// TODO: Make a way to delete the supplierProfile if it is null

@Mapper(componentModel = "spring", uses = {SupplierProfileMapper.class, ProductMapper.class})
public interface UserMapper {

    List<UserResponseDto> toResponseList(List<User> users);

    UserResponseDto toResponse(User user);

    UserDetailDto toDetail(User user);

    User toEntity(UserRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UserUpdateDto dto, @MappingTarget User user);

}
