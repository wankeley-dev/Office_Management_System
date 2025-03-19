package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.TransportAllowance;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Service.TransportAllowanceService;
import com.example.Office.Management.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transport-allowance")
public class TransportAllowanceController {

    @Autowired
    private TransportAllowanceService transportAllowanceService;

    @Autowired
    private UserService userService;

    @GetMapping("/form")
    public String showTransportAllowanceForm(Model model) {
        model.addAttribute("transportAllowance", new TransportAllowance());
        return "/transportAllowance/transport-allowance-form";
    }

    @PostMapping("/submit")
    public String submitTransportAllowance(@ModelAttribute TransportAllowance transportAllowance, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        transportAllowance.setUser(user);
        transportAllowance.setRequestDate(LocalDate.now());
        transportAllowance.setApprovalStatus(TransportAllowance.ApprovalStatus.PENDING);
        transportAllowanceService.saveTransportAllowance(transportAllowance);
        return "redirect:/transport-allowance/AdminList";
    }

    @GetMapping("/list")
    public String listTransportAllowances(Model model) {
        List<TransportAllowance> transportAllowances = transportAllowanceService.getAllTransportAllowances();
        model.addAttribute("transportAllowances", transportAllowances);
        return "/transportAllowance/transport-allowance-list";
    }

    @GetMapping("/AdminList")
    public String AdminListTransportAllowances(Model model) {
        List<TransportAllowance> transportAllowances = transportAllowanceService.getAllTransportAllowances();
        model.addAttribute("transportAllowances", transportAllowances);
        return "/transportAllowance/Admin-transport-allowance-list";
    }

    @PostMapping("/approve/{id}")
    public String approveTransportAllowance(@PathVariable Long id, @RequestParam String remarks) {
        transportAllowanceService.updateApprovalStatus(id, "APPROVED", remarks);
        return "redirect:/transport-allowance/list";
    }

    @PostMapping("/reject/{id}")
    public String rejectTransportAllowance(@PathVariable Long id, @RequestParam String remarks) {
        transportAllowanceService.updateApprovalStatus(id, "REJECTED", remarks);
        return "redirect:/transport-allowance/list";
    }
}
