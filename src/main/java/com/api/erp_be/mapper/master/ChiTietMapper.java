package com.api.erp_be.mapper.master;

import com.api.erp_be.model.ChiTiet;
import com.api.erp_be.request.master.ChiTietRequest;
import com.api.erp_be.response.master.ChiTietResponse;
import org.springframework.stereotype.Component;

@Component
public class ChiTietMapper {

    public ChiTiet toEntity(ChiTietRequest req) {
        ChiTiet entity = new ChiTiet();
        entity.setMaChiTiet(req.getMaChiTiet());
        entity.setTenChiTiet(req.getTenChiTiet());
        entity.setNhomChiTiet(req.getNhomChiTiet());
        entity.setDonViChiTiet(req.getDonViChiTiet());
        entity.setTrongLuong(req.getTrongLuong());
        entity.setDonViTrongLuong(req.getDonViTrongLuong());
        entity.setNguoiTao(req.getNguoiTao());
        entity.setNguoiCapNhat(req.getNguoiCapNhat());
        return entity;
    }

    public ChiTietResponse toResponse(ChiTiet entity) {
        ChiTietResponse res = new ChiTietResponse();
        res.setStt(entity.getStt());
        res.setMaChiTiet(entity.getMaChiTiet());
        res.setTenChiTiet(entity.getTenChiTiet());
        res.setNhomChiTiet(entity.getNhomChiTiet());
        res.setDonViChiTiet(entity.getDonViChiTiet());
        res.setTrongLuong(entity.getTrongLuong());
        res.setDonViTrongLuong(entity.getDonViTrongLuong());
        res.setNgayTao(entity.getNgayTao());
        res.setNguoiTao(entity.getNguoiTao());
        res.setNgayCapNhat(entity.getNgayCapNhat());
        res.setNguoiCapNhat(entity.getNguoiCapNhat());
        return res;
    }
}
