package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.StaffRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRecordRepository extends JpaRepository<StaffRecord, Long> {
}
