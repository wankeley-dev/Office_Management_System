package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.TransportAllowance;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Repository.TransportAllowanceRepository;
import com.example.Office.Management.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportAllowanceService {

    @Autowired
    private TransportAllowanceRepository transportAllowanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    public TransportAllowance saveTransportAllowance(TransportAllowance transportAllowance) {

        // Get HR/Admin and notify them
        User hrAdmin = getHRAdmin();
        if (hrAdmin != null) {
            notificationService.createNotification("New leave request submitted by " + transportAllowance.getUser().getFullName(), hrAdmin);
        }
        return transportAllowanceRepository.save(transportAllowance);
    }

    private User getHRAdmin() {
        return userRepository.findByRole(User.Role.ADMIN); // Assuming Role is an ENUM

    }

    public List<TransportAllowance> getTransportAllowancesByUser(Long userId) {
        return transportAllowanceRepository.findByUserId(userId);
    }

    public List<TransportAllowance> getAllTransportAllowances() {
        return transportAllowanceRepository.findAll();
    }

    public Optional<TransportAllowance> getTransportAllowanceById(Long id) {
        return transportAllowanceRepository.findById(id);
    }

    public TransportAllowance updateApprovalStatus(Long id, String status, String remarks) {
        Optional<TransportAllowance> optionalTransportAllowance = transportAllowanceRepository.findById(id);
        if (optionalTransportAllowance.isPresent()) {
            TransportAllowance transportAllowance = optionalTransportAllowance.get();
            transportAllowance.setApprovalStatus(TransportAllowance.ApprovalStatus.valueOf(status));
            transportAllowance.setRemarks(remarks);
            // Notify the user
            notificationService.createNotification("Your Transport request has been approved.", transportAllowance.getUser());
            return transportAllowanceRepository.save(transportAllowance);
        }
        return null;
    }
}
