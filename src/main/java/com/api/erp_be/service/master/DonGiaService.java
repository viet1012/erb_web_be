package com.api.erp_be.service.master;


import com.api.erp_be.mapper.master.DonGiaMapper;
import com.api.erp_be.model.DonGia;
import com.api.erp_be.repository.master.DonGiaRepository;
import com.api.erp_be.repository.master.SanPhamRepository;
import com.api.erp_be.request.DonGiaRequest;
import com.api.erp_be.response.DonGiaResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonGiaService {

    private final SanPhamRepository sanPhamRepository;
    private final DonGiaRepository donGiaRepository;
    private final DonGiaMapper donGiaMapper;

    public DonGiaService(DonGiaRepository donGiaRepository, DonGiaMapper donGiaMapper, SanPhamRepository sanPhamRepository) {
        this.donGiaRepository = donGiaRepository;
        this.donGiaMapper = donGiaMapper;
        this.sanPhamRepository = sanPhamRepository;
    }


    /** 🔹 Lấy toàn bộ danh sách đơn giá */
    public List<DonGiaResponse> getAll() {
        return donGiaRepository.findAll()
                .stream()
                .map(donGiaMapper::toResponse)
                .collect(Collectors.toList());
    }

    /** 🔹 Lấy 1 đơn giá theo ID */
    public DonGiaResponse getById(Integer id) {
        DonGia entity = donGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn giá có STT = " + id));
        return donGiaMapper.toResponse(entity);
    }

    /** 🔹 Thêm mới đơn giá */
    public DonGiaResponse create(DonGiaRequest req) {
        // Kiểm tra mã sản phẩm tồn tại
        if (!sanPhamRepository.existsByMaSanPham(req.getMaSanPham())) {
            throw new RuntimeException("Mã sản phẩm '" + req.getMaSanPham() + "' không tồn tại trong hệ thống!");
        }

        DonGia entity = donGiaMapper.toEntity(req);
        DonGia saved = donGiaRepository.save(entity);
        return donGiaMapper.toResponse(saved);
    }


    /** 🔹 Cập nhật đơn giá */
    public DonGiaResponse update(Integer id, DonGiaRequest req) {
        DonGia entity = donGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn giá có STT = " + id));

        // Kiểm tra mã sản phẩm có tồn tại
        if (!sanPhamRepository.existsByMaSanPham(req.getMaSanPham())) {
            throw new RuntimeException("Mã sản phẩm '" + req.getMaSanPham() + "' không tồn tại!");
        }

        entity.setMaSanPham(req.getMaSanPham());
        entity.setMaKhachHang(req.getMaKhachHang());
        entity.setDonGia(req.getDonGia());
        entity.setDonViSuDung(req.getDonViSuDung());
        entity.setNgayCapNhat(LocalDateTime.now());
        entity.setNguoiCapNhat("system");

        DonGia updated = donGiaRepository.save(entity);
        return donGiaMapper.toResponse(updated);
    }


    /** 🔹 Xóa đơn giá */
    public void delete(Integer id) {
        if (!donGiaRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy đơn giá có STT = " + id);
        }
        donGiaRepository.deleteById(id);
    }
}
