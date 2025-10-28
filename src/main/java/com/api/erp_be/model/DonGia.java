package com.api.erp_be.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DON_GIA")
public class DonGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STT")
    private Integer stt;

    @Column(name = "MaSanPham", insertable = false, updatable = false)
    private String maSanPham;

    @Column(name = "MaKhachHang")
    private String maKhachHang;

    @Column(name = "DonGia")
    private String donGia;

    @Column(name = "DonVi_SuDung")
    private Double donViSuDung;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "NguoiTao")
    private String nguoiTao;

    @Column(name = "NgayCapNhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "NguoiCapNhat")
    private String nguoiCapNhat;

    // ManyToOne -> SAN_PHAM
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSanPham", referencedColumnName = "MaSanPham")
    private SanPham sanPham;
}
