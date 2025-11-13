package com.api.erp_be.mapper.master;

import com.api.erp_be.model.SanPham;
import com.api.erp_be.request.master.SanPhamRequest;
import com.api.erp_be.response.master.SanPhamResponse;
import org.springframework.stereotype.Component;

@Component
public class SanPhamMapper {

    public SanPham toEntity(SanPhamRequest dto) {
        SanPham entity = new SanPham();
        entity.setTenSanPham(dto.getTenSanPham());
        entity.setNhomSanPham(dto.getNhomSanPham());
        entity.setTrongLuong(dto.getTrongLuong());
        entity.setDonViTrongLuong(dto.getDonViTrongLuong());
        entity.setNguoiTao(dto.getNguoiTao());
        entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        entity.setSoLuongLenhSanXuat(dto.getSoLuongLenhSanXuat());
        return entity;
    }

    public SanPhamResponse toResponse(SanPham entity) {
        SanPhamResponse res = new SanPhamResponse();
        res.setStt(entity.getStt());
        res.setMaSanPham(entity.getMaSanPham());
        res.setTenSanPham(entity.getTenSanPham());
        res.setNhomSanPham(entity.getNhomSanPham());
        res.setTrongLuong(entity.getTrongLuong());
        res.setDonViTrongLuong(entity.getDonViTrongLuong());
        res.setNgayTao(entity.getNgayTao());
        res.setNguoiTao(entity.getNguoiTao());
        res.setNgayCapNhat(entity.getNgayCapNhat());
        res.setNguoiCapNhat(entity.getNguoiCapNhat());
        res.setSoLuongLenhSanXuat(entity.getSoLuongLenhSanXuat());
        res.setStatus(entity.getStatus());
        return res;
    }
}
