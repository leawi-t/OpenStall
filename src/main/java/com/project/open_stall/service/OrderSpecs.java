package com.project.open_stall.service;

import com.project.open_stall.model.Order;
import com.project.open_stall.model.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderSpecs {
    public static Specification<Order> hasUserId(Long userId){
        return ((root, query, cb) -> {
            if (userId == null) return null;
            return cb.equal(root.get("user").get("id"), userId);
        });
    }

    public static Specification<Order> hasTotalAmountBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max == null) {
                return cb.greaterThanOrEqualTo(root.get("totalAmount"), min);
            }
            if (min == null && max != null) {
                return cb.lessThanOrEqualTo(root.get("totalAmount"), max);
            }
            // Both are present: use between (inclusive of boundaries)
            return cb.between(root.get("totalAmount"), min, max);
        };
    }

    public static Specification<Order> hasStatus(OrderStatus status){
        return ((root, query, cb) -> {
            if (status==null) return null;
            return cb.equal(root.get("status"), status);
        });
    }

    public static Specification<Order> createdBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            if (start != null && end == null) {
                return cb.greaterThanOrEqualTo(root.get("createdAt"), start);
            }
            if (start == null && end != null) {
                return cb.lessThanOrEqualTo(root.get("createdAt"), end);
            }
            return cb.between(root.get("createdAt"), start, end);
        };
    }
}
