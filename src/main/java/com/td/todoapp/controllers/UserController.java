package com.td.todoapp.controllers;

import com.td.todoapp.entity.Users;
import com.td.todoapp.entity.Works;
import com.td.todoapp.models.dto.work.WorkWithUserDto;
import com.td.todoapp.models.request.user.UpdateUserRequest;
import com.td.todoapp.models.request.user.UserRequets;
import com.td.todoapp.services.servicesImp.UserServiceImp;
import com.td.todoapp.services.servicesImp.WorkServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImp service;

    @Autowired
    private WorkServiceImp workServiceImp;


    @GetMapping("/works")
    public ResponseEntity<?> getAllWorkByUser(Authentication auth){
        String name = auth.getName();
        WorkWithUserDto works = workServiceImp.getWorkWithUser(name);
        return ResponseEntity.ok(works);
    }
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UserRequets requets , BindingResult result){
        if (result.hasErrors()){
            Map errors = new HashMap();
            result.getFieldErrors().forEach((error)->{
                errors.put(error.getField(),error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok(service.create(requets));
    }

    @GetMapping
    public ResponseEntity<List<Users>> all(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        Optional<Users> user = service.getOne(id);
        if (user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("Không có user hợp lệ !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        if (service.delete(id)){
            return ResponseEntity.ok("Xóa thành công");
        }
        return ResponseEntity.badRequest().body("Xóa không thành công !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                        @RequestBody @Valid UpdateUserRequest requets,
                                        BindingResult result){
        if (result.hasErrors()){
            Map errors = new HashMap();
            result.getFieldErrors().forEach((error)->{
                errors.put(error.getField(),error.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        Optional<Users> user = service.update(id,requets);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.badRequest().body("Cập nhật không thành coong !");
    }
}
