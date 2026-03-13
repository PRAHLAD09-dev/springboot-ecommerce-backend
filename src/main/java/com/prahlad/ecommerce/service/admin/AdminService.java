package com.prahlad.ecommerce.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.Merchant;
import com.prahlad.ecommerce.entity.Order;
import com.prahlad.ecommerce.entity.User;

@Service
public interface AdminService {

    String approveMerchant(Long merchantId);

    List<User> getAllUsers();

    List<Merchant> getAllMerchants();

    String blockMerchant(Long merchantId);

    String unblockMerchant(Long merchantId);

    List<Order> getAllOrders();
}