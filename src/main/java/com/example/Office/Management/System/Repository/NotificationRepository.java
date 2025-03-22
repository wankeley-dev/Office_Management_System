package com.example.Office.Management.System.Repository;

import com.example.Office.Management.System.Entity.Notification;
import com.example.Office.Management.System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipient(User user);

    List<Notification> findByRecipientId(Long recipientId);

   // List<Notification> findByRead();

    List<Notification> findByRecipientIdAndIsRead(Long recipientId,boolean isRead);

    // New method to find notifications by recipient and read status
    List<Notification> findByRecipientAndIsRead(User recipient, boolean read);
}
