package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Entity.WorkOrder;
import com.example.Office.Management.System.Repository.UserRepository;
import com.example.Office.Management.System.Repository.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    public WorkOrder submitWorkOrder(WorkOrder workOrder) {

        // Get HR/Admin and notify them
        User hrAdmin = getHRAdmin();
        if (hrAdmin != null) {
            notificationService.createNotification("New Vehicle request submitted by " + workOrder.getUser().getFullName(), hrAdmin);
        }
        return workOrderRepository.save(workOrder);
    }

    private User getHRAdmin() {
        return userRepository.findByRole(User.Role.ADMIN); // Assuming Role is an ENUM

    }

    public List<WorkOrder> getWorkOrdersByUser(Long userId) {
        return workOrderRepository.findByUserId(userId);
    }

    public List<WorkOrder> getAllWorkOrders() {
        return workOrderRepository.findAll();
    }

    @Transactional
    public WorkOrder updateWorkOrderStatus(Long workOrderId, WorkOrder.WorkOrderStatus status) {
        Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(workOrderId);
        if (optionalWorkOrder.isPresent()) {
            WorkOrder workOrder = optionalWorkOrder.get();
            workOrder.setStatus(status);
            // Notify the user
            notificationService.createNotification("Your Vehicle request has been approved.", workOrder.getUser());
            return workOrderRepository.save(workOrder);
        } else {
            throw new RuntimeException("Work Order not found with ID: " + workOrderId);
        }
    }
}
