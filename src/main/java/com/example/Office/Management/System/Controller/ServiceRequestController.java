package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.ServiceRequest;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Service.ServiceRequestService;
import com.example.Office.Management.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/service-requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    @Autowired
    private UserService userService;

    // Display form to submit a service request
    @GetMapping("/submit")
    public String showServiceRequestForm(Model model) {
        model.addAttribute("serviceRequest", new ServiceRequest());
        return "/serviceRequest/service-request-form";
    }

    // Handle form submission for service request
    @PostMapping("/submit")
    public String submitServiceRequest(@ModelAttribute ServiceRequest serviceRequest,
                                       @RequestParam("serviceType") String serviceType,
                                       Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        serviceRequest.setServiceType(serviceType);
        serviceRequest.setUser(user);
        serviceRequestService.submitServiceRequest(serviceRequest);

        return "redirect:/service-requests/list";
    }

    // Display list of service requests for a specific user
    @GetMapping("/user/{userId}")
    public String getServiceRequestsByUser(@PathVariable Long userId, Model model) {
        List<ServiceRequest> requests = serviceRequestService.getServiceRequestsByUser(userId);
        model.addAttribute("requests", requests);
        return "/serviceRequest/user-service-request";
    }

    // Approve a service request
    @PostMapping("/{id}/approve")
    public String approveServiceRequest(@PathVariable Long id) {
        serviceRequestService.approveServiceRequest(id);
        return "redirect:/service-requests/AdminList";
    }

    // Reject a service request
    @PostMapping("/{id}/reject")
    public String rejectServiceRequest(@PathVariable Long id) {
        serviceRequestService.rejectServiceRequest(id);
        return "redirect:/service-requests/AdminList";
    }

    // Display all service requests (for user)
    @GetMapping("/list")
    public String listAllServiceRequests(Model model) {
        List<ServiceRequest> requests = serviceRequestService.getAllServiceRequests();
        model.addAttribute("requests", requests);
        return "/serviceRequest/user-service-request";
    }

    // Display all service requests (for admin)
    @GetMapping("/AdminList")
    public String AdminListAllServiceRequests(Model model) {
        List<ServiceRequest> requests = serviceRequestService.getAllServiceRequests();
        model.addAttribute("serviceRequests", requests);
        return "/serviceRequest/service-request-list";
    }
}
