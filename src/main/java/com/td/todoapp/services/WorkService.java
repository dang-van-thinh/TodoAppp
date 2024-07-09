package com.td.todoapp.services;
import com.td.todoapp.entity.Works;
import com.td.todoapp.models.request.work.UpdateWorkRequest;
import com.td.todoapp.models.request.work.WorkRequets;

import java.util.List;

public interface WorkService {
    List<Works> getAll();
    Works getOne(Integer id);
    Works update(Integer id, UpdateWorkRequest requets);
    boolean delete(Integer id);
    Works create(WorkRequets requets);
    List<Works> search(String name);
}
