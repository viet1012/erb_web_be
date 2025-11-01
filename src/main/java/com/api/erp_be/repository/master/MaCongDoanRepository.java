package com.api.erp_be.repository.master;



import com.api.erp_be.model.MaCongDoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaCongDoanRepository extends JpaRepository<MaCongDoan, Integer> {

    boolean existsByMaCongDoan(String maCongDoan);
}
