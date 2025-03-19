package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.SalaryAdvance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryAdvanceRepository extends JpaRepository<SalaryAdvance, Long> {
    List<SalaryAdvance> findByUserId(Long userId);
}
