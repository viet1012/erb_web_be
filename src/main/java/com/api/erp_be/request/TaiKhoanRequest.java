package com.api.erp_be.request;


import lombok.Data;

@Data
public class TaiKhoanRequest {
    private String taiKhoan;
    private String matKhau;
    private String hoTen;
    private String chucVu;
    private String phanQuyen;
    private String nguoiTao;
    private String nguoiCapNhat;
}
