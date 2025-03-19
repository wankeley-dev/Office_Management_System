package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.Attendance;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Service.AttendanceService;
import com.example.Office.Management.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserService userService;

    @GetMapping("/mark")
    public String showAttendanceForm(Model model) {
        model.addAttribute("attendance", new Attendance());
        return "/attendance/attendance-form";
    }

    @PostMapping("/mark")
    public String markAttendance(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(today);
        attendance.setCheckInTime(now);
        attendance.setStatus(Attendance.AttendanceStatus.PRESENT);

        attendanceService.markAttendance(attendance);
        return "redirect:/attendance/list";
    }

    @GetMapping("/list")
    public String listAttendance(Model model) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByDate(LocalDate.now());
        model.addAttribute("attendanceList", attendanceList);
        return "/attendance/attendance-list";
    }
}
