package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.ServiceRequest;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Repository.ServiceRequestRepository;
import com.example.Office.Management.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    // Submit a new service request
    public ServiceRequest submitServiceRequest(ServiceRequest request) {

        User hrAdmin = getHRAdmin();
        if (hrAdmin != null) {
            notificationService.createNotification("New Service request submitted by " + request.getUser().getFullName(), hrAdmin);
        }

        return serviceRequestRepository.save(request);
    }

    private User getHRAdmin() {
        return userRepository.findByRole(User.Role.ADMIN); // Assuming Role is an ENUM

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
            notificationService.createNotification("Your Service request has been approved.", request.getUser());

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
            notificationService.createNotification("Your Vehicle request has been approved.", request.getUser());
            return serviceRequestRepository.save(request);
        } else {
            throw new RuntimeException("Service request not found with ID: " + requestId);
        }
    }
}
