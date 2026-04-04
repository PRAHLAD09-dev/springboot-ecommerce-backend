package com.prahlad.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.prahlad.ecommerce.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification , Long>
{
	
}