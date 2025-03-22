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
public class DecisionSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Column(length = 500)
    private String details;

    private LocalDate decisionDate;

    @Enumerated(EnumType.STRING)
    private DecisionStatus status;

    @Column(length = 200)
    private String decisionOutcome;

    public enum DecisionStatus {
        PENDING, APPROVED, REJECTED, IMPLEMENTED
    }
}
