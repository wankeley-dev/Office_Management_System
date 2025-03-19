package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Entity.WorkOrder;
import com.example.Office.Management.System.Service.UserService;
import com.example.Office.Management.System.Service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/work-orders")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private UserService userService;

    @GetMapping("/submit")
    public String showWorkOrderForm(Model model) {
        model.addAttribute("workOrder", new WorkOrder());
        return "/workOrder/work-order-form";
    }

    @PostMapping("/submit")
    public String submitWorkOrder(@ModelAttribute WorkOrder workOrder, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        workOrder.setUser(user);
        workOrderService.submitWorkOrder(workOrder);
        return "redirect:/work-orders/UserList";
    }

    @GetMapping("/user/{userId}")
    public String getWorkOrdersByUser(@PathVariable Long userId, Model model) {
        List<WorkOrder> workOrders = workOrderService.getWorkOrdersByUser(userId);
        model.addAttribute("workOrders", workOrders);
        return "/workOrder/user-work-order-list";
    }

    @PostMapping("/{id}/update-status")
    public String updateWorkOrderStatus(@PathVariable Long id, @RequestParam("status") String status) {
        workOrderService.updateWorkOrderStatus(id, WorkOrder.WorkOrderStatus.valueOf(status));
        return "redirect:/work-orders/list";
    }

    @GetMapping("/list")
    public String listAllWorkOrders(Model model) {
        List<WorkOrder> workOrders = workOrderService.getAllWorkOrders();
        model.addAttribute("workOrders", workOrders);
        return "/workOrder/work-order-list";
    }

    @GetMapping("/UserList")
    public String UserListAllWorkOrders(Model model) {
        List<WorkOrder> workOrders = workOrderService.getAllWorkOrders();
        model.addAttribute("workOrders", workOrders);
        return "/workOrder/user-work-order-list";
    }
}
