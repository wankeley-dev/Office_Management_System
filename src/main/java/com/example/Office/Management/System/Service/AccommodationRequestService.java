package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.AccommodationRequest;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Repository.AccommodationRequestRepository;
import com.example.Office.Management.System.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationRequestService {

    private final AccommodationRequestRepository accommodationRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccommodationRequestService(AccommodationRequestRepository accommodationRequestRepository, UserRepository userRepository) {
        this.accommodationRequestRepository = accommodationRequestRepository;
        this.userRepository = userRepository;
    }

    // Submit a new accommodation request
    public AccommodationRequest submitAccommodationRequest( AccommodationRequest request) {
        return accommodationRequestRepository.save(request);
    }



    // Get all accommodation requests for a specific user
    public List<AccommodationRequest> getAccommodationRequestsByUser(Long userId) {
        return accommodationRequestRepository.findByUserId(userId);
    }

    // Approve an accommodation request
    public void approveAccommodationRequest(Long requestId) {
        Optional<AccommodationRequest> optionalRequest = accommodationRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            AccommodationRequest request = optionalRequest.get();
            request.setStatus(AccommodationRequest.Status.APPROVED);
            accommodationRequestRepository.save(request);
        } else {
            throw new RuntimeException("Accommodation request not found");
        }
    }

    // Reject an accommodation request
    public void rejectAccommodationRequest(Long requestId) {
        Optional<AccommodationRequest> optionalRequest = accommodationRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            AccommodationRequest request = optionalRequest.get();
            request.setStatus(AccommodationRequest.Status.REJECTED);
            accommodationRequestRepository.save(request);
        } else {
            throw new RuntimeException("Accommodation request not found");
        }
    }

    public List<AccommodationRequest> getAllAccommodationRequests() {
        return accommodationRequestRepository.findAll();
    }


}
