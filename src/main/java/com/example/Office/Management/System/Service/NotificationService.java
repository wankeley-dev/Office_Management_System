package com.example.Office.Management.System.Service;

import com.example.Office.Management.System.Entity.Notification;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // For WebSocket (optional)

    @Transactional
    public void createNotification(String message, User recipient) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipient(recipient);
        notification.setRead(false); // Default is unread

        // Get the role from the User entity
        String role = recipient.getRole().name(); // Assuming Role is an ENUM

        notificationRepository.saveAndFlush(notification);

        // If using WebSocket, send real-time notification with role info
        messagingTemplate.convertAndSend("/topic/notifications/" + recipient.getId(),
                "Role: " + role + " - " + message);
    }

    // Fetch notifications using user ID
    public List<Notification> getNotificationsForUser(User recipient) {
        return notificationRepository.findByRecipientId(recipient.getId());
    }

    // Fetch notifications by read status
    public List<Notification> getNotificationsForUserByReadStatus(User user, Boolean read) {
        return notificationRepository.findByRecipientIdAndIsRead(user.getId(), read);
    }

    // Mark a notification as read
    @Transactional
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification); // Persist update
    }

    // Mark all notifications as read for a user
    @Transactional
    public void markAllNotificationsAsRead(User recipient) {
        List<Notification> notifications = notificationRepository.findByRecipientId(recipient.getId());
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    // Delete a notification
    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
