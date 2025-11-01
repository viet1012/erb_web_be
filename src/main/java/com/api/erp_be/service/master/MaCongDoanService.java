package com.api.erp_be.service.master;

import com.api.erp_be.mapper.master.MaCongDoanMapper;
import com.api.erp_be.model.MaCongDoan;
import com.api.erp_be.repository.master.MaCongDoanRepository;

import com.api.erp_be.request.master.MaCongDoanRequest;
import com.api.erp_be.response.master.MaCongDoanResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaCongDoanService {

    private final MaCongDoanRepository repository;
    private final MaCongDoanMapper mapper;

    public MaCongDoanService(MaCongDoanRepository repository, MaCongDoanMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MaCongDoanResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public MaCongDoanResponse getById(Integer id) {
        MaCongDoan entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã công đoạn có STT = " + id));
        return mapper.toResponse(entity);
    }

    public MaCongDoanResponse create(MaCongDoanRequest req) {
        if (repository.existsByMaCongDoan(req.getMaCongDoan())) {
            throw new RuntimeException("Mã công đoạn '" + req.getMaCongDoan() + "' đã tồn tại!");
        }
        MaCongDoan saved = repository.save(mapper.toEntity(req));
        return mapper.toResponse(saved);
    }

    public MaCongDoanResponse update(Integer id, MaCongDoanRequest req) {
        MaCongDoan entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công đoạn có STT = " + id));

        entity.setMaCongDoan(req.getMaCongDoan());
        entity.setTenCongDoan(req.getTenCongDoan());
        entity.setThoiGianGiaCong(req.getThoiGianGiaCong());
        entity.setNgayCapNhat(LocalDateTime.now());
        entity.setNguoiCapNhat("system");

        MaCongDoan updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy công đoạn có STT = " + id);
        }
        repository.deleteById(id);
    }
}
