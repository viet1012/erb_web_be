package com.api.erp_be.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "CHI_TIET")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stt;

    @Column(name = "MaChiTiet")
    private String maChiTiet;

    @Column(name = "TenChiTiet")
    private String tenChiTiet;

    @Column(name = "NhomChiTiet")
    private String nhomChiTiet;

    @Column(name = "DonVi_ChiTiet")
    private String donViChiTiet;

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

    // Getters và Setters
}
