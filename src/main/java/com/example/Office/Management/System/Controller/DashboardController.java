package com.example.Office.Management.System.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        return "/Dashboard/dashboard";  // Refers to 'dashboard.html' in Thymeleaf templates
    }

    @GetMapping("/AdminDashboard")
    public String showAdminDashboard(Model model) {
        return "/Dashboard/adminDashboard";  // Refers to 'dashboard.html' in Thymeleaf templates
    }
}
