package com.example.Office.Management.System.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Employee submitting the work order

    private String department; // Department requesting maintenance

    private String issueDescription; // Description of the issue

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status = WorkOrderStatus.PENDING;

    public enum WorkOrderStatus {
        PENDING, IN_PROGRESS, COMPLETED, REJECTED
    }

    private LocalDateTime requestedDate = LocalDateTime.now(); // Date of request
}
