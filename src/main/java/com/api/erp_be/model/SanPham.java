package com.api.erp_be.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "SAN_PHAM")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STT")
    private Integer stt;

    @Column(name = "MaSanPham")
    private String maSanPham;

    @Column(name = "TenSanPham")
    private String tenSanPham;

    @Column(name = "NhomSanPham")
    private String nhomSanPham;

    @Column(name = "TrongLuong")
    private Double trongLuong;

    @Column(name = "DonVi_TrongLuong")
    private String donViTrongLuong;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "NguoiTao")
    private String nguoiTao;

    @Column(name = "NgayCapNhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "NguoiCapNhat")
    private String nguoiCapNhat;

    @Column(name = "SoLuong_LenhSanXuat")
    private Integer soLuongLenhSanXuat;

    // Quan hệ 1-nhiều với DON_GIA
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DonGia> donGias;
}
