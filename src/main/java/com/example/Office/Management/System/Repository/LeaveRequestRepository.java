package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Get all leave requests for a specific user
    List<LeaveRequest> findByUserId(Long userId);

    // Get all leave requests by status (e.g., pending, approved, rejected)
    List<LeaveRequest> findByStatus(LeaveRequest.Status status);

    // Get all leave requests for a user with a specific status
    List<LeaveRequest> findByUserIdAndStatus(Long userId, LeaveRequest.Status status);

    // Count leave requests by status (useful for analytics or dashboard)
    long countByStatus(LeaveRequest.Status status);
}

