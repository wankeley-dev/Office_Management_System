package com.example.Office.Management.System.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Employee marking attendance

    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    public enum AttendanceStatus {
        PRESENT, ABSENT, LATE, HALF_DAY
    }
}
