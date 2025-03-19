package com.example.Office.Management.System.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MedicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffRecord staff;

    private LocalDate reportDate;
    private String diagnosis;
    private String prescribedTreatment;
    private String doctorNotes;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    public enum ReportStatus {
        PENDING, REVIEWED, APPROVED
    }
}
