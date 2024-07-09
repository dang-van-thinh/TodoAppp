package com.td.todoapp.services;

import com.td.todoapp.entity.Users;
import com.td.todoapp.models.request.user.UserRequets;

import java.util.List;

public interface UserService {
    List<Users> getAll();
    Users getOne(Integer id);
    Users update(Integer id, UserRequets requets);
    boolean delete(Integer id);
    Users create(UserRequets requets);
}
