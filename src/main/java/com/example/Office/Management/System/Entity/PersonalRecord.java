package com.example.Office.Management.System.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The employee whose record is being maintained

    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private String position;
    private String department;
    private LocalDate dateOfEmployment;

    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelation;
}
