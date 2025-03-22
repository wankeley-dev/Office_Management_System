package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.AccommodationRequest;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Repository.AccommodationRequestRepository;
import com.example.Office.Management.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationRequestService {

    private final AccommodationRequestRepository accommodationRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccommodationRequestService(AccommodationRequestRepository accommodationRequestRepository,
                                       UserRepository userRepository) {
        this.accommodationRequestRepository = accommodationRequestRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    private NotificationService notificationService;


    // Submit a new accommodation request
    public AccommodationRequest submitAccommodationRequest(AccommodationRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus(AccommodationRequest.Status.PENDING);
        // Get HR/Admin and notify them
        User hrAdmin = getHRAdmin();
        if (hrAdmin != null) {
            notificationService.createNotification("New Vehicle request submitted by " + request.getUser().getFullName(), hrAdmin);
        }
        return accommodationRequestRepository.save(request);
    }

    private User getHRAdmin() {
        return userRepository.findByRole(User.Role.ADMIN); // Assuming Role is an ENUM

    }

    // Get accommodation requests by status
    public List<AccommodationRequest> getAccommodationRequestsByStatus(AccommodationRequest.Status status) {
        if (status == null) {
            return accommodationRequestRepository.findAll();
        }
        return accommodationRequestRepository.findByStatus(status);
    }

    // Get all accommodation requests for a specific user
    public List<AccommodationRequest> getAccommodationRequestsByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return accommodationRequestRepository.findByUserId(userId);
    }

    // Approve an accommodation request
    public void approveAccommodationRequest(Long requestId) {
        updateRequestStatus(requestId, AccommodationRequest.Status.APPROVED);

    }

    // Reject an accommodation request
    public void rejectAccommodationRequest(Long requestId) {
        updateRequestStatus(requestId, AccommodationRequest.Status.REJECTED);
    }

    // Get all accommodation requests
    public List<AccommodationRequest> getAllAccommodationRequests() {
        return accommodationRequestRepository.findAll();
    }

    // Private helper method to update request status
    private void updateRequestStatus(Long requestId, AccommodationRequest.Status status) {
        if (requestId == null) {
            throw new IllegalArgumentException("Request ID cannot be null");
        }

        Optional<AccommodationRequest> optionalRequest = accommodationRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            AccommodationRequest request = optionalRequest.get();
            request.setStatus(status);
            request.setCreatedAt(LocalDateTime.now());
            accommodationRequestRepository.save(request);
        } else {
            throw new RuntimeException("Accommodation request not found with ID: " + requestId);
        }
    }
}