package com.td.todoapp.controllers;

import com.td.todoapp.dto.UserDto;
import com.td.todoapp.services.servicesImp.AuthenServiceImp;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenController {
    @Autowired
    private AuthenServiceImp service;

    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto, HttpSession session){
        session.setAttribute("auth",userDto);
       if (service.isAuthented(userDto)){
           return "Đúng";
       }else {
           return "Sai";
       }
    }
}
