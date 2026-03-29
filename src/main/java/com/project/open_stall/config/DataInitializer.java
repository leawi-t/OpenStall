package com.project.open_stall.config;

import com.project.open_stall.cart.CartService;
import com.project.open_stall.cart.dto.CartItemDto.CartItemRequestDto;
import com.project.open_stall.category.Category;
import com.project.open_stall.order.OrderService;
import com.project.open_stall.order.dto.orderDto.OrderDetailsDto;
import com.project.open_stall.order.model.AddressSnapshot;
import com.project.open_stall.product.model.Product;
import com.project.open_stall.category.CategoryRepo;
import com.project.open_stall.product.ProductRepo;
import com.project.open_stall.security.AuthService;
import com.project.open_stall.supplierProfile.dto.AddressDto;
import com.project.open_stall.supplierProfile.dto.SupplierProfileRequestDto;
import com.project.open_stall.supplierProfile.model.SocialMediaLink;
import com.project.open_stall.supplierProfile.model.SupplierProfile;
import com.project.open_stall.user.*;
import com.project.open_stall.user.dto.UserDetailDto;
import com.project.open_stall.user.dto.UserRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepo;
    private final UserService userService;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final CartService cartService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepo.count() == 0) {
            System.out.println("--- Starting OpenStall Data Initialization ---");

            // 1. Create Categories
            Category electronics = new Category();
            electronics.setName("Electronics");
            electronics.setDescription("Gadgets and tech");
            categoryRepo.save(electronics);

            Category home = new Category();
            home.setName("Home & Kitchen");
            home.setDescription("Everything for your house");
            categoryRepo.save(home);

            AddressDto address = new AddressDto("Ethiopia", "Addis Ababa", "Bole");

            List<SocialMediaLink> links = new ArrayList<>(Arrays.asList(new SocialMediaLink("Telegram", "...")));

            // 2. Register a Supplier (Using the DTO and Service to test logic)
            SupplierProfileRequestDto profileDto = new SupplierProfileRequestDto(
                    "Tech Haven",
                    "Best gadgets in Addis",
                    address, links
            );

            UserRequestDto supplierDto = new UserRequestDto(
                    "John", "Doe", "john_supplier", passwordEncoder.encode("John123!"), "john@gmail.com", "SUPPLIER", profileDto
            );

            UserDetailDto supplier = authService.registerUser(supplierDto);
            System.out.println("✅ Supplier Registered: " + supplier.userName());

            // 3. Register a Consumer
            UserRequestDto consumerDto = new UserRequestDto(
                    "Jane", "Smith", "jane_buyer", passwordEncoder.encode("Jane123!"), "jane@gamil.com", "CONSUMER", null
            );
            UserDetailDto consumer = authService.registerUser(consumerDto);
            System.out.println("✅ Consumer Registered: " + consumer.userName());

            User managedSupplier = userRepo.findById(supplier.id())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));

            SupplierProfile managedProfile = managedSupplier.getSupplierProfile();
            System.out.println("Supplier Profile ID: " + managedProfile.getId());

            // 4. Add Products to the Supplier's Profile
            Product phone = new Product();
            phone.setName("StallPhone 15");
            phone.setDescription("Good phone");
            phone.setModel("StallPhone");
            phone.setSalePrice(new BigDecimal("1000.00"));
            phone.setSupplierCost(new BigDecimal("700.00"));
            phone.setStockQuantity(10);
            phone.setActive(true);
            phone.setCategories(Set.of(electronics));
            phone.setSupplierProfile(managedProfile);
            productRepo.save(phone);
            managedProfile.addProduct(phone);

            Product blender = new Product();
            blender.setName("SmoothieMaker Pro");
            blender.setDescription("great blender");
            blender.setModel("Blender");
            blender.setSalePrice(new BigDecimal("50.00"));
            blender.setSupplierCost(new BigDecimal("20.00"));
            blender.setStockQuantity(5);
            blender.setActive(true);
            blender.setSupplierProfile(managedProfile);
            blender.setCategories(Set.of(home));
            productRepo.save(blender);
            managedProfile.addProduct(blender);
            System.out.println("✅ Products Created and Linked to Supplier.");

            // 5. Test Cart Logic: Add items to Jane's Cart
            cartService.addItemToCart(consumer.id(), new CartItemRequestDto(2, phone.getId())); // 2 phones
            cartService.addItemToCart(consumer.id(), new CartItemRequestDto(1, blender.getId())); // 1 blender
            System.out.println("✅ Items added to Jane's Cart. Checking stock...");

            // 6. Test Order Logic: Create an Order (Snapshotting)
            AddressSnapshot shipping = new AddressSnapshot("Ethiopia", "Addis Ababa", "Kazanchis");
            OrderDetailsDto order = orderService.createOrder(consumer.id(), shipping);
            System.out.println("🚀 Order Placed! ID: " + order.id() + " Total: " + order.totalAmount());

            // Verify Stock Decrement
            Product updatedPhone = productRepo.findById(phone.getId()).get();
            System.out.println("📉 Phone Stock after order (Should be 8): " + updatedPhone.getStockQuantity());

            // 7. Test Soft Delete
            userService.softDeleteUser(supplier.id());
            System.out.println("⚠️ Supplier soft-deleted. Checking product status...");

            Product deletedSupplierProduct = productRepo.findById(phone.getId())
                    .orElseThrow(() -> new RuntimeException("Product lost!"));

            System.out.println("🚫 Is Product active? (Should be false): " + deletedSupplierProduct.isActive());

            System.out.println("--- Full Flow Test Complete ---");
        }
    }
}
