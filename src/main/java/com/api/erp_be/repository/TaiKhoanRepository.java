package com.api.erp_be.repository;
import com.api.erp_be.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    TaiKhoan findByTaiKhoan(String taiKhoan);
}
