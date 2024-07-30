package com.td.todoapp.controllers;

import com.td.todoapp.entity.Users;
import com.td.todoapp.models.dto.user.UserDto;
import com.td.todoapp.models.request.user.UserRequets;
import com.td.todoapp.services.servicesImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServiceImp userServiceImp;
    @PostMapping("/login")
    public void login(@RequestBody UserDto user, Authentication auth){
        System.out.println(auth.getName());
//        System.out.println( principal.getName());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequets userRequets){
        Optional<Users> user = userServiceImp.findByUserName(userRequets.getName());
        if (user == null){
            return ResponseEntity.ok(userServiceImp.create(userRequets));
        }
        return ResponseEntity.badRequest().body("User da ton tai ! Vui long dang nhap !");
    }
}
