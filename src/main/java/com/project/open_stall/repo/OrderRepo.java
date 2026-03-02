package com.project.open_stall.repo;

import com.project.open_stall.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByUserId(long userId);

    Optional<Order> findByIdAndUserId(long id, long userId);
}
