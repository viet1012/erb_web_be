package com.api.erp_be.controller.master;
import com.api.erp_be.request.master.SanPhamRequest;
import com.api.erp_be.response.master.SanPhamResponse;
import com.api.erp_be.service.master.SanPhamService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
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

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToExcel() {
        ByteArrayInputStream inputStream = service.exportToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=danh_sach_san_pham.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStream.readAllBytes());
    }

    @PostMapping("/import")
    public ResponseEntity<List<SanPhamResponse>> importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("❌ File upload trống!");
        }
        List<SanPhamResponse> imported = service.importFromExcel(file);
        return ResponseEntity.ok(imported);
    }

}
