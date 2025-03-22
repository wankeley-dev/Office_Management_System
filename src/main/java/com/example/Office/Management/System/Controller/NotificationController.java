package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.Notification;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Repository.UserRepository;
import com.example.Office.Management.System.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Notification>> getUserNotifications(@RequestParam(required = false) Boolean read, Principal principal) {
        logger.info("Fetching notifications for user with email: {}", principal.getName());

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", principal.getName());
                    return new UsernameNotFoundException("User not found");
                });

        logger.info("Fetching notifications for user ID: {}", user.getId());

        List<Notification> notifications = (read == null) ?
                notificationService.getNotificationsForUser(user) :
                notificationService.getNotificationsForUserByReadStatus(user, read);

        logger.info("Fetched {} notifications for user ID: {}", notifications.size(), user.getId());
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/read/{notificationId}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read");
    }

    @PostMapping("/read/all")
    public ResponseEntity<String> markAllNotificationsAsRead(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        notificationService.markAllNotificationsAsRead(user);
        return ResponseEntity.ok("All notifications marked as read");
    }

    // WebSocket Endpoint to Send Notifications
    @MessageMapping("/send-notification")
    @SendTo("/topic/notifications")
    public Notification sendNotification(Notification notification) {
        notificationService.createNotification(notification.getMessage(), notification.getRecipient());
        return notification;
    }
}