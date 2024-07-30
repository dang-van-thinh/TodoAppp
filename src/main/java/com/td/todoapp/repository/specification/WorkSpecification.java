package com.td.todoapp.repository.specification;

import com.td.todoapp.entity.Users;
import com.td.todoapp.entity.Works;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkSpecification {
    public static Specification<Works> tenContains(String key) {
        return ((root, query, cb) -> cb.like(cb.lower(root.get("ten")), "%" + key.toLowerCase() + "%"));
    }

    public static Specification<Works> findByTenOrTrangThaiOrId(String key) {

        return ((root, query, cb) -> {

            Predicate tenContains = cb.like(cb.lower(root.get("ten")), "%" + key.toLowerCase() + "%");
            Predicate trangThaiContains = cb.like(cb.lower(root.get("trangThai")), "%" + key.toLowerCase() + "%");
            Predicate idContains = cb.disjunction();
            Integer id = null;

            try {
                id = Integer.parseInt(key);
            } catch (Exception ex) {
                System.out.println("Không chuyen duoc sang dang Integer");
            }

            if (id != null) {
                idContains = cb.equal(root.get("id"), id);
            }

            return cb.or(tenContains, trangThaiContains, idContains);
        });
    }

    public static Specification<Works> statusContains(String status) {
        return ((root, query, cb) -> cb.like(cb.lower(root.get("trangThai")), "%" + status.toLowerCase() + "%"));
    }

    public static Specification<Works> findByWorks(String ten, String status, LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            List<Predicate> queries = new ArrayList<>();

            if (ten != null && !ten.isBlank()) {
                queries.add(cb.like(cb.lower(root.get("ten")), "%" + ten.toLowerCase() + "%"));
            }

            if (status != null && !status.isBlank()) {
                queries.add(cb.like(cb.lower(root.get("trangThai")), "%" + status.toLowerCase() + "%"));
            }

            if (start != null && end != null) {
                queries.add(cb.between(root.get("ngayBatDau"), start, end));
            }

            return cb.and(queries.toArray(new Predicate[0]));
        };

    }

    public static Specification<Works> dateBetween(LocalDate startDate, LocalDate endDate) {
        return ((root, query, cb) -> cb.between(root.get("ngayBatDau"), startDate, endDate));
    }
    // timf kiếm works bằng truy vấn join bảng với specification
  public static Specification<Works> findWorksByUserName(String name){
        return (root, query, cb) -> {
            Join<Users,Works> worksUser = root.join("users");
           return cb.equal(worksUser.get("name"),name);
        };
  }
}
