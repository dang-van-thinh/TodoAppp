package com.td.todoapp.services.servicesImp;
import com.td.todoapp.entity.Role;
import com.td.todoapp.entity.Users;
import com.td.todoapp.models.request.user.UpdateUserRequest;
import com.td.todoapp.models.request.user.UserRequets;
import com.td.todoapp.repository.UserRepository;
import com.td.todoapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users create(UserRequets requets) {
        Users user = new Users();
        user.setPassword(passwordEncoder.encode(requets.getPassword()));
        user.setName(requets.getName());
        user.setRole(Role.USER);
        return repo.save(user);
    }

    @Override
    public List<Users> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Users> getOne(Integer id) {
            Optional<Users> users = repo.findById(id);
            return users;
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Users> user = this.getOne(id);
        if (user.isPresent()){
            repo.delete(user.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<Users> update(Integer id, UpdateUserRequest requets) {
        Optional<Users> usered = this.getOne(id);
        if (usered.isPresent()){
            usered.get().setName(requets.getName());
            usered.get().setPassword(passwordEncoder.encode(requets.getPassword()));
            if ("user".equalsIgnoreCase(requets.getRole())){
                usered.get().setRole(Role.USER);
            }else {
                usered.get().setRole(Role.ADMIN);
            }
        }
        return usered;
    }

    @Override
    public Optional<Users> findByUserName(String name) {
        Optional<Users> users = repo.findByName(name);
        if (users.isPresent()){
            return users;
        }
        return null;
    }
}
