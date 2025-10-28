package com.api.erp_be.controller;

import com.api.erp_be.request.TaiKhoanRequest;
import com.api.erp_be.response.TaiKhoanResponse;
import com.api.erp_be.service.TaiKhoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taikhoan")
@CrossOrigin(origins = "*")
public class TaiKhoanController {

    private final TaiKhoanService service;

    public TaiKhoanController(TaiKhoanService service) {
        this.service = service;
    }

    @GetMapping
    public List<TaiKhoanResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public TaiKhoanResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public TaiKhoanResponse create(@RequestBody TaiKhoanRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public TaiKhoanResponse update(@PathVariable Integer id, @RequestBody TaiKhoanRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
