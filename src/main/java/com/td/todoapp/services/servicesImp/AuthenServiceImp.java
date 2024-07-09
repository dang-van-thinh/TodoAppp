package com.td.todoapp.services.servicesImp;

import com.td.todoapp.dto.UserDto;
import com.td.todoapp.models.Users;
import com.td.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenServiceImp {
    @Autowired
    private UserRepository repo;

    public boolean isAuthented(UserDto userDto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        try {
            Users user = repo.findByName(userDto.getName()).get();
            if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }



}
