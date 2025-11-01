package com.api.erp_be.controller.master;

import com.api.erp_be.request.master.MaCongDoanRequest;
import com.api.erp_be.response.master.MaCongDoanResponse;
import com.api.erp_be.service.master.MaCongDoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ma-cong-doan")
public class MaCongDoanController {

    private final MaCongDoanService service;

    public MaCongDoanController(MaCongDoanService service) {
        this.service = service;
    }

    @GetMapping
    public List<MaCongDoanResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MaCongDoanResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public MaCongDoanResponse create(@RequestBody MaCongDoanRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public MaCongDoanResponse update(@PathVariable Integer id, @RequestBody MaCongDoanRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
