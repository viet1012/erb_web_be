package com.api.erp_be.repository.master;


import com.api.erp_be.model.ChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietRepository extends JpaRepository<ChiTiet, Integer> {
    boolean existsByMaChiTiet(String maChiTiet);
}
