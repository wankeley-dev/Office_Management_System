package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.SalaryAdvance;
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
    private UserRepository userRepository;

    public SalaryAdvance saveSalaryAdvance(SalaryAdvance salaryAdvance) {
        return salaryAdvanceRepository.save(salaryAdvance);
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
            return salaryAdvanceRepository.save(salaryAdvance);
        }
        return null;
    }
}
