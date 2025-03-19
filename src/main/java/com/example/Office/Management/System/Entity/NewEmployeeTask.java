package com.example.Office.Management.System.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewEmployeeTask {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String taskName;

        private String description;

        @Enumerated(EnumType.STRING)
        private TaskStatus status;

        public enum TaskStatus {
                PENDING, IN_PROGRESS, COMPLETED
        }

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user; // Assigned employee
}
