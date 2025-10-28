package com.api.erp_be.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "KHACH_HANG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stt;

    @Column(name = "MaKhachHang")
    private String maKhachHang;

    @Column(name = "TenKhachHang")
    private String tenKhachHang;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "Email")
    private String email;

    @Column(name = "MaSoThue")
    private String maSoThue;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "NguoiTao")
    private String nguoiTao;

    @Column(name = "NgayCapNhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "NguoiCapNhat")
    private String nguoiCapNhat;

    // Getters và Setters
    // (có thể dùng Lombok @Data nếu bạn đã bật Lombok)
}
