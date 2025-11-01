package com.api.erp_be.service.master;


import com.api.erp_be.mapper.master.ChiTietMapper;
import com.api.erp_be.model.ChiTiet;
import com.api.erp_be.repository.master.ChiTietRepository;
import com.api.erp_be.request.master.ChiTietRequest;
import com.api.erp_be.response.master.ChiTietResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChiTietService {

    private final ChiTietRepository repository;
    private final ChiTietMapper mapper;

    public ChiTietService(ChiTietRepository repository, ChiTietMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ChiTietResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public ChiTietResponse getById(Integer stt) {
        ChiTiet entity = repository.findById(stt)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết có STT = " + stt));
        return mapper.toResponse(entity);
    }

    public ChiTietResponse create(ChiTietRequest req) {
        if (repository.existsByMaChiTiet(req.getMaChiTiet())) {
            throw new RuntimeException("Mã chi tiết '" + req.getMaChiTiet() + "' đã tồn tại!");
        }
        ChiTiet entity = mapper.toEntity(req);
        ChiTiet saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    public ChiTietResponse update(Integer stt, ChiTietRequest req) {
        ChiTiet entity = repository.findById(stt)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết có STT = " + stt));

        entity.setTenChiTiet(req.getTenChiTiet());
        entity.setNhomChiTiet(req.getNhomChiTiet());
        entity.setDonViChiTiet(req.getDonViChiTiet());
        entity.setTrongLuong(req.getTrongLuong());
        entity.setDonViTrongLuong(req.getDonViTrongLuong());
        entity.setNguoiCapNhat(req.getNguoiCapNhat());

        ChiTiet updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Integer stt) {
        if (!repository.existsById(stt)) {
            throw new RuntimeException("Không tìm thấy chi tiết có STT = " + stt);
        }
        repository.deleteById(stt);
    }
}
