package com.td.todoapp.repository;

import com.td.todoapp.entity.Works;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<Works,Integer>, JpaSpecificationExecutor<Works> {

//    @Query(value = "SELECT w FROM Works as w " +
//            "WHERE (w.ten LIKE CONCAT('%', :ten ,'%') OR :ten IS NULL ) " +
//            "AND (:status IS NULL OR w.trangThai = :status) " +
//            "AND (cast(:start as timestamp) IS NULL OR w.ngayBatDau >= :start ) " +
//            "AND (cast(:end as timestamp) IS NULL OR w.ngayKetThuc <= :end )" )
//    List<Works> filter( @Param("ten") String ten,
//                             @Param("status") String status,
//                             @Param("start") LocalDate start,
//                             @Param("end") LocalDate end);

//    @Query(value = "SELECT w FROM Works w WHERE concat(w.id ,'', w.trangThai,'',w.ten) like concat('%',:key,'%')  ")
//List<Works> search(@Param("key") String key);

}
