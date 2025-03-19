package com.example.Office.Management.System.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The Dean or Director making the request

    private String employeeName;

    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private LocalDateTime createdAt = LocalDateTime.now(); // Auto-generated timestamp
}
