package com.td.todoapp.models.request.user;

import jakarta.validation.constraints.NotEmpty;

public class UserRequets {
    @NotEmpty(message = "Không để trống trường tên !")
    private String name;
    @NotEmpty(message = "Không để trống trường password !")
    private String password;

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
