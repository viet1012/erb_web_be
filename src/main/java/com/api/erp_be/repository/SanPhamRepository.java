package com.api.erp_be.repository;

import com.api.erp_be.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    boolean existsByMaSanPham(String maSanPham);
}

