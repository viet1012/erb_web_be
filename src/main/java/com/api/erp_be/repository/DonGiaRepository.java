package com.api.erp_be.repository;


import com.api.erp_be.model.DonGia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonGiaRepository extends JpaRepository<DonGia, Integer> {
    List<DonGia> findBySanPham_MaSanPham(String maSanPham);
}
