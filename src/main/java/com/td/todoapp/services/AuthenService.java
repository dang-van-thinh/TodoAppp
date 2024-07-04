package com.td.todoapp.services;

import com.td.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenService {
    @Autowired
    private UserRepository repo;

    public boolean authented(){
        return true;
    }
}
