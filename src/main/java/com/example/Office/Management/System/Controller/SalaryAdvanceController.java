package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.SalaryAdvance;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Service.SalaryAdvanceService;
import com.example.Office.Management.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/salary-advance")
public class SalaryAdvanceController {

    @Autowired
    private SalaryAdvanceService salaryAdvanceService;

    @Autowired
    private UserService userService;

    @GetMapping("/form")
    public String showSalaryAdvanceForm(Model model) {
        model.addAttribute("salaryAdvance", new SalaryAdvance());
        return "/salaryAdvance/salary-advance-form";
    }

    @PostMapping("/submit")
    public String submitSalaryAdvance(@ModelAttribute SalaryAdvance salaryAdvance, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        salaryAdvance.setUser(user);
        salaryAdvance.setRequestDate(LocalDate.now());
        salaryAdvance.setApprovalStatus(SalaryAdvance.ApprovalStatus.PENDING);
        salaryAdvanceService.saveSalaryAdvance(salaryAdvance);
        return "redirect:/salary-advance/list";
    }

    @GetMapping("/list")
    public String listSalaryAdvances(Model model) {
        List<SalaryAdvance> salaryAdvances = salaryAdvanceService.getAllSalaryAdvances();
        model.addAttribute("salaryAdvances", salaryAdvances);
        return "/salaryAdvance/salary-advance-list";
    }

    @GetMapping("/AdminList")
    public String AdminListSalaryAdvances(Model model) {
        List<SalaryAdvance> salaryAdvances = salaryAdvanceService.getAllSalaryAdvances();
        model.addAttribute("salaryAdvances", salaryAdvances);
        return "/salaryAdvance/Admin-salary-advance-list";
    }

    @PostMapping("/approve/{id}")
    public String approveSalaryAdvance(@PathVariable Long id, @RequestParam String remarks) {
        salaryAdvanceService.updateApprovalStatus(id, "APPROVED", remarks);
        return "redirect:/salary-advance/AdminList";
    }

    @PostMapping("/reject/{id}")
    public String rejectSalaryAdvance(@PathVariable Long id, @RequestParam String remarks) {
        salaryAdvanceService.updateApprovalStatus(id, "REJECTED", remarks);
        return "redirect:/salary-advance/AdminList";
    }
}
