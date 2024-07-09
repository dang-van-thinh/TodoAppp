package com.td.todoapp.services;
import com.td.todoapp.dto.WorkDto;
import com.td.todoapp.models.Works;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkService {
    List<Works> getAll();
    Works getOne(Integer id);
    Works update(Integer id, WorkDto workDto);
    boolean delete(Integer id);
    Works create(WorkDto workDto);
    List<Works> search(String name);
}
