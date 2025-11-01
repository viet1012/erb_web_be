package com.api.erp_be.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ma_cong_doan") // đổi về chữ thường
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaCongDoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stt;

    @Column(name = "ma_cong_doan")
    private String maCongDoan;

    @Column(name = "ten_cong_doan")
    private String tenCongDoan;

    @Column(name = "thoi_gian_gia_cong")
    private Double thoiGianGiaCong;

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

