package com.prahlad.ecommerce.service.notification;

import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.Notification;
import com.prahlad.ecommerce.enums.NotificationType;
import com.prahlad.ecommerce.repository.NotificationRepository;
import com.prahlad.ecommerce.service.otp.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService 
{

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public void sendNotification(String email, String title, String message, NotificationType type) 
    {

 
        Notification n = new Notification();
        n.setEmail(email);
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);

        notificationRepository.save(n);

        emailService.sendSimpleMail(email, title, message);
    }
}
