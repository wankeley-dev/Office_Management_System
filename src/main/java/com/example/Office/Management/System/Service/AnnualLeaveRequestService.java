package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.AnnualLeaveRequest;
import com.example.Office.Management.System.Repository.AnnualLeaveRequestRepository;
import com.example.Office.Management.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AnnualLeaveRequestService {

    @Autowired
    private AnnualLeaveRequestRepository annualLeaveRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // Submit a new annual leave request
    public AnnualLeaveRequest submitAnnualLeaveRequest(AnnualLeaveRequest request) {
        return annualLeaveRequestRepository.save(request);
    }

    // Get all annual leave requests for a specific user
    public List<AnnualLeaveRequest> getAnnualLeaveRequestsByUser(Long userId) {
        return annualLeaveRequestRepository.findByUserId(userId);
    }

    // Get all annual leave requests by employee name (case-insensitive)
    public List<AnnualLeaveRequest> getAnnualLeaveRequestsByEmployeeName(String employeeName) {
        if (employeeName == null || employeeName.trim().isEmpty()) {
            return annualLeaveRequestRepository.findAll();
        }
        return annualLeaveRequestRepository.findByEmployeeNameContainingIgnoreCase(employeeName);
    }

    // Get all annual leave requests
    public List<AnnualLeaveRequest> getAllAnnualLeaveRequests() {
        return annualLeaveRequestRepository.findAll();
    }

    // Approve an annual leave request
    @Transactional
    public AnnualLeaveRequest approveAnnualLeaveRequest(Long requestId) {
        Optional<AnnualLeaveRequest> optionalRequest = annualLeaveRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            AnnualLeaveRequest leaveRequest = optionalRequest.get();
            leaveRequest.setStatus(AnnualLeaveRequest.Status.APPROVED);
            return annualLeaveRequestRepository.save(leaveRequest);
        } else {
            throw new RuntimeException("Leave request not found with ID: " + requestId);
        }
    }

    // Reject an annual leave request
    public AnnualLeaveRequest rejectAnnualLeaveRequest(Long requestId) {
        Optional<AnnualLeaveRequest> optionalRequest = annualLeaveRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            AnnualLeaveRequest leaveRequest = optionalRequest.get();
            leaveRequest.setStatus(AnnualLeaveRequest.Status.REJECTED);
            return annualLeaveRequestRepository.save(leaveRequest);
        } else {
            throw new RuntimeException("Leave request not found with ID: " + requestId);
        }
    }
}