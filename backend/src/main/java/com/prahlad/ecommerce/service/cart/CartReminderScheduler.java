package com.prahlad.ecommerce.service.cart;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.CartItem;
import com.prahlad.ecommerce.enums.NotificationType;
import com.prahlad.ecommerce.repository.CartItemRepository;
import com.prahlad.ecommerce.service.notification.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartReminderScheduler
{

    private final CartItemRepository cartItemRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 10 * * ?") 
    public void sendCartReminders() 
    {

        LocalDateTime threshold = LocalDateTime.now().minusDays(3);

        List<CartItem> items = cartItemRepository.findByCreatedAtBeforeAndReminderSentFalse(threshold);

        for (CartItem item : items) 
        {

            String email = item.getCart().getUser().getEmail();

            notificationService.sendNotification(
                email,
                "Cart Reminder ",
                "You have items waiting in your cart. Complete your purchase!",
                NotificationType.CART_REMINDER
            );

            item.setReminderSent(true);
        }

        cartItemRepository.saveAll(items);
    
    }
}