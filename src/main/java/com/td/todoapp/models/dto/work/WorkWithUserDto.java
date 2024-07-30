package com.td.todoapp.models.dto.work;

import com.td.todoapp.models.dto.user.UserDto;

import java.util.List;

public class WorkWithUserDto {
    private UserDto user;
    private List<WorkDto> works;

    public WorkWithUserDto(UserDto user, List<WorkDto> works) {
        this.user = user;
        this.works = works;
    }

    public WorkWithUserDto() {
    }

    public UserDto getUserDto() {
        return user;
    }

    public void setUserDto(UserDto userDto) {
        this.user = userDto;
    }

    public List<WorkDto> getWorkDto() {
        return works;
    }

    public void setWorkDto(List<WorkDto> workDto) {
        this.works = workDto;
    }
}
