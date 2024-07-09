package com.td.todoapp.services.servicesImp;
import com.td.todoapp.entity.Users;
import com.td.todoapp.models.request.user.UserRequets;
import com.td.todoapp.repository.UserRepository;
import com.td.todoapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository repo;

    @Override
    public Users create(UserRequets requets) {
        Users user = new Users();
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        user.setPassword(encoder.encode(requets.getPassword()));
        user.setName(requets.getName());
        return repo.save(user);
    }

    @Override
    public List<Users> getAll() {
        return repo.findAll();
    }

    @Override
    public Users getOne(Integer id) {
        try {
            Users users = repo.findById(id).get();
            return users;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(Integer id) {
        Users user = this.getOne(id);
        if (user != null){
            repo.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public Users update(Integer id, UserRequets requets) {
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        Users usered = this.getOne(id);
        if (usered!= null){
            usered.setName(requets.getName());
            usered.setPassword(encoder.encode(requets.getPassword()));
            return usered;
        }
        return null;
    }
}
