package com.td.todoapp.services.servicesImp;

import com.td.todoapp.dto.WorkDto;
import com.td.todoapp.models.Works;
import com.td.todoapp.repository.WorkRepository;
import com.td.todoapp.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.util.List;

@Service
public class WorkServiceImp implements WorkService {
    @Autowired
    private WorkRepository repo;

    @Override
    public List<Works> getAll() {
        return repo.findAll();
    }

    @Override
    public Works create(WorkDto workDto) {
        boolean isCheck = this.isTimeCorrect(workDto);
        if (isCheck) {
            Works work = new Works(workDto.getTen().toLowerCase(),
                    workDto.getTrangThai().toUpperCase(),
                    workDto.getNgayBatDau(),
                    workDto.getNgayKetThuc());
            return repo.save(work);
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        Works work = this.getOne(id);
        if (work != null) {
            repo.delete(work);
            return true;
        }
        return false;
    }

    @Override
    public Works getOne(Integer id) {
        try {
            Works work = repo.findById(id).get();
            return work;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Works update(Integer id, WorkDto work) {
        Works worked = this.getOne(id);
        boolean isCheck = this.isTimeCorrect(work);
        if (worked != null && isCheck) {
            worked.setTen(work.getTen());
            worked.setTrangThai(work.getTrangThai());
            worked.setNgayBatDau(work.getNgayBatDau());
            worked.setNgayKetThuc(work.getNgayKetThuc());
        } else {
            return null;
        }
        return repo.save(worked);
    }


    public List<Works> filter(String ten, String status, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        try {

            if (ten == null && status == null) {
                return repo.findByNgayKetThucBetween(ngayBatDau, ngayKetThuc);
            }
            return repo.findByNgayBatDauBetweenAndTrangThaiStartingWithAndTenContaining(ngayBatDau, ngayKetThuc, status.toUpperCase(), ten.toLowerCase());
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Works> search(String key) {
        return repo.findByTrangThaiOrTenContaining(key.toUpperCase(), key.toLowerCase());
    }

    public boolean isTimeCorrect(WorkDto work) {
        boolean isCheckDate = false;
        LocalDate timeNow = LocalDate.now();
        
        System.out.println(timeNow);
        if (work.getNgayBatDau().isBefore(work.getNgayKetThuc()) || work.getNgayBatDau().isEqual(work.getNgayKetThuc())) {
            if ("DOING".equals(work.getTrangThai()) &&
                    (work.getNgayBatDau().isBefore(timeNow) || work.getNgayBatDau().isEqual(timeNow)) &&
                    (work.getNgayKetThuc().isAfter(timeNow) || work.getNgayKetThuc().isEqual(timeNow))) {

                isCheckDate = true;

            } else if ("DONE".equals(work.getTrangThai()) &&
                    (work.getNgayBatDau().isBefore(timeNow) || work.getNgayBatDau().isEqual(timeNow)) &&
                    (work.getNgayKetThuc().isBefore(timeNow) || work.getNgayKetThuc().isEqual(timeNow))
            ) {

                isCheckDate = true;

            } else if ("TODO".equals(work.getTrangThai()) && (work.getNgayBatDau().isAfter(timeNow) && work.getNgayKetThuc().isAfter(timeNow))) {

                isCheckDate = true;

            }
        }

        return isCheckDate;
    }

}
