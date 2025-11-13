package com.api.erp_be.response.master;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SanPhamResponse {
    private Integer stt;
    private String maSanPham;
    private String tenSanPham;
    private String nhomSanPham;
    private Double trongLuong;
    private String donViTrongLuong;
    private LocalDateTime ngayTao;
    private String nguoiTao;
    private LocalDateTime ngayCapNhat;
    private String nguoiCapNhat;
    private Integer soLuongLenhSanXuat;
    private String status;
}
