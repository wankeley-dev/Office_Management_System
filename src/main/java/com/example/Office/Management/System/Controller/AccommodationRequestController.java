package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.AccommodationRequest;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Service.AccommodationRequestService;
import com.example.Office.Management.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/accommodation-requests")
public class AccommodationRequestController {

    private final AccommodationRequestService accommodationRequestService;

    @Autowired
    private UserService userService;

    public AccommodationRequestController(AccommodationRequestService accommodationRequestService) {
        this.accommodationRequestService = accommodationRequestService;
    }

    // Display form to submit an accommodation request
    @GetMapping("/submit")
    public String showAccommodationRequestForm(Model model) {
        model.addAttribute("accommodationRequest", new AccommodationRequest());
        return "accommodation-request-form"; // Thymeleaf template name
    }

    // Handle form submission for accommodation request
    @PostMapping("/submit")
    public String submitAccommodationRequest(@ModelAttribute AccommodationRequest accommodationRequest,
                                             @RequestParam("accommodationType") String accommodationType,
                                             Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        // Convert String to Enum
        accommodationRequest.setAccommodationType(AccommodationRequest.AccommodationType.valueOf(accommodationType));
        accommodationRequest.setUser(user);

        accommodationRequestService.submitAccommodationRequest(accommodationRequest);
        return "redirect:/accommodation-requests/list"; // Redirect after submission
    }

    // Display list of accommodation requests for a specific user
    @GetMapping("/user/{userId}")
    public String getAccommodationRequestsByUser(@PathVariable Long userId, Model model) {
        List<AccommodationRequest> requests = accommodationRequestService.getAccommodationRequestsByUser(userId);
        model.addAttribute("requests", requests);
        return "userAccommodationRequest"; // Thymeleaf template name
    }

    // Approve an accommodation request
    @PostMapping("/{id}/approve")
    public String approveAccommodationRequest(@PathVariable Long id) {
        accommodationRequestService.approveAccommodationRequest(id);
        return "redirect:/accommodation-requests/AdminList";
    }

    // Reject an accommodation request
    @PostMapping("/{id}/reject")
    public String rejectAccommodationRequest(@PathVariable Long id) {
        accommodationRequestService.rejectAccommodationRequest(id);
        return "redirect:/accommodation-requests/AdminList";
    }

    @GetMapping("/list")
    public String listAllAccommodationRequests(Model model) {
        List<AccommodationRequest> requests = accommodationRequestService.getAllAccommodationRequests();
        model.addAttribute("requests", requests);
        return "userAccommodationRequest"; // Thymeleaf template name
    }

    // Display all accommodation requests (for admin)
    @GetMapping("/AdminList")
    public String AdminListAllAccommodationRequests(Model model) {
        List<AccommodationRequest> requests = accommodationRequestService.getAllAccommodationRequests();
        model.addAttribute("requests", requests);
        return "accommodation-request-list"; // Thymeleaf template name
    }


}
