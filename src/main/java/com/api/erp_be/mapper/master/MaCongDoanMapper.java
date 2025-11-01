package com.api.erp_be.mapper.master;



import com.api.erp_be.model.MaCongDoan;
import com.api.erp_be.request.master.MaCongDoanRequest;
import com.api.erp_be.response.master.MaCongDoanResponse;
import org.springframework.stereotype.Component;

@Component
public class MaCongDoanMapper {

    public MaCongDoan toEntity(MaCongDoanRequest req) {
        MaCongDoan entity = new MaCongDoan();
        entity.setMaCongDoan(req.getMaCongDoan());
        entity.setTenCongDoan(req.getTenCongDoan());
        entity.setThoiGianGiaCong(req.getThoiGianGiaCong());
        entity.setNguoiTao(req.getNguoiTao());
        return entity;
    }

    public MaCongDoanResponse toResponse(MaCongDoan entity) {
        MaCongDoanResponse res = new MaCongDoanResponse();
        res.setStt(entity.getStt());
        res.setMaCongDoan(entity.getMaCongDoan());
        res.setTenCongDoan(entity.getTenCongDoan());
        res.setThoiGianGiaCong(entity.getThoiGianGiaCong());
        res.setNgayTao(entity.getNgayTao());
        res.setNguoiTao(entity.getNguoiTao());
        res.setNgayCapNhat(entity.getNgayCapNhat());
        res.setNguoiCapNhat(entity.getNguoiCapNhat());
        return res;
    }
}
