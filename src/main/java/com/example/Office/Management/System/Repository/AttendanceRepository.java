package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserIdAndDate(Long userId, LocalDate date);
    List<Attendance> findByDate(LocalDate date);
}
