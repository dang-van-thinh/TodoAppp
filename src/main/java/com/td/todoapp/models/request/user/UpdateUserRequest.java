package com.td.todoapp.models.request.user;

import jakarta.validation.constraints.NotEmpty;

public class UpdateUserRequest {
    @NotEmpty(message = "Không để trống trường tên !")
    private String name;
    @NotEmpty(message = "Không để trống trường password !")
    private String password;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
