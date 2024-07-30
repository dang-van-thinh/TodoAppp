package com.td.todoapp.repository;

import com.td.todoapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByName(String name);

}
