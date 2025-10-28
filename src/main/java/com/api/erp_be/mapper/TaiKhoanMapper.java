package com.api.erp_be.mapper;

import com.api.erp_be.model.TaiKhoan;
import com.api.erp_be.request.TaiKhoanRequest;
import com.api.erp_be.response.TaiKhoanResponse;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class TaiKhoanMapper {

    public TaiKhoan toEntity(TaiKhoanRequest dto) {
        TaiKhoan tk = new TaiKhoan();
        tk.setTaiKhoan(dto.getTaiKhoan());
        tk.setMatKhau(dto.getMatKhau());
        tk.setHoTen(dto.getHoTen());
        tk.setChucVu(dto.getChucVu());
        tk.setPhanQuyen(dto.getPhanQuyen());
        tk.setNguoiTao(dto.getNguoiTao());
        tk.setNguoiCapNhat(dto.getNguoiCapNhat());
        tk.setNgayTao(LocalDateTime.now());
        return tk;
    }

    public TaiKhoanResponse toResponse(TaiKhoan entity) {
        TaiKhoanResponse res = new TaiKhoanResponse();
        res.setStt(entity.getStt());
        res.setTaiKhoan(entity.getTaiKhoan());
        res.setHoTen(entity.getHoTen());
        res.setChucVu(entity.getChucVu());
        res.setPhanQuyen(entity.getPhanQuyen());
        res.setNguoiTao(entity.getNguoiTao());
        res.setNguoiCapNhat(entity.getNguoiCapNhat());
        res.setNgayTao(entity.getNgayTao());
        res.setNgayCapNhat(entity.getNgayCapNhat());
        return res;
    }

    public void updateEntity(TaiKhoan entity, TaiKhoanRequest dto) {
        entity.setTaiKhoan(dto.getTaiKhoan());
        entity.setMatKhau(dto.getMatKhau());
        entity.setHoTen(dto.getHoTen());
        entity.setChucVu(dto.getChucVu());
        entity.setPhanQuyen(dto.getPhanQuyen());
        entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        entity.setNgayCapNhat(LocalDateTime.now());
    }
}
