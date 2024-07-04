package com.td.todoapp.controllers;

import com.td.todoapp.dto.WorkDto;
import com.td.todoapp.models.Works;
import com.td.todoapp.repository.WorkRepository;
import com.td.todoapp.services.WorkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/works")
public class WorkController {
    @Autowired
    private WorkService service;


    @GetMapping({"", "/"})
    public ResponseEntity<List<Works>> list() {
        List works = service.getAll();
        return ResponseEntity.ok(works);
    }

    @PostMapping
    public ResponseEntity<Object> store(@RequestBody @Valid WorkDto work, BindingResult result) {
        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(errors);
        }
        Works works = service.store(work);
        if (works != null) {
            return ResponseEntity.ok(service.store(work));
        }
        return ResponseEntity.badRequest().body("Thêm công việc không thành coong !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody WorkDto work,
                                         BindingResult result,
                                         @PathVariable int id) {
        if (result.hasErrors()) {

            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(errors);
        }
        Works works = service.update(id, work);
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
            return ResponseEntity.ok("Xóa thành công công việc !");
        }
        return ResponseEntity.badRequest().body("Xóa không thành công !");
    }


    @GetMapping("/search")
    public ResponseEntity<List<Works>> search(@RequestParam(name = "key", required = false) String ten,
                                              @RequestParam(name = "status", required = false) Short status,
                                              @RequestParam(name = "ngayB", required = false, defaultValue = "") String ngayB,
                                              @RequestParam(name = "ngayK", required = false, defaultValue = "") String ngayK
    ) {

        LocalDateTime ngayS = LocalDateTime.parse(ngayB);
        LocalDateTime ngayE = LocalDateTime.parse(ngayK);
        return ResponseEntity.ok(service.search(ngayS, ngayE, status, ten));
    }


}
