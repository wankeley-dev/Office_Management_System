package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.AnnualLeaveRequest;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Service.AnnualLeaveRequestService;
import com.example.Office.Management.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/annual-leave-requests")
public class AnnualLeaveRequestController {

    @Autowired
    private AnnualLeaveRequestService annualLeaveRequestService;

    @Autowired
    private UserService userService;

    @GetMapping("/submit")
    public String showLeaveRequestForm(Model model) {
        model.addAttribute("leaveRequest", new AnnualLeaveRequest());
        return "/leaveRequest/annualRequestForm";
    }

    @PostMapping("/submit")
    public String submitLeaveRequest(@ModelAttribute AnnualLeaveRequest leaveRequest,
                                     Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        leaveRequest.setUser(user);
        annualLeaveRequestService.submitAnnualLeaveRequest(leaveRequest);

        return "redirect:/annual-leave-requests/list";
    }

    @GetMapping("/user/{userId}")
    public String getAnnualLeaveRequestsByUser(@PathVariable Long userId, Model model) {
        List<AnnualLeaveRequest> requests = annualLeaveRequestService.getAnnualLeaveRequestsByUser(userId);
        model.addAttribute("requests", requests);
        return "/leaveRequest/deanAnnualRequests";
    }

    @PostMapping("/{id}/approve")
    public String approveAnnualLeaveRequest(@PathVariable Long id) {
        annualLeaveRequestService.approveAnnualLeaveRequest(id);
        return "redirect:/annual-leave-requests/list";
    }

    @PostMapping("/{id}/reject")
    public String rejectAnnualLeaveRequest(@PathVariable Long id) {
        annualLeaveRequestService.rejectAnnualLeaveRequest(id);
        return "redirect:/annual-leave-requests/list";
    }

    @GetMapping("/list")
    public String listAllAnnualLeaveRequests(Model model) {
        List<AnnualLeaveRequest> requests = annualLeaveRequestService.getAllAnnualLeaveRequests();
        model.addAttribute("requests", requests);
        return "/leaveRequest/deanAnnualRequests";
    }

    @GetMapping("/AdminList")
    public String AdminListAllAnnualLeaveRequests(
            @RequestParam(name = "employeeName", required = false) String employeeName,
            Model model) {
        List<AnnualLeaveRequest> leaveRequests;
        if (employeeName != null && !employeeName.trim().isEmpty()) {
            // Search by employee name
            leaveRequests = annualLeaveRequestService.getAnnualLeaveRequestsByEmployeeName(employeeName);
        } else {
            // Get all leave requests if no search parameter is provided
            leaveRequests = annualLeaveRequestService.getAllAnnualLeaveRequests();
        }
        model.addAttribute("leaveRequests", leaveRequests);
        model.addAttribute("employeeName", employeeName);
        return "/leaveRequest/annualRequestsList";
    }
}