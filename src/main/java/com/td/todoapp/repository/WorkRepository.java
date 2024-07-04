package com.td.todoapp.repository;

import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.td.todoapp.models.Works;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.crypto.ShortBufferException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Works,Integer> {
    List<Works> findByNgayBatDauBetweenOrTrangThaiOrTenContaining(LocalDateTime ngayBatDau,LocalDateTime ngayKetThuc,Short status,String key);
}
