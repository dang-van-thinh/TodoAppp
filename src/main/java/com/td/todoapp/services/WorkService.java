package com.td.todoapp.services;
import com.td.todoapp.entity.Works;
import com.td.todoapp.models.dto.work.WorkWithUserDto;
import com.td.todoapp.models.request.work.UpdateWorkRequest;
import com.td.todoapp.models.request.work.WorkRequests;

import java.util.List;
import java.util.Optional;

public interface WorkService {
    List<Works> getAll();
    Optional<Works> getOne(Integer id);

    WorkWithUserDto getWorkWithUser(String name);
    Works update(Integer id, UpdateWorkRequest requets);
    boolean delete(Integer id);
    Works create(WorkRequests requets);
    List<Works> search(String key);
}
