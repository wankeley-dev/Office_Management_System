package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.StaffRecord;
import com.example.Office.Management.System.Repository.StaffRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffRecordService {

    @Autowired
    private StaffRecordRepository staffRecordRepository;

    public StaffRecord addStaffRecord(StaffRecord staffRecord) {
        return staffRecordRepository.save(staffRecord);
    }

    public List<StaffRecord> getAllStaffRecords() {
        return staffRecordRepository.findAll();
    }

    public Optional<StaffRecord> getStaffRecordById(Long id) {
        return staffRecordRepository.findById(id);
    }

    public StaffRecord updateStaffRecord(Long id, StaffRecord updatedStaff) {
        return staffRecordRepository.findById(id).map(staff -> {
            staff.setFullName(updatedStaff.getFullName());
            staff.setEmail(updatedStaff.getEmail());
            staff.setPhoneNumber(updatedStaff.getPhoneNumber());
            staff.setDepartment(updatedStaff.getDepartment());
            staff.setPosition(updatedStaff.getPosition());
            staff.setDateOfHire(updatedStaff.getDateOfHire());
            staff.setEmploymentType(updatedStaff.getEmploymentType());
            staff.setSalary(updatedStaff.getSalary());
            staff.setBenefits(updatedStaff.getBenefits());
            return staffRecordRepository.save(staff);
        }).orElseThrow(() -> new RuntimeException("Staff record not found"));
    }

    public void deleteStaffRecord(Long id) {
        staffRecordRepository.deleteById(id);
    }
}
