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
public class VehicleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The employee making the request

    private String employeeName;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    public enum VehicleType
    {
        CAR, VAN, TRUCK, MOTORCYCLE
    }

    private String destination; // Location where the vehicle is needed

    private LocalDateTime requestedDate; // Date & time for vehicle usage

    private String reason; // Purpose of the request

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public enum Status
    {
        PENDING, APPROVED, REJECTED
    }

    private LocalDateTime createdAt=LocalDateTime.now(); // Auto-generated timestamp
}
