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
public class StaffRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String department;
    private String position;
    private LocalDate dateOfHire;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    private Double salary;
    private String benefits;

    public enum EmploymentType {
        FULL_TIME, PART_TIME, CONTRACT
    }
}
