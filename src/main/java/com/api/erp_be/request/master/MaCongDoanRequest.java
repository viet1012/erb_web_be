package com.api.erp_be.request.master;


import lombok.Data;

@Data
public class MaCongDoanRequest {
    private String maCongDoan;
    private String tenCongDoan;
    private Double thoiGianGiaCong;
    private String nguoiTao;
}
