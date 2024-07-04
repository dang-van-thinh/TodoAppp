package com.td.todoapp.services;

import com.td.todoapp.dto.UserDto;
import com.td.todoapp.models.Users;
import com.td.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public Users create(UserDto userDto){
        Users user = new Users();
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
            user.setPassword(encoder.encode(userDto.getPassword()));
            user.setName(userDto.getName());
        return repo.save(user);
    }

    public List<Users> getAll(){
        return repo.findAll();
    }

    public Users getOne(Integer id){
        try {
            Users users = repo.findById(id).get();
            return users;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public boolean delete(Integer id){
        Users user = this.getOne(id);
        if (user != null){
            repo.delete(user);
            return true;
        }
        return false;
    }

    public Users update(Integer id, UserDto userDto){
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        Users usered = this.getOne(id);
        if (usered!= null){
            usered.setName(userDto.getName());
            usered.setPassword(encoder.encode(userDto.getPassword()));
            return usered;
        }
       return null;
    }
}
