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
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The employee requesting the service

    private String serviceType; // Type of service requested

    @Column(length = 500)
    private String description; // Details about the request

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING; // Request status

    private LocalDateTime requestDate = LocalDateTime.now(); // Auto-generated timestamp

    public enum RequestStatus {
        PENDING, APPROVED, REJECTED
    }
}
