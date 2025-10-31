package com.api.erp_be.request;

import lombok.Data;

@Data
public class DonGiaRequest {
    private String maSanPham;
    private String maKhachHang;
    private Double donGia;
    private Double donViSuDung;
    private String nguoiTao;
    private String nguoiCapNhat;
}

