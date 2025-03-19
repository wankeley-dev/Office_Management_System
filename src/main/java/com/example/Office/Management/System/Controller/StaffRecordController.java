package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.StaffRecord;
import com.example.Office.Management.System.Service.StaffRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/staff-records")
public class StaffRecordController {

    @Autowired
    private StaffRecordService staffRecordService;

    // Display form to add a new staff record
    @GetMapping("/add")
    public String showAddStaffForm(Model model) {
        model.addAttribute("staffRecord", new StaffRecord());
        return "/staffRecords/staff-record-form";
    }

    // Handle form submission for adding staff record
    @PostMapping("/add")
    public String addStaffRecord(@ModelAttribute StaffRecord staffRecord) {
        staffRecordService.addStaffRecord(staffRecord);
        return "redirect:/staff-records/list";
    }

    // Display all staff records
    @GetMapping("/list")
    public String listAllStaffRecords(Model model) {
        List<StaffRecord> staffRecords = staffRecordService.getAllStaffRecords();
        model.addAttribute("staffRecords", staffRecords);
        return "/staffRecords/staff-record-list";
    }

    // Display form to edit staff record
    @GetMapping("/edit/{id}")
    public String editStaffRecord(@PathVariable Long id, Model model) {
        Optional<StaffRecord> staffRecord = staffRecordService.getStaffRecordById(id);
        if (staffRecord.isPresent()) {
            model.addAttribute("staffRecord", staffRecord.get());
            return "/staffRecords/staff-record-form";
        }
        return "redirect:/staff-records/list";
    }

    // Handle staff record update
    @PostMapping("/update/{id}")
    public String updateStaffRecord(@PathVariable Long id, @ModelAttribute StaffRecord staffRecord) {
        staffRecordService.updateStaffRecord(id, staffRecord);
        return "redirect:/staff-records/list";
    }

    // Delete a staff record
    @GetMapping("/delete/{id}")
    public String deleteStaffRecord(@PathVariable Long id) {
        staffRecordService.deleteStaffRecord(id);
        return "redirect:/staff-records/list";
    }
}
