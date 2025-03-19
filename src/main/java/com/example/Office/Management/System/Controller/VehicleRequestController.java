package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Entity.VehicleRequest;
import com.example.Office.Management.System.Service.UserService;
import com.example.Office.Management.System.Service.VehicleRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/vehicle-requests")
public class VehicleRequestController {

    @Autowired
    private VehicleRequestService vehicleRequestService;

    @Autowired
    private UserService userService;


    // Display form to submit a vehicle request
    @GetMapping("/submit")
    public String showVehicleRequestForm(Model model) {
        model.addAttribute("vehicleRequest", new VehicleRequest());
        return "/vehicleRequest/vehicle-request-form";
    }

    // Handle form submission for vehicle request
    @PostMapping("/submit")
    public String submitVehicleRequest(@ModelAttribute VehicleRequest vehicleRequest,
                                       @RequestParam("vehicleType") String vehicleType,
                                       Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        vehicleRequest.setVehicleType(VehicleRequest.VehicleType.valueOf(vehicleType));
        vehicleRequest.setUser(user);
        vehicleRequest.setUser(user);
        vehicleRequestService.submitVehicleRequest(vehicleRequest);

        return "redirect:/vehicle-requests/list";
    }

    // Display list of vehicle requests for a specific user
    @GetMapping("/user/{userId}")
    public String getVehicleRequestsByUser(@PathVariable Long userId, Model model) {
        List<VehicleRequest> requests = vehicleRequestService.getVehicleRequestsByUser(userId);
        model.addAttribute("requests", requests);
        return "/vehicleRequest/user-vehicle-request";
    }

    // Approve a vehicle request
    @PostMapping("/{id}/approve")
    public String approveVehicleRequest(@PathVariable Long id) {
        vehicleRequestService.approveVehicleRequest(id);
        return "redirect:/vehicle-requests/AdminList";
    }

    // Reject a vehicle request
    @PostMapping("/{id}/reject")
    public String rejectVehicleRequest(@PathVariable Long id) {
        vehicleRequestService.rejectVehicleRequest(id);
        return "redirect:/vehicle-requests/AdminList";
    }

    // Display all vehicle requests (for user)
    @GetMapping("/list")
    public String listAllVehicleRequests(Model model) {
        List<VehicleRequest> requests = vehicleRequestService.getAllVehicleRequests();
        model.addAttribute("requests", requests);
        return "/vehicleRequest/user-vehicle-request";
    }

    // Display all leave requests (for admin)
    @GetMapping("/AdminList")
    public String AdminListAllVehicleRequests(Model model) {
        List<VehicleRequest> vehicleRequests = vehicleRequestService.getAllVehicleRequests();
        model.addAttribute("leaveRequests", vehicleRequests);
        return "/vehicleRequest/vehicle-request-list";  // Refers to 'vehicle-request-list' in Thymeleaf templates
    }
}
