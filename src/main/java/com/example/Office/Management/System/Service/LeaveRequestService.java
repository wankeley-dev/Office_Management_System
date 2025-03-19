package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.LeaveRequest;
import com.example.Office.Management.System.Repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getLeaveRequestsByUser(Long userId) {
        return leaveRequestRepository.findByUserId(userId);
    }

    @Transactional
    public LeaveRequest approveLeaveRequest(Long requestId) {
        Optional<LeaveRequest> optionalRequest = leaveRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            LeaveRequest leaveRequest = optionalRequest.get();
            leaveRequest.setStatus(LeaveRequest.Status.APPROVED);
            return leaveRequestRepository.save(leaveRequest);
        } else {
            throw new RuntimeException("Leave request not found with ID: " + requestId);
        }
    }

    @Transactional
    public LeaveRequest rejectLeaveRequest(Long requestId) {
        Optional<LeaveRequest> optionalRequest = leaveRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            LeaveRequest leaveRequest = optionalRequest.get();
            leaveRequest.setStatus(LeaveRequest.Status.REJECTED);
            return leaveRequestRepository.save(leaveRequest);
        } else {
            throw new RuntimeException("Leave request not found with ID: " + requestId);
        }
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }
}
