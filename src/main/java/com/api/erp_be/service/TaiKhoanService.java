package com.api.erp_be.service;

import com.api.erp_be.mapper.TaiKhoanMapper;
import com.api.erp_be.model.TaiKhoan;
import com.api.erp_be.repository.TaiKhoanRepository;
import com.api.erp_be.request.TaiKhoanRequest;
import com.api.erp_be.response.LoginResponse;
import com.api.erp_be.response.TaiKhoanResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaiKhoanService {

    private final TaiKhoanRepository repository;
    private final TaiKhoanMapper mapper;

    public TaiKhoanService(TaiKhoanRepository repository, TaiKhoanMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public LoginResponse login(String taiKhoan, String matKhau) {
        TaiKhoan user = repository.findByTaiKhoan(taiKhoan)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        if (!user.getMatKhau().equals(matKhau)) {
            return new LoginResponse(false, "Sai mật khẩu", null, null);
        }

        return new LoginResponse(true, "Đăng nhập thành công", user.getPhanQuyen(), user.getHoTen());
    }

    public List<TaiKhoanResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public TaiKhoanResponse getById(Integer id) {
        TaiKhoan tk = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản ID: " + id));
        return mapper.toResponse(tk);
    }

    public TaiKhoanResponse create(TaiKhoanRequest dto) {
        TaiKhoan entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    public TaiKhoanResponse update(Integer id, TaiKhoanRequest dto) {
        TaiKhoan existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản ID: " + id));
        mapper.updateEntity(existing, dto);
        return mapper.toResponse(repository.save(existing));
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
