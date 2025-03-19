package com.example.Office.Management.System.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryAdvance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Employee requesting the salary advance

    private BigDecimal amountRequested;
    private String reason;
    private LocalDate requestDate;

    @Enumerated(EnumType.STRING)
    private SalaryAdvance.ApprovalStatus approvalStatus = SalaryAdvance.ApprovalStatus.PENDING;

    public enum ApprovalStatus {
        PENDING, APPROVED, REJECTED
    }

    private String remarks;
}
