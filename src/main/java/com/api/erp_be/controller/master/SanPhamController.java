package com.api.erp_be.controller.master;

import com.api.erp_be.model.SanPham;
import com.api.erp_be.request.master.SanPhamRequest;
import com.api.erp_be.response.master.SanPhamResponse;
import com.api.erp_be.service.master.SanPhamService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.*;
@RestController
@RequestMapping("/api/sanpham")
public class SanPhamController {

    private final SanPhamService service;

    public SanPhamController(SanPhamService service) {
        this.service = service;
    }

    // --- Phân trang ---
    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "stt") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<SanPhamResponse> pageResult = service.getPage(page, size, sortBy, sortDir);

        Map<String, Object> response = new HashMap<>();
        response.put("data", pageResult.getContent());
        response.put("currentPage", pageResult.getNumber());
        response.put("totalItems", pageResult.getTotalElements());
        response.put("totalPages", pageResult.getTotalPages());
        response.put("sort", sortBy);
        response.put("direction", sortDir);

        return ResponseEntity.ok(response);
    }


    // --- Thống kê ---
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = service.getStatistics();
        return ResponseEntity.ok(stats);
    }

    // --- Lấy tất cả ---
    @GetMapping
    public ResponseEntity<List<SanPhamResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // --- Lấy theo ID ---
    @GetMapping("/{id}")
    public ResponseEntity<SanPhamResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // --- Tạo mới ---
    @PostMapping
    public ResponseEntity<SanPhamResponse> create(@RequestBody SanPhamRequest req) {
        SanPhamResponse created = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // --- Cập nhật ---
    @PutMapping("/{id}")
    public ResponseEntity<SanPhamResponse> update(@PathVariable Integer id, @RequestBody SanPhamRequest req) {
        SanPhamResponse updated = service.update(id, req);
        return ResponseEntity.ok(updated);
    }

    // --- Xóa ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Xuất Excel ---
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToExcel() {
        ByteArrayInputStream inputStream = service.exportToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=danh_sach_san_pham.xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        byte[] bytes = inputStream.readAllBytes();
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .body(bytes);
    }

    // --- Nhập Excel ---
    @PostMapping("/import")
    public ResponseEntity<List<SanPhamResponse>> importExcel(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<SanPhamResponse> imported = service.importFromExcel(file);
        return ResponseEntity.ok(imported);
    }
}
