package com.td.todoapp.repository;

import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.td.todoapp.models.Works;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Works,Integer> {
    List<Works> findByNgayBatDauBetweenAndTrangThaiStartingWithAndTenContaining(LocalDate ngayBatDau, LocalDate ngayKetThuc, String status, String name);
    List<Works> findByTenContainingAndTrangThaiAndNgayBatDauBetween(String ten,String status,LocalDate start,LocalDate end);
    List<Works> findByNgayKetThucBetween(LocalDate start , LocalDate end);

    List<Works> findByTrangThaiOrTenContaining(String status,String ten);
    List<Works> findByTenContaining(String ten);

}
