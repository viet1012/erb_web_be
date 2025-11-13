package com.api.erp_be.request.master;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SanPhamRequest {
    private String tenSanPham;
    private String nhomSanPham;
    private Double trongLuong;
    private String donViTrongLuong;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer soLuongLenhSanXuat;
}
