package com.td.todoapp.services;

import com.td.todoapp.dto.WorkDto;
import com.td.todoapp.models.Works;
import com.td.todoapp.repository.WorkRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkService {
    @Autowired
    private WorkRepository repo;

    public List<Works> getAll(){
        return repo.findAll();
    }

    public Works store(WorkDto workDto){
        boolean isCheck = this.isTimeCorrect(workDto);
        if (isCheck){
            Works work = new Works(workDto.getTen(),
                    workDto.getTrangThai(),
                    workDto.getNgayBatDau(),
                    workDto.getNgayKetThuc());
            return repo.save(work);
        }
        return null;
    }

    public boolean delete(Integer id){
        Works work = this.getOne(id);
        if (work != null){
            repo.delete(work);
            return true;
        }
        return false;
    }

    public Works getOne(Integer id){
        try {
            Works work = repo.findById(id).get();
            return work;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Works update(Integer id, WorkDto work){
        Works worked = this.getOne(id);
        boolean isCheck = this.isTimeCorrect(work);
        if (worked != null && isCheck == true){
            worked.setTen(work.getTen());
            worked.setTrangThai(work.getTrangThai());
            worked.setNgayBatDau(work.getNgayBatDau());
            worked.setNgayKetThuc(work.getNgayKetThuc());
        }else {
            return null;
        }
        return repo.save(worked);
    }

    public List<Works> search(LocalDateTime ngayBatDau,LocalDateTime ngayKetThuc,Short status,String key){
//        List<Works> search = repo.findByNgayBatDauBetweenAndTrangThaiAndTenContaining();
        List<Works> search = repo.findByNgayBatDauBetweenOrTrangThaiOrTenContaining(ngayBatDau,ngayKetThuc,status,key);
        return search;
    }

    public boolean isTimeCorrect(WorkDto work){
        boolean isCheckDate = false;
        System.out.println(LocalDateTime.now());
        if (work.getTrangThai() == 2) {
            if (work.getNgayBatDau().isBefore(LocalDateTime.now()) && work.getNgayKetThuc().isAfter(LocalDateTime.now()) &&
                    (work.getNgayBatDau().isBefore(work.getNgayKetThuc()))) {
                isCheckDate = true;
            }
        } else if (work.getTrangThai() == 3) {
            if (work.getNgayBatDau().isBefore(LocalDateTime.now()) && work.getNgayKetThuc().isBefore(LocalDateTime.now()) &&
                    (work.getNgayBatDau().isBefore(work.getNgayKetThuc()))
            ) {
                isCheckDate = true;
            }
        } else if (work.getTrangThai() == 1) {
            if (work.getNgayBatDau().isAfter(LocalDateTime.now()) && work.getNgayKetThuc().isAfter(LocalDateTime.now()) &&
                    (work.getNgayBatDau().isBefore(work.getNgayKetThuc()))
            ) {
                isCheckDate = true;
            }
        }
        return isCheckDate;
    }

}
