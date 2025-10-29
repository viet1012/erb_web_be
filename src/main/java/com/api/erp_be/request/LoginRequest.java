package com.api.erp_be.request;


import lombok.Data;

@Data
public class LoginRequest {
    private String taiKhoan;
    private String matKhau;
}
