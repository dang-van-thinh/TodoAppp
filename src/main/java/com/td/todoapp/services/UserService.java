package com.td.todoapp.services;

import com.td.todoapp.dto.UserDto;
import com.td.todoapp.models.Users;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<Users> getAll();
    Users getOne(Integer id);
    Users update(Integer id, UserDto userDto);
    boolean delete(Integer id);
    Users create(UserDto userDto);
}
