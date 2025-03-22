package com.example.Office.Management.System.Service;


import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Entity.VehicleRequest;
import com.example.Office.Management.System.Repository.UserRepository;
import com.example.Office.Management.System.Repository.VehicleRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleRequestService {

    @Autowired
    private VehicleRequestRepository vehicleRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;


    // Submit a new vehicle request
    public VehicleRequest submitVehicleRequest(VehicleRequest request) {

        // Get HR/Admin and notify them
        User hrAdmin = getHRAdmin();
        if (hrAdmin != null) {
            notificationService.createNotification("New Vehicle request submitted by " + request.getUser().getFullName(), hrAdmin);
        }

        return vehicleRequestRepository.save(request);
    }

    private User getHRAdmin() {
        return userRepository.findByRole(User.Role.ADMIN); // Assuming Role is an ENUM

    }

    // Get all vehicle requests for a specific user
    public List<VehicleRequest> getVehicleRequestsByUser(Long userId) {
        return vehicleRequestRepository.findByUserId(userId);
    }

    // Get all vehicle requests
    public List<VehicleRequest> getAllVehicleRequests() {
        return vehicleRequestRepository.findAll();
    }

    // Approve a vehicle request
    @Transactional
    public VehicleRequest approveVehicleRequest(Long requestId) {
        Optional<VehicleRequest> optionalRequest = vehicleRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            VehicleRequest vehicleRequest = optionalRequest.get();
            vehicleRequest.setStatus(VehicleRequest.Status.APPROVED);
            // Notify the user
            notificationService.createNotification("Your Vehicle request has been approved.", vehicleRequest.getUser());
            return vehicleRequestRepository.save(vehicleRequest);

        } else {
            throw new RuntimeException("Vehicle request not found with ID: " + requestId);
        }
    }

    // Reject a vehicle request
    public VehicleRequest rejectVehicleRequest(Long requestId) {
        Optional<VehicleRequest> optionalRequest = vehicleRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            VehicleRequest vehicleRequest = optionalRequest.get();
            vehicleRequest.setStatus(VehicleRequest.Status.REJECTED);
            notificationService.createNotification("Your Vehicle request has been approved.", vehicleRequest.getUser());

            return vehicleRequestRepository.save(vehicleRequest);
        } else {
            throw new RuntimeException("Vehicle request not found with ID: " + requestId);
        }
    }
}