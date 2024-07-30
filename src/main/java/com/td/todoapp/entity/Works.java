package com.td.todoapp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "works")
public class Works {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ten;
    private String trangThai;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    public Works(String ten, String trangThai, LocalDate ngayBatDau, LocalDate ngayKetThuc , Users user) {
        this.ten = ten;
        this.trangThai = trangThai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.users = user;
    }

    @Override
    public String toString() {
        return "Works{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                ", username=" + users.getName() +
                ", role=" + users.getRole() +
                '}';
    }

    public Works() {
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}
