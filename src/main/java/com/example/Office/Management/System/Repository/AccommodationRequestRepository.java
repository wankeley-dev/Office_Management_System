package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.AccommodationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRequestRepository extends JpaRepository<AccommodationRequest, Long> {

    // Find all accommodation requests by user ID
    List<AccommodationRequest> findByUserId(Long userId);

    // Find all accommodation requests by status
    List<AccommodationRequest> findByStatus(AccommodationRequest.Status status);
}
