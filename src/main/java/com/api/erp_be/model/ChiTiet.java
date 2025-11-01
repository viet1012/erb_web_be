package com.api.erp_be.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chi_tiet") // đổi về chữ thường
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stt;

    @Column(name = "ma_chi_tiet")
    private String maChiTiet;

    @Column(name = "ten_chi_tiet")
    private String tenChiTiet;

    @Column(name = "nhom_chi_tiet")
    private String nhomChiTiet;

    @Column(name = "don_vi_chi_tiet")
    private String donViChiTiet;

    @Column(name = "trong_luong")
    private Double trongLuong;

    @Column(name = "don_vi_trong_luong")
    private String donViTrongLuong;

    @CreationTimestamp
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @UpdateTimestamp
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;
}
