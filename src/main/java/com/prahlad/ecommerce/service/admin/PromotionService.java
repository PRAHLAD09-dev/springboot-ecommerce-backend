package com.prahlad.ecommerce.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.User;
import com.prahlad.ecommerce.enums.NotificationType;
import com.prahlad.ecommerce.repository.UserRepository;
import com.prahlad.ecommerce.service.notification.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromotionService 
{

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public void sendPromotionToAllUsers(String title, String message) 
    {

        List<User> users = userRepository.findAll();

        for (User user : users) 
        {

            notificationService.sendNotification(
                    user.getEmail(),
                    title,
                    message,
                    NotificationType.PROMOTION
            );
        }
    }
}