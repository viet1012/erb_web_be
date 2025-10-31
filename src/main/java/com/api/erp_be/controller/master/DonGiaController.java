package com.api.erp_be.controller.master;

import com.api.erp_be.request.DonGiaRequest;
import com.api.erp_be.response.DonGiaResponse;
import com.api.erp_be.service.master.DonGiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/don-gia")
public class DonGiaController {

    private final DonGiaService donGiaService;

    public DonGiaController(DonGiaService donGiaService) {
        this.donGiaService = donGiaService;
    }

    /** 🔹 Lấy danh sách tất cả đơn giá */
    @GetMapping
    public ResponseEntity<List<DonGiaResponse>> getAll() {
        return ResponseEntity.ok(donGiaService.getAll());
    }

    /** 🔹 Lấy đơn giá theo ID */
    @GetMapping("/{id}")
    public ResponseEntity<DonGiaResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(donGiaService.getById(id));
    }

    /** 🔹 Thêm mới đơn giá */
    @PostMapping
    public ResponseEntity<DonGiaResponse> create(@RequestBody DonGiaRequest req) {
        return ResponseEntity.ok(donGiaService.create(req));
    }

    /** 🔹 Cập nhật đơn giá theo ID */
    @PutMapping("/{id}")
    public ResponseEntity<DonGiaResponse> update(@PathVariable Integer id, @RequestBody DonGiaRequest req) {
        return ResponseEntity.ok(donGiaService.update(id, req));
    }

    /** 🔹 Xóa đơn giá theo ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        donGiaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
