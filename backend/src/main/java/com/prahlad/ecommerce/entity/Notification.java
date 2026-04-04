package com.prahlad.ecommerce.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.prahlad.ecommerce.enums.NotificationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification 
{

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String title;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private boolean isRead = false;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();
}
