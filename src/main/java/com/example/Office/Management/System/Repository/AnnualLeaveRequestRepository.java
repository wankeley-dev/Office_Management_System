package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.AnnualLeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnnualLeaveRequestRepository extends JpaRepository<AnnualLeaveRequest, Long> {
    // Find all annual leave requests by user ID
    List<AnnualLeaveRequest> findByUserId(Long userId);

    // Find all annual leave requests by employee name (case-insensitive)
    List<AnnualLeaveRequest> findByEmployeeNameContainingIgnoreCase(String employeeName);
}