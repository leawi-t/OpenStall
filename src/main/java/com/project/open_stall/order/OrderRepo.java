package com.project.open_stall.order;

import com.project.open_stall.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Page<Order> findAll(Pageable pageable);

    List<Order> findByUserId(long userId);

    Optional<Order> findByIdAndUserId(long id, long userId);
}
