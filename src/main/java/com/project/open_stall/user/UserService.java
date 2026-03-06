package com.project.open_stall.user;

import com.project.open_stall.common.exception.InvalidOperationException;
import com.project.open_stall.common.exception.ResourceNotFoundException;
import com.project.open_stall.order.OrderRepo;
import com.project.open_stall.order.OrderService;
import com.project.open_stall.order.model.Order;
import com.project.open_stall.order.model.OrderStatus;
import com.project.open_stall.supplierProfile.SupplierProfileMapper;
import com.project.open_stall.cart.model.Cart;
import com.project.open_stall.supplierProfile.dto.SupplierProfileDetailsDto;
import com.project.open_stall.supplierProfile.dto.SupplierProfileRequestDto;
import com.project.open_stall.supplierProfile.dto.SupplierProfileUpdateDto;
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

    // Can I preauthorize some of the filters for the admin like active == null or false? or should I create a separate method where I use @Preauthorize and make the active parameter always true for this method
    public Page<UserResponseDto> getUsers(Pageable pageable, Boolean active, String email,
                                          String username, LocalDateTime start, LocalDateTime end) {
        Specification<User> spec = Specification.where(UserSpecs.isActive(active))
                .and(UserSpecs.hasDate(start, end))
                .and(UserSpecs.hasEmail(email))
                .and(UserSpecs.hasUsername(username));

        return userRepo.findAll(spec, pageable).map(userMapper::toResponse);
    }

    //should I Use a method like findByIdAndActive here?
    public UserResponseDto getUserById(long userId){
        return userMapper.toResponse(userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    @Transactional
    public UserDetailDto registerUser(UserRequestDto dto){
        if (userRepo.existsByEmail(dto.email())){
            throw new InvalidOperationException("Email already in use");
        }

        if (userRepo.existsByUserName(dto.userName())){
            throw new InvalidOperationException("Username already in use");
        }

        User user = userMapper.toEntity(dto);
        Cart cart = new Cart();
        user.setCart(cart);

        if (dto.role().equalsIgnoreCase("Supplier")){
            if (dto.supplierProfile() == null)
                throw new InvalidOperationException("Supplier profile data is required for Supplier role");
            else
                user.setSupplierProfile(null);
        }

        else{
            if (dto.supplierProfile() != null)
                throw new InvalidOperationException("Consumer roles can not have Supplier Profiles");
            else
                user.setSupplierProfile(null);
        }

        User savedUser = userRepo.save(user);
        return userMapper.toDetail(savedUser);
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

        user.setRole(Role.valueOf("SUPPLIER"));
        user.setSupplierProfile(profileMapper.toEntity(dto));

        return userMapper.toDetail(userRepo.save(user));
    }

    @Transactional
    @PreAuthorize("hasRole('SUPPLIER')")
    public SupplierProfileDetailsDto updateSupplierProfile(SupplierProfileUpdateDto dto, long userId){
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        profileMapper.updateEntity(dto, user.getSupplierProfile());
        return profileMapper.toDetails(user.getSupplierProfile());
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
    public void softDeleteUser(long userId){
        User user = userRepo.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User with " + userId + " does not exist"));

        user.setActive(false);

        LocalDateTime deletedAt = LocalDateTime.now();
        user.setUserName(user.getUserName() + deletedAt);
        user.setEmail(user.getUserName() + deletedAt);

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
    }

}
