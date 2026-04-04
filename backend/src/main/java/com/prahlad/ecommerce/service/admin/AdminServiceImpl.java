package com.prahlad.ecommerce.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.dto.merchant.MerchantResponse;
import com.prahlad.ecommerce.dto.order.OrderItemDTO;
import com.prahlad.ecommerce.dto.order.OrderResponse;
import com.prahlad.ecommerce.dto.user.UserResponse;
import com.prahlad.ecommerce.entity.Merchant;
import com.prahlad.ecommerce.entity.Order;
import com.prahlad.ecommerce.entity.User;
import com.prahlad.ecommerce.enums.NotificationType;
import com.prahlad.ecommerce.exception.ResourceNotFoundException;
import com.prahlad.ecommerce.repository.MerchantRepository;
import com.prahlad.ecommerce.repository.OrderRepository;
import com.prahlad.ecommerce.repository.UserRepository;
import com.prahlad.ecommerce.service.notification.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService 
{

	private final MerchantRepository merchantRepository;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final NotificationService notificationService;


	// ================= APPROVE =================
	@Override
	public String approveMerchant(Long merchantId) 
	
	{

		Merchant merchant = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new ResourceNotFoundException("Merchant not found"));

		if (merchant.isApproved())
		{
		    notificationService.sendNotification(
		        merchant.getEmail(),
		        "Already Approved",
		        "Your merchant account is already approved.",
		        NotificationType.MERCHANT_APPROVED
		    );

		    return "Merchant already approved";
		}

		merchant.setApproved(true);
		merchant.setActive(true);

		merchantRepository.save(merchant);
		
		notificationService.sendNotification(
			    merchant.getEmail(),
			    "Account Approved ",
			    "Your merchant account has been approved. You can now start selling ",
			    NotificationType.MERCHANT_APPROVED
			);

		return "Merchant approved successfully";
	}

	// ================= USERS =================
	@Override
	public List<UserResponse> getAllUsers() 
	{

		return userRepository.findAll().stream().map(this::mapUserToDTO).toList();
	}

	// ================= MERCHANTS =================
	@Override
	public List<MerchantResponse> getAllMerchants() 
	{

		return merchantRepository.findAll().stream().map(this::mapMerchantToDTO).toList();
	}

	// ================= BLOCK =================
	@Override
	public String blockMerchant(Long merchantId) 
	{

		Merchant merchant = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new ResourceNotFoundException("Merchant not found"));

		merchant.setActive(false);
		merchantRepository.save(merchant);
		
		notificationService.sendNotification(
				merchant.getEmail(),
		    "Account Blocked ",
		    "Your account has been blocked by admin.",
		    NotificationType.ACCOUNT_BLOCKED
		);

		return "Merchant blocked successfully";
	}

	// ================= UNBLOCK =================
	@Override
	public String unblockMerchant(Long merchantId) 
	{

		Merchant merchant = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new ResourceNotFoundException("Merchant not found"));

		merchant.setActive(true);
		merchantRepository.save(merchant);
		
		notificationService.sendNotification(
				merchant.getEmail(),
			    "Account Activated ",
			    "Your account is now active again.",
			    NotificationType.ACCOUNT_UNBLOCKED
			);

		return "Merchant unblocked successfully";
	}

	// ================= ORDERS =================
	@Override
	public List<OrderResponse> getAllOrders() 
	{

		return orderRepository.findAll().stream().map(this::mapOrderToDTO).toList();
	}

	// ================= MAPPERS =================

	private UserResponse mapUserToDTO(User user) 
	{
		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
	}

	private MerchantResponse mapMerchantToDTO(Merchant merchant) 
	{
		return new MerchantResponse(merchant.getId(), merchant.getBusinessName(), merchant.getEmail(),
				merchant.isApproved(), merchant.isActive());
	}

	private OrderResponse mapOrderToDTO(Order order) 
	{

		List<OrderItemDTO> items = order.getOrderItems().stream()
				.map(i -> new OrderItemDTO(i.getProduct().getName(), i.getQuantity(), i.getPrice())).toList();

		return new OrderResponse(order.getId(), order.getStatus(), order.getTotalPrice(), order.isPaid(), items);
	}
}