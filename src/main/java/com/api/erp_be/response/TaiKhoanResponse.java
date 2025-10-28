package com.api.erp_be.response;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaiKhoanResponse {
    private Integer stt;
    private String taiKhoan;
    private String hoTen;
    private String chucVu;
    private String phanQuyen;
    private String nguoiTao;
    private String nguoiCapNhat;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
}
