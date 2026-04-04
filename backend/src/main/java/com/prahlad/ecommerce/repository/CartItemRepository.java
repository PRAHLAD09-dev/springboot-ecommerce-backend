package com.prahlad.ecommerce.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.prahlad.ecommerce.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> 
{

	List<CartItem> findByCreatedAtBeforeAndReminderSentFalse(LocalDateTime time);
}