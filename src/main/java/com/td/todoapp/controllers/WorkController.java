package com.td.todoapp.controllers;

import com.td.todoapp.entity.Works;
import com.td.todoapp.models.request.work.UpdateWorkRequest;
import com.td.todoapp.models.request.work.WorkRequets;
import com.td.todoapp.services.servicesImp.WorkServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/works")
public class WorkController {
    @Autowired
    private WorkServiceImp service;


    @GetMapping({"", "/"})
    public ResponseEntity<List<Works>> list() {
        List<Works> works = service.getAll();
        return ResponseEntity.ok(works);
    }

    @PostMapping
    public ResponseEntity<Object> store(@RequestBody @Valid WorkRequets requets, BindingResult result) {
        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(errors);
        }
        Works works = service.create(requets);
        if (works != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(works);
        }
        return ResponseEntity.badRequest().body("Thêm công việc không thành coong !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody UpdateWorkRequest request,
                                         BindingResult result,
                                         @PathVariable int id) {
        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(errors);
        }
        Works works = service.update(id, request);
        if (works == null) {
            return ResponseEntity.badRequest().body("Cập nhật không thành công !");
        }
        return ResponseEntity.ok(works);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable int id) {
        Works works = service.getOne(id);
        if (works == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy công việc phù hợp nào !");
        }
        return ResponseEntity.ok(works);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        boolean isDelete = service.delete(id);
        if (isDelete) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.badRequest().body("Xóa không thành công !");
    }


    @GetMapping("/filter")
    public ResponseEntity<List<Works>> filter(@RequestParam(name = "key", required = false) String ten,
                                              @RequestParam(name = "status", required = false) String status,
                                              @RequestParam(name = "start", required = false) LocalDate start,
                                              @RequestParam(name = "end", required = false) LocalDate end
    ) {
        return ResponseEntity.ok(service.filter(ten,status,start,end));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Works>> search(@RequestParam(name = "key", required = false) String ten
//                                              @RequestParam(name = "status", required = false) Short status
    ) {
        return ResponseEntity.ok(service.search(ten));
    }


}
