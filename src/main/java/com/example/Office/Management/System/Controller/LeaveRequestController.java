package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.LeaveRequest;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Service.LeaveRequestService;
import com.example.Office.Management.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/leave-requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private UserService userService;

    // Show the leave request form
    @GetMapping("/form")
    public String showLeaveRequestForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "leaveRequestForm";  // Refers to 'leaveRequestForm.html' in Thymeleaf templates
    }

    // Submit a leave request (form submission)
    @PostMapping("/submit")
    public String submitLeaveRequest(@ModelAttribute LeaveRequest leaveRequest,
                                     @RequestParam("leaveType") String leaveType,
                                     Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        // Convert String to Enum
        leaveRequest.setLeaveType(LeaveRequest.LeaveType.valueOf(leaveType));
        leaveRequest.setUser(user);
        leaveRequestService.submitLeaveRequest(leaveRequest);

        return "redirect:/leave-requests/list";
    }

    // Display leave requests for a specific user
    @GetMapping("/user/{userId}")
    public String getUserLeaveRequests(@PathVariable Long userId, Model model) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByUser(userId);
        model.addAttribute("leaveRequests", leaveRequests);
        return "userLeaveRequests";  // Refers to 'userLeaveRequests.html' in Thymeleaf templates
    }

    // Approve a leave request
    @PostMapping("/{id}/approve")
    public String approveLeaveRequest(@PathVariable Long id) {
        leaveRequestService.approveLeaveRequest(id);
        return "redirect:/leave-requests/AdminList";  // Redirect to updated leave request list
    }

    // Reject a leave request
    @PostMapping("/{id}/reject")
    public String rejectLeaveRequest(@PathVariable Long id) {
        leaveRequestService.rejectLeaveRequest(id);
        return "redirect:/leave-requests/AdminList";
    }


    @GetMapping("/list")
    public String listAllLeaveRequests(Model model) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getAllLeaveRequests();
        model.addAttribute("leaveRequests", leaveRequests);
        return "userLeaveRequests";  // Refers to 'leaveRequestList.html' in Thymeleaf templates
    }

    // Display all leave requests (for admin)
    @GetMapping("/AdminList")
    public String AdminListAllLeaveRequests(Model model) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getAllLeaveRequests();
        model.addAttribute("leaveRequests", leaveRequests);
        return "leaveRequestList";  // Refers to 'leaveRequestList.html' in Thymeleaf templates
    }
}
