package com.project.open_stall.config;

import com.project.open_stall.model.*;
import com.project.open_stall.repo.CategoryRepo;
import com.project.open_stall.repo.ProductRepo;
import com.project.open_stall.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepo userRepository;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    @Bean
    @Transactional
    CommandLineRunner initData() {
        return args -> {
            //To initialize dummy data

            if (userRepository.count()==0) {

                Category electronics = new Category();
                electronics.setName("Electronics");
                electronics.setDescription("Gadgets and gizmos");
                categoryRepo.save(electronics);

                Category home = new Category();
                home.setName("Home & Kitchen");
                home.setDescription("Everything for your house");
                categoryRepo.save(home);

                // 2. Create a Supplier
                User supplier = new User();
                supplier.setFirstName("John");
                supplier.setLastName("Doe");
                supplier.setUserName("johndoe_shop");
                supplier.setEmail("john@example.com");
                supplier.setPassword("Password123!"); // to be encoded
                supplier.setRole(Role.SUPPLIER);

                SupplierProfile profile = new SupplierProfile();
                profile.setCompanyName("John's Tech Stall");
                profile.setBio("Best tech in town");

                Address address = new Address();
                address.setCountry("Ethiopia");
                address.setCity("Bole, Lemmi");
                address.setState("Addis Ababa");

                profile.setAddress(address);

                SocialMediaLink socialMediaLink = new SocialMediaLink();
                socialMediaLink.setPlatform("telegram");
                socialMediaLink.setUrl("...");

                profile.getSocialMediaLinks().add(socialMediaLink);

                supplier.setSupplierProfile(profile);
                userRepository.save(supplier);

                // 3. Create Products
                Product phone = new Product();
                phone.setName("StallPhone 15");
                phone.setModel("Pro Max");
                phone.setDescription("Iphone Pro max brand new case not opened");
                phone.setSalePrice(new BigDecimal("999.99"));
                phone.setSupplierCost(new BigDecimal("700.00"));
                phone.setStockQuantity(50);
                phone.setActive(true);
                phone.setSupplierProfile(profile);
                phone.setCategories(Set.of(electronics));
                productRepo.save(phone);

                Product toaster = new Product();
                toaster.setName("ToastMaster 3000");
                toaster.setModel("X-Chrome");
                toaster.setDescription("Works like a charm");
                toaster.setSalePrice(new BigDecimal("49.99"));
                toaster.setSupplierCost(new BigDecimal("20.00"));
                toaster.setStockQuantity(10);
                toaster.setActive(false);
                toaster.setSupplierProfile(profile);
                toaster.setCategories(Set.of(home));
                productRepo.save(toaster);

                System.out.println("Dummy data initialized!");
            }
        };
    }
}
