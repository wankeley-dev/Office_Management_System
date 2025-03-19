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
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    public enum LeaveType {
        ANNUAL_LEAVE, SICK_LEAVE, CASUAL_LEAVE, MATERNITY_LEAVE
    }

    private String employeeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING; // Default value

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private LocalDate createdAt = LocalDate.now(); // Auto-set current date
}
