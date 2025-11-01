package com.api.erp_be.response.master;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaCongDoanResponse {
    private Integer stt;
    private String maCongDoan;
    private String tenCongDoan;
    private Double thoiGianGiaCong;
    private LocalDateTime ngayTao;
    private String nguoiTao;
    private LocalDateTime ngayCapNhat;
    private String nguoiCapNhat;
}
