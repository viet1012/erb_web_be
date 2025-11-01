package com.api.erp_be.request.master;

import lombok.Data;

@Data
public class ChiTietRequest {
    private String maChiTiet;
    private String tenChiTiet;
    private String nhomChiTiet;
    private String donViChiTiet;
    private Double trongLuong;
    private String donViTrongLuong;
    private String nguoiTao;
    private String nguoiCapNhat;
}
