package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.MedicalReport;
import com.example.Office.Management.System.Repository.MedicalReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalReportService {

    @Autowired
    private MedicalReportRepository medicalReportRepository;

    public MedicalReport addMedicalReport(MedicalReport report) {
        return medicalReportRepository.save(report);
    }

    public List<MedicalReport> getAllMedicalReports() {
        return medicalReportRepository.findAll();
    }

    public List<MedicalReport> getMedicalReportsByStaffId(Long staffId) {
        return medicalReportRepository.findByStaffId(staffId);
    }

    public Optional<MedicalReport> getMedicalReportById(Long id) {
        return medicalReportRepository.findById(id);
    }

    public MedicalReport updateMedicalReport(Long id, MedicalReport updatedReport) {
        return medicalReportRepository.findById(id).map(report -> {
            report.setReportDate(updatedReport.getReportDate());
            report.setDiagnosis(updatedReport.getDiagnosis());
            report.setPrescribedTreatment(updatedReport.getPrescribedTreatment());
            report.setDoctorNotes(updatedReport.getDoctorNotes());
            report.setStatus(updatedReport.getStatus());
            return medicalReportRepository.save(report);
        }).orElseThrow(() -> new RuntimeException("Medical report not found"));
    }

    public void deleteMedicalReport(Long id) {
        medicalReportRepository.deleteById(id);
    }
}
