package com.api.erp_be.mapper.master;
import com.api.erp_be.model.DonGia;
import com.api.erp_be.request.DonGiaRequest;
import com.api.erp_be.response.DonGiaResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DonGiaMapper {

    public DonGia toEntity(DonGiaRequest req) {
        DonGia entity = new DonGia();
        entity.setMaSanPham(req.getMaSanPham());
        entity.setMaKhachHang(req.getMaKhachHang());
        entity.setDonGia(req.getDonGia());
        entity.setDonViSuDung(req.getDonViSuDung());
        entity.setNgayTao(LocalDateTime.now());
        entity.setNguoiTao("system"); // hoặc lấy từ user hiện tại
        return entity;
    }

    public DonGiaResponse toResponse(DonGia entity) {
        DonGiaResponse res = new DonGiaResponse();
        res.setStt(entity.getStt());
        res.setMaSanPham(entity.getMaSanPham());
        res.setTenSanPham(entity.getSanPham() != null ? entity.getSanPham().getTenSanPham() : null);
        res.setMaKhachHang(entity.getMaKhachHang());
        res.setDonGia(entity.getDonGia() != null ? entity.getDonGia() : null);
        res.setDonViSuDung(entity.getDonViSuDung());
        res.setNguoiTao(entity.getNguoiTao());
        res.setNgayTao(entity.getNgayTao());
        res.setNguoiCapNhat(entity.getNguoiCapNhat());
        res.setNgayCapNhat(entity.getNgayCapNhat());
        return res;
    }

}
