package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByUserId(Long userId);
}
