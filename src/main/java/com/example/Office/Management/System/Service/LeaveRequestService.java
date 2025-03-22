package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.LeaveRequest;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Repository.LeaveRequestRepository;
import com.example.Office.Management.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository; // Added this to fetch HR/Admin

    public LeaveRequest submitLeaveRequest(LeaveRequest leaveRequest) {
        LeaveRequest savedRequest = leaveRequestRepository.save(leaveRequest);

        // Get HR/Admin and notify them
        User hrAdmin = getHRAdmin();
        if (hrAdmin != null) {
            notificationService.createNotification("New leave request submitted by " + leaveRequest.getUser().getFullName(), hrAdmin);
        }

        return savedRequest;
    }

    @Transactional
    public LeaveRequest approveLeaveRequest(Long requestId) {
        Optional<LeaveRequest> optionalRequest = leaveRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            LeaveRequest leaveRequest = optionalRequest.get();
            leaveRequest.setStatus(LeaveRequest.Status.APPROVED);
            leaveRequestRepository.save(leaveRequest);

            // Notify the user
            notificationService.createNotification("Your leave request has been approved.", leaveRequest.getUser());

            return leaveRequest;
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
            leaveRequestRepository.save(leaveRequest);

            // Notify the user
            notificationService.createNotification("Your leave request has been rejected.", leaveRequest.getUser());

            return leaveRequest;
        } else {
            throw new RuntimeException("Leave request not found with ID: " + requestId);
        }
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public List<LeaveRequest> getLeaveRequestsByUser(Long userId) {
        return leaveRequestRepository.findByUserId(userId);
    }

    private User getHRAdmin() {
        return userRepository.findByRole(User.Role.ADMIN); // Assuming Role is an ENUM

    }
}

