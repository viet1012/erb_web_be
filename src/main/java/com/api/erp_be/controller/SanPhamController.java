package com.api.erp_be.controller;
import com.api.erp_be.request.SanPhamRequest;
import com.api.erp_be.response.SanPhamResponse;
import com.api.erp_be.service.SanPhamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanpham")
public class SanPhamController {

    private final SanPhamService service;

    public SanPhamController(SanPhamService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SanPhamResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<SanPhamResponse> create(@RequestBody SanPhamRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPhamResponse> update(@PathVariable Integer id, @RequestBody SanPhamRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
