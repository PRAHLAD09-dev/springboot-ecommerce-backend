package com.prahlad.ecommerce.service.Order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.Cart;
import com.prahlad.ecommerce.entity.CartItem;
import com.prahlad.ecommerce.entity.Order;
import com.prahlad.ecommerce.entity.OrderItem;
import com.prahlad.ecommerce.entity.Product;
import com.prahlad.ecommerce.entity.User;
import com.prahlad.ecommerce.enums.OrderStatus;
import com.prahlad.ecommerce.repository.CartItemRepository;
import com.prahlad.ecommerce.repository.CartRepository;
import com.prahlad.ecommerce.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService 
{

	 private final CartRepository cartRepository;
	    private final CartItemRepository cartItemRepository;
	    private final OrderRepository orderRepository;

	    public Order placeOrder(User user) 
	    {

	        Cart cart = cartRepository.findByUser(user)
	                .orElseThrow(() -> new RuntimeException("Cart not found"));

	        if (cart.getItems().isEmpty()) 
	        {
	            throw new RuntimeException("Cart is empty");
	        }

	        Order order = new Order();
	        order.setUser(user);
	        order.setStatus(OrderStatus.CREATED);

	        List<OrderItem> orderItems = new ArrayList<>();

	        double totalPrice = 0;

	        for (CartItem cartItem : cart.getItems()) 
	        {

	            Product product = cartItem.getProduct();

	            OrderItem orderItem = new OrderItem();
	            orderItem.setProduct(product);
	            orderItem.setQuantity(cartItem.getQuantity());

	            double itemPrice = product.getPrice() * cartItem.getQuantity();

	            orderItem.setPrice(itemPrice);
	            orderItem.setOrder(order);

	            orderItems.add(orderItem);

	            totalPrice += itemPrice;
	        }

	        order.setOrderItems(orderItems);
	        order.setTotalPrice(totalPrice);

	        Order savedOrder = orderRepository.save(order);

	    
	        cartItemRepository.deleteAll(cart.getItems());
	        cart.getItems().clear();
	        cartRepository.save(cart);

	        return savedOrder;
    }

    public List<Order> getUserOrders(User user) 
    {

        return orderRepository.findByUserId(user.getId());
    }

    public Order getOrderById(Long orderId) 
    {

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
