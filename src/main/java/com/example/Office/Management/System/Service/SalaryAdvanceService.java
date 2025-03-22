package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.SalaryAdvance;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Repository.SalaryAdvanceRepository;
import com.example.Office.Management.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryAdvanceService {

    @Autowired
    private SalaryAdvanceRepository salaryAdvanceRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    public SalaryAdvance saveSalaryAdvance(SalaryAdvance salaryAdvance) {
        // Get HR/Admin and notify them
        User hrAdmin = getHRAdmin();
        if (hrAdmin != null) {
            notificationService.createNotification("New Salary Advance request submitted by " + salaryAdvance.getUser().getFullName(), hrAdmin);
        }

        return salaryAdvanceRepository.save(salaryAdvance);
    }

    private User getHRAdmin() {
        return userRepository.findByRole(User.Role.ADMIN); // Assuming Role is an ENUM

    }

    public List<SalaryAdvance> getSalaryAdvancesByUser(Long userId) {
        return salaryAdvanceRepository.findByUserId(userId);
    }

    public List<SalaryAdvance> getAllSalaryAdvances() {
        return salaryAdvanceRepository.findAll();
    }

    public Optional<SalaryAdvance> getSalaryAdvanceById(Long id) {
        return salaryAdvanceRepository.findById(id);
    }

    public SalaryAdvance updateApprovalStatus(Long id, String status, String remarks) {
        Optional<SalaryAdvance> optionalSalaryAdvance = salaryAdvanceRepository.findById(id);
        if (optionalSalaryAdvance.isPresent()) {
            SalaryAdvance salaryAdvance = optionalSalaryAdvance.get();
            salaryAdvance.setApprovalStatus(SalaryAdvance.ApprovalStatus.valueOf(status));
            salaryAdvance.setRemarks(remarks);
            // Notify the user
            notificationService.createNotification("You New Message On Your Salary Advance", salaryAdvance.getUser());
            return salaryAdvanceRepository.save(salaryAdvance);
        }
        return null;
    }
}
