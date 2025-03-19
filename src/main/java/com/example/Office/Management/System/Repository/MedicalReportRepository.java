package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {
    List<MedicalReport> findByStaffId(Long staffId);
}
