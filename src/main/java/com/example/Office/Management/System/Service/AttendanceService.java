package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.Attendance;
import com.example.Office.Management.System.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }

    public List<Attendance> getUserAttendance(Long userId, LocalDate date) {
        return attendanceRepository.findByUserIdAndDate(userId, date);
    }
}
