package com.api.erp_be.controller;


import com.api.erp_be.request.LoginRequest;
import com.api.erp_be.response.LoginResponse;
import com.api.erp_be.service.TaiKhoanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final TaiKhoanService taiKhoanService;

    public AuthController(TaiKhoanService taiKhoanService) {
        this.taiKhoanService = taiKhoanService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return taiKhoanService.login(request.getTaiKhoan(), request.getMatKhau());
    }
}
