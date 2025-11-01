package com.api.erp_be.response.master;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChiTietResponse {
    private Integer stt;
    private String maChiTiet;
    private String tenChiTiet;
    private String nhomChiTiet;
    private String donViChiTiet;
    private Double trongLuong;
    private String donViTrongLuong;
    private LocalDateTime ngayTao;
    private String nguoiTao;
    private LocalDateTime ngayCapNhat;
    private String nguoiCapNhat;
}
