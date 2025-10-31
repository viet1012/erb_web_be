package com.api.erp_be.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "don_gia")
public class DonGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stt")
    private Integer stt;

    // ✅ Field này được phép ghi xuống DB
    @Column(name = "ma_san_pham")
    private String maSanPham;

    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @Column(name = "don_gia")
    private Double donGia;

    @Column(name = "don_vi_su_dung")
    private Double donViSuDung;

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

    // ✅ Quan hệ chỉ để đọc thông tin sản phẩm
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_san_pham", referencedColumnName = "ma_san_pham", insertable = false, updatable = false)
    private SanPham sanPham;
}
