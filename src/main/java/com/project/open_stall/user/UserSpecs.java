package com.project.open_stall.user;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class UserSpecs {
    public static Specification<User> isActive(Boolean active){
        return ((root, query, cb) -> {
            if (active==null) return null;
            return cb.equal(root.get("active"), active);
        });
    }

    public static Specification<User> hasUsername(String username){
        return ((root, query, cb) -> {
            if (username==null) return null;
            return cb.equal(root.get("username"), username);
        });
    }

    public static Specification<User> hasEmail(String email){
        return ((root, query, cb) -> {
            if (email==null) return null;
            return cb.equal(root.get("username"), email);
        });
    }

    public static Specification<User> hasDate(LocalDateTime start, LocalDateTime end){
        return ((root, query, cb) -> {
            if (start == null && end == null) return null;
            if (end == null) return cb.greaterThanOrEqualTo(root.get("createdAt"), start);
            if (start == null) return cb.lessThanOrEqualTo(root.get("createdAt"), end);
            return cb.between(root.get("createdAt"), start, end);
        });
    }
}
