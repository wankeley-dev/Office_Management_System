package com.example.Office.Management.System.Service;


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


    // Submit a new vehicle request
    public VehicleRequest submitVehicleRequest(VehicleRequest request) {
        return vehicleRequestRepository.save(request);
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
            return vehicleRequestRepository.save(vehicleRequest);
        } else {
            throw new RuntimeException("Leave request not found with ID: " + requestId);
        }
    }

    // Reject a vehicle request
    public VehicleRequest rejectVehicleRequest(Long requestId) {
        Optional<VehicleRequest> optionalRequest = vehicleRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            VehicleRequest vehicleRequest = optionalRequest.get();
            vehicleRequest.setStatus(VehicleRequest.Status.REJECTED);
            return vehicleRequestRepository.save(vehicleRequest);
        } else {
            throw new RuntimeException("Leave request not found with ID: " + requestId);
        }
    }
}