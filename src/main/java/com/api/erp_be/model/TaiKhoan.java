package com.api.erp_be.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "\"TAI_KHOAN\"") // ⚠️ Giữ nguyên chữ HOA và thêm \"
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"STT\"") // Nếu DB để STT hoa thì phải có "
    private Integer stt;

    @Column(name = "\"TaiKhoan\"", nullable = false, unique = true)
    private String taiKhoan;

    @Column(name = "\"MatKhau\"", nullable = false)
    private String matKhau;

    @Column(name = "\"HoTen\"")
    private String hoTen;

    @Column(name = "\"ChucVu\"")
    private String chucVu;

    @Column(name = "\"PhanQuyen\"")
    private String phanQuyen;

    @Column(name = "\"NgayTao\"")
    private LocalDateTime ngayTao;

    @Column(name = "\"NguoiTao\"")
    private String nguoiTao;

    @Column(name = "\"NgayCapNhat\"")
    private LocalDateTime ngayCapNhat;

    @Column(name = "\"NguoiCapNhat\"")
    private String nguoiCapNhat;
}
