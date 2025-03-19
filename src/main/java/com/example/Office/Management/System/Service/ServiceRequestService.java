package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.ServiceRequest;
import com.example.Office.Management.System.Repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    // Submit a new service request
    public ServiceRequest submitServiceRequest(ServiceRequest request) {
        return serviceRequestRepository.save(request);
    }

    // Get all service requests for a specific user
    public List<ServiceRequest> getServiceRequestsByUser(Long userId) {
        return serviceRequestRepository.findByUserId(userId);
    }

    // Get all service requests
    public List<ServiceRequest> getAllServiceRequests() {
        return serviceRequestRepository.findAll();
    }

    // Approve a service request
    @Transactional
    public ServiceRequest approveServiceRequest(Long requestId) {
        Optional<ServiceRequest> optionalRequest = serviceRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            ServiceRequest request = optionalRequest.get();
            request.setStatus(ServiceRequest.RequestStatus.APPROVED);
            return serviceRequestRepository.save(request);
        } else {
            throw new RuntimeException("Service request not found with ID: " + requestId);
        }
    }

    // Reject a service request
    @Transactional
    public ServiceRequest rejectServiceRequest(Long requestId) {
        Optional<ServiceRequest> optionalRequest = serviceRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            ServiceRequest request = optionalRequest.get();
            request.setStatus(ServiceRequest.RequestStatus.REJECTED);
            return serviceRequestRepository.save(request);
        } else {
            throw new RuntimeException("Service request not found with ID: " + requestId);
        }
    }
}
