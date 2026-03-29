package com.project.open_stall.user;

import com.project.open_stall.common.exception.InvalidOperationException;
import com.project.open_stall.common.exception.ResourceNotFoundException;
import com.project.open_stall.order.OrderRepo;
import com.project.open_stall.order.OrderService;
import com.project.open_stall.order.model.Order;
import com.project.open_stall.order.model.OrderStatus;
import com.project.open_stall.product.ProductRepo;
import com.project.open_stall.supplierProfile.SupplierProfileMapper;
import com.project.open_stall.cart.model.Cart;
import com.project.open_stall.supplierProfile.dto.SupplierProfileRequestDto;
import com.project.open_stall.supplierProfile.dto.SupplierProfileResponseDto;
import com.project.open_stall.supplierProfile.dto.SupplierProfileUpdateDto;
import com.project.open_stall.supplierProfile.model.SupplierProfile;
import com.project.open_stall.user.dto.UserDetailDto;
import com.project.open_stall.user.dto.UserRequestDto;
import com.project.open_stall.user.dto.UserResponseDto;
import com.project.open_stall.user.dto.UserUpdateDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final SupplierProfileMapper profileMapper;
    private final OrderService orderService;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;

    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponseDto> getUsers(Pageable pageable, Boolean active, String email,
                                          String username, LocalDateTime start, LocalDateTime end) {
        Specification<User> spec = Specification.where(UserSpecs.isActive(active))
                .and(UserSpecs.hasDate(start, end))
                .and(UserSpecs.hasEmail(email))
                .and(UserSpecs.hasUsername(username));

        return userRepo.findAll(spec, pageable).map(userMapper::toResponse);
    }

    public SupplierProfileResponseDto getUserById(long userId){
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with id: " + userId + " was not found"));

        if (!user.isActive()) throw new ResourceNotFoundException("User with id: " + userId + " was not found");

        return profileMapper.toResponse(user.getSupplierProfile());
    }

    @Transactional
    public UserDetailDto updateUser(UserUpdateDto dto, long userId){
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        userMapper.updateEntity(dto, user);
        return userMapper.toDetail(user);
    }

    @Transactional
    public void deleteUser(long userId){
        if (!userRepo.existsById(userId))
            throw new ResourceNotFoundException("User with " + userId + " does not exist");

    }

    @Transactional
    public UserDetailDto addSupplierProfile(SupplierProfileRequestDto dto, long userId) {
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        if (user.getRole() == Role.SUPPLIER){
            throw new InvalidOperationException("Already have an associated Supplier profile");
        }

        if (user.getRole() == Role.ADMIN){
            throw new InvalidOperationException("Admins can not have a Supplier profile");
        }

        user.setRole(Role.valueOf("SUPPLIER"));
        user.setSupplierProfile(profileMapper.toEntity(dto));

        return userMapper.toDetail(userRepo.save(user));
    }

    @Transactional
    @PreAuthorize("hasRole('SUPPLIER')")
    public SupplierProfileResponseDto updateSupplierProfile(SupplierProfileUpdateDto dto, long userId){
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        profileMapper.updateEntity(dto, user.getSupplierProfile());
        return profileMapper.toResponse(user.getSupplierProfile());
    }

    @Transactional
    @PreAuthorize("hasRole('SUPPLIER')")
    public void deleteSupplierProfile(long userId){
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        user.setSupplierProfile(null);
        user.setRole(Role.valueOf("CONSUMER"));
    }

    @Transactional
    public void softDeleteUser(long userId) {
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        user.setActive(false);

        String deletedSuffix = "_del_" + userId;
        user.setUserName(user.getUserName() + deletedSuffix);
        user.setEmail("deleted_" + userId + "@openstall.com");

        if (user.getCart() != null) {
            user.getCart().getItems().clear();
        }

        if (user.getRole() == Role.SUPPLIER && user.getSupplierProfile() != null) {
            user.getSupplierProfile().getProducts().forEach(product -> {
                product.setActive(false);
            });
        }

        List<Order> orders = orderRepo.findByUserIdAndStatus(userId, OrderStatus.PENDING);
        for (Order order : orders) {
            orderService.cancelOrder(userId, order.getId());
        }

        userRepo.save(user);
    }
}
