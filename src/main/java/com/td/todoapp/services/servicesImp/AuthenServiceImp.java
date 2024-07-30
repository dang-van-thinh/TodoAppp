package com.td.todoapp.services.servicesImp;

import com.td.todoapp.entity.Users;
import com.td.todoapp.models.request.user.UserRequets;
import com.td.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenServiceImp {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.serectKey}")
    private String serectKey;


    // KIỂM TRA XEM CÓ TỒN TẠI USER KHÔNG
    public boolean isAuthented(UserRequets request) {
        try {
            Optional<Users> usered = repo.findByName(request.getName());
            boolean isPasswored = this.passwordEncoder.matches(request.getPassword(), usered.get().getPassword());
            if (isPasswored) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

}
