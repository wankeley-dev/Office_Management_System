package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.VehicleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRequestRepository extends JpaRepository<VehicleRequest, Long> {
    List<VehicleRequest> findByUserId(Long userId);

    // Get all leave requests by status (e.g., pending, approved, rejected)
    List<VehicleRequest> findByStatus(VehicleRequest.Status status);

    // Get all leave requests for a user with a specific status
    List<VehicleRequest> findByUserIdAndStatus(Long userId, VehicleRequest.Status status);

    // Count leave requests by status (useful for analytics or dashboard)
    long countByStatus(VehicleRequest.Status status);
}

