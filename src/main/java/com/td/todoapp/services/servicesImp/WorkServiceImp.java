package com.td.todoapp.services.servicesImp;

import com.td.todoapp.entity.Works;
import com.td.todoapp.models.request.work.UpdateWorkRequest;
import com.td.todoapp.models.request.work.WorkRequets;
import com.td.todoapp.repository.WorkRepository;
import com.td.todoapp.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Works create(WorkRequets requets) {
        boolean isCheck = this.isTimeCorrect(requets.getTrangThai(),requets.getNgayBatDau(),requets.getNgayKetThuc());
        if (isCheck) {
            Works work = new Works(requets.getTen().toLowerCase(),
                    requets.getTrangThai().toUpperCase(),
                    requets.getNgayBatDau(),
                    requets.getNgayKetThuc());
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
    public Works update(Integer id, UpdateWorkRequest request) {
        Works worked = this.getOne(id);
        boolean isCheck = this.isTimeCorrect(request.getTrangThai(),request.getNgayBatDau(),request.getNgayKetThuc());
        if (worked != null && isCheck) {
            worked.setTrangThai(request.getTrangThai().toUpperCase());
            worked.setNgayBatDau(request.getNgayBatDau());
            worked.setNgayKetThuc(request.getNgayKetThuc());
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

    public boolean isTimeCorrect(String trangThai,LocalDate ngayBatDau,LocalDate ngayKetThuc) {
        boolean isCheckDate = false;
        LocalDate timeNow = LocalDate.now();

        System.out.println(timeNow);
        if (ngayBatDau.isBefore(ngayKetThuc) || ngayBatDau.isEqual(ngayKetThuc)) {
            if ("DOING".equalsIgnoreCase(trangThai) &&
                    (ngayBatDau.isBefore(timeNow) || ngayBatDau.isEqual(timeNow)) &&
                    (ngayKetThuc.isAfter(timeNow) || ngayKetThuc.isEqual(timeNow))) {

                isCheckDate = true;

            } else if ("DONE".equalsIgnoreCase(trangThai) &&
                    (ngayBatDau.isBefore(timeNow) || ngayBatDau.isEqual(timeNow)) &&
                    (ngayKetThuc.isBefore(timeNow) || ngayKetThuc.isEqual(timeNow))
            ) {

                isCheckDate = true;

            } else if ("TODO".equalsIgnoreCase(trangThai) && (ngayBatDau.isAfter(timeNow) && ngayKetThuc.isAfter(timeNow))) {

                isCheckDate = true;

            }
        }

        return isCheckDate;
    }

}
