package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.AnnualLeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnnualLeaveRequestRepository extends JpaRepository<AnnualLeaveRequest, Long> {
    List<AnnualLeaveRequest> findByUserId(Long userId);
}

