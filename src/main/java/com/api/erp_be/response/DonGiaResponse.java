package com.api.erp_be.response;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DonGiaResponse {
    private Integer stt;
    private String maSanPham;
    private String tenSanPham;
    private String maKhachHang;
    private Double donGia;
    private Double donViSuDung;
    private LocalDateTime ngayTao;
    private String nguoiTao;
    private LocalDateTime ngayCapNhat;
    private String nguoiCapNhat;
}
