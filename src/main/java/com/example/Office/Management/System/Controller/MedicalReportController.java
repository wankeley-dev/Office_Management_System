package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.MedicalReport;
import com.example.Office.Management.System.Service.MedicalReportService;
import com.example.Office.Management.System.Service.StaffRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/medical-reports")
public class MedicalReportController {

    @Autowired
    private MedicalReportService medicalReportService;

    @Autowired
    private StaffRecordService staffRecordService;

    // Display form to add a new medical report
    @GetMapping("/add")
    public String showAddMedicalReportForm(Model model) {
        model.addAttribute("medicalReport", new MedicalReport());
        model.addAttribute("staffRecords", staffRecordService.getAllStaffRecords());
        return "/medicalRecord/medical-report-form";
    }

    // Handle form submission for adding medical report
    @PostMapping("/add")
    public String addMedicalReport(@ModelAttribute MedicalReport medicalReport) {
        medicalReportService.addMedicalReport(medicalReport);
        return "redirect:/medical-reports/list";
    }

    // Display all medical reports
    @GetMapping("/list")
    public String listAllMedicalReports(Model model) {
        List<MedicalReport> medicalReports = medicalReportService.getAllMedicalReports();
        model.addAttribute("medicalReports", medicalReports);
        return "/medicalRecord/medical-report-list";
    }

    // Display form to edit medical report
    @GetMapping("/edit/{id}")
    public String editMedicalReport(@PathVariable Long id, Model model) {
        Optional<MedicalReport> medicalReport = medicalReportService.getMedicalReportById(id);
        if (medicalReport.isPresent()) {
            model.addAttribute("medicalReport", medicalReport.get());
            model.addAttribute("staffRecords", staffRecordService.getAllStaffRecords());
            return "/medicalRecord/medical-report-form";
        }
        return "redirect:/medical-reports/list";
    }

    // Handle medical report update
    @PostMapping("/update/{id}")
    public String updateMedicalReport(@PathVariable Long id, @ModelAttribute MedicalReport medicalReport) {
        medicalReportService.updateMedicalReport(id, medicalReport);
        return "redirect:/medical-reports/list";
    }

    // Delete a medical report
    @GetMapping("/delete/{id}")
    public String deleteMedicalReport(@PathVariable Long id) {
        medicalReportService.deleteMedicalReport(id);
        return "redirect:/medical-reports/list";
    }
}
