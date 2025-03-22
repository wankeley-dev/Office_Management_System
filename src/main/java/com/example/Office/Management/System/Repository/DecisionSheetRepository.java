package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.DecisionSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecisionSheetRepository extends JpaRepository<DecisionSheet, Long> {
}
