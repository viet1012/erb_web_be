package com.api.erp_be.service;

import com.api.erp_be.mapper.SanPhamMapper;
import com.api.erp_be.model.SanPham;
import com.api.erp_be.repository.SanPhamRepository;
import com.api.erp_be.request.SanPhamRequest;
import com.api.erp_be.response.SanPhamResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SanPhamService {

    private final SanPhamRepository repository;
    private final SanPhamMapper mapper;

    public SanPhamService(SanPhamRepository repository, SanPhamMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SanPhamResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public SanPhamResponse getById(Integer id) {
        SanPham sp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với STT: " + id));
        return mapper.toResponse(sp);
    }

    public SanPhamResponse create(SanPhamRequest req) {
        if (repository.existsByMaSanPham(req.getMaSanPham())) {
            throw new RuntimeException("Mã sản phẩm đã tồn tại!");
        }
        SanPham sp = mapper.toEntity(req);
        repository.save(sp);
        return mapper.toResponse(sp);
    }

    public SanPhamResponse update(Integer id, SanPhamRequest req) {
        SanPham sp = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với STT: " + id));
        sp.setTenSanPham(req.getTenSanPham());
        sp.setNhomSanPham(req.getNhomSanPham());
        sp.setTrongLuong(req.getTrongLuong());
        sp.setDonViTrongLuong(req.getDonViTrongLuong());
        sp.setNgayCapNhat(req.getNgayCapNhat());
        sp.setNguoiCapNhat(req.getNguoiCapNhat());
        sp.setSoLuongLenhSanXuat(req.getSoLuongLenhSanXuat());
        repository.save(sp);
        return mapper.toResponse(sp);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Không tồn tại sản phẩm để xóa!");
        }
        repository.deleteById(id);
    }
}
