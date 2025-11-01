package com.api.erp_be.controller.master;


import com.api.erp_be.request.master.ChiTietRequest;
import com.api.erp_be.response.master.ChiTietResponse;
import com.api.erp_be.service.master.ChiTietService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chi-tiet")
@CrossOrigin(origins = "*")
public class ChiTietController {

    private final ChiTietService service;

    public ChiTietController(ChiTietService service) {
        this.service = service;
    }

    @GetMapping
    public List<ChiTietResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{stt}")
    public ChiTietResponse getById(@PathVariable Integer stt) {
        return service.getById(stt);
    }

    @PostMapping
    public ChiTietResponse create(@RequestBody ChiTietRequest req) {
        return service.create(req);
    }

    @PutMapping("/{stt}")
    public ChiTietResponse update(@PathVariable Integer stt, @RequestBody ChiTietRequest req) {
        return service.update(stt, req);
    }

    @DeleteMapping("/{stt}")
    public void delete(@PathVariable Integer stt) {
        service.delete(stt);
    }
}
