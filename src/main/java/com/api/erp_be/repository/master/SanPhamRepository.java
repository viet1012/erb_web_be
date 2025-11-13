package com.api.erp_be.repository.master;

import com.api.erp_be.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    boolean existsByMaSanPham(String maSanPham);
    List<SanPham> findByStatus(String status);

}

