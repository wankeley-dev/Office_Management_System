package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.TransportAllowance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportAllowanceRepository extends JpaRepository<TransportAllowance, Long> {
    List<TransportAllowance> findByUserId(Long userId);
}
