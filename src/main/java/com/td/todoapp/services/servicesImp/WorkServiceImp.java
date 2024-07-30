package com.td.todoapp.services.servicesImp;

import com.td.todoapp.entity.Users;
import com.td.todoapp.entity.Works;
import com.td.todoapp.models.dto.user.UserDto;
import com.td.todoapp.models.dto.work.WorkDto;
import com.td.todoapp.models.dto.work.WorkWithUserDto;
import com.td.todoapp.models.request.work.UpdateWorkRequest;
import com.td.todoapp.models.request.work.WorkRequests;
import com.td.todoapp.repository.WorkRepository;
import com.td.todoapp.repository.specification.WorkSpecification;
import com.td.todoapp.services.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class WorkServiceImp implements WorkService {

    private static final Logger logger = LoggerFactory.getLogger(WorkServiceImp.class);
    @Autowired
    private WorkRepository repo;

    @Autowired
    private UserServiceImp userService;

    @Override
    public List<Works> getAll() {

        return repo.findAll();
    }

    @Override
    public Works create(WorkRequests requests) {
        boolean isCheck = this.isTimeCorrect(requests.getTrangThai(), requests.getNgayBatDau(), requests.getNgayKetThuc());
        Works work = new Works();

        if (isCheck) {
            System.out.println("id user:" + requests.getUserId());
            Optional<Users> usered = userService.getOne(requests.getUserId());
            System.out.println("User: " + usered.isPresent());
            if (usered.isPresent()) {
                work.setUsers(usered.get());
            }
            System.out.println(usered);
            work.setTen(requests.getTen());
            work.setTrangThai(requests.getTrangThai());
            work.setNgayKetThuc(requests.getNgayKetThuc());
            work.setNgayBatDau(requests.getNgayBatDau());
            return repo.save(work);

        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Works> work = this.getOne(id);
        if (work.isPresent()) {
            repo.delete(work.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Works> getOne(Integer id) {
        Optional<Works> works = repo.findById(id);
        System.out.println(works.isPresent());
        return works;
    }

    @Override
    public Works update(Integer id, UpdateWorkRequest request) {
        Optional<Works> worked = this.getOne(id);
        boolean isCheck = this.isTimeCorrect(request.getTrangThai(), request.getNgayBatDau(), request.getNgayKetThuc());
        if (worked != null && isCheck) {
            worked.get().setTrangThai(request.getTrangThai().toUpperCase());
            worked.get().setNgayBatDau(request.getNgayBatDau());
            worked.get().setNgayKetThuc(request.getNgayKetThuc());
        } else {
            return null;
        }
        return repo.save(worked.get());
    }


    public List<Works> filter(String ten, String status, LocalDate start, LocalDate end) {

        Specification<Works> specification = WorkSpecification.findByWorks(ten, status, start, end);
        return repo.findAll(specification);
    }


    @Override
    public List<Works> search(String key) {
        Specification<Works> search = WorkSpecification.findByTenOrTrangThaiOrId(key);
        return repo.findAll(search);
    }


    public boolean isTimeCorrect(String trangThai, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
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

    @Override
    public WorkWithUserDto getWorkWithUser(String name) {

        List<Works> worksList = repo.findAll(WorkSpecification.findWorksByUserName(name));
        if (worksList.size() != 0) {
           logger.info("service work : " + worksList.toString());
            UserDto userDto = new UserDto();
            List<WorkDto> workDtos = new ArrayList<>();

            Users usered = worksList.get(0).getUsers();

            userDto.setName(usered.getName());
            userDto.setPassword(usered.getPassword());

            for (Works work : worksList) {
                WorkDto workDto = new WorkDto();

                workDto.setTen(work.getTen());
                workDto.setTrangThai(work.getTrangThai());
                workDto.setNgayBatDau(work.getNgayBatDau());
                workDto.setNgayKetThuc(work.getNgayKetThuc());

                workDtos.add(workDto);
            }
            return new WorkWithUserDto(userDto, workDtos);
        }
        return null;
    }
}
