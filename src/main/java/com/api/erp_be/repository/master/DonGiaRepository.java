package com.api.erp_be.repository.master;


import com.api.erp_be.model.DonGia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonGiaRepository extends JpaRepository<DonGia, Integer> {
    List<DonGia> findBySanPham_MaSanPham(String maSanPham);
    Optional<DonGia> findTopByMaSanPhamOrderByNgayTaoDesc(String maSanPham);
    List<DonGia> findByMaSanPhamOrderByNgayCapNhatDesc(String maSanPham);
}
