package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.PersonalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalRecordRepository extends JpaRepository<PersonalRecord, Long> {
    List<PersonalRecord> findByUserId(Long userId);
}
