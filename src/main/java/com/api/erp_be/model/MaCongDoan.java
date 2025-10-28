package com.api.erp_be.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "MA_CONG_DOAN")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaCongDoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stt;

    @Column(name = "MaCongDoan")
    private String maCongDoan;

    @Column(name = "TenCongDoan")
    private String tenCongDoan;

    @Column(name = "ThoiGian_GiaCong")
    private Double thoiGianGiaCong;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "NguoiTao")
    private String nguoiTao;

    @Column(name = "NgayCapNhat")
    private LocalDateTime ngayCapNhat;

    @Column(name = "NguoiCapNhat")
    private String nguoiCapNhat;

}
