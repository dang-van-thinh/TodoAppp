package com.td.todoapp.services;

import com.td.todoapp.entity.Users;
import com.td.todoapp.entity.Works;
import com.td.todoapp.models.request.user.UpdateUserRequest;
import com.td.todoapp.models.request.user.UserRequets;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<Users> getAll();
    Optional<Users> getOne(Integer id);
    Optional<Users> update(Integer id, UpdateUserRequest requets);
    boolean delete(Integer id);
    Users create(UserRequets requets);

    Optional<Users> findByUserName(String name);
}
