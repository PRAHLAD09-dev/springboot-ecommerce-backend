package com.prahlad.ecommerce.service.merchant;

import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.Merchant;

@Service
public interface MerchantService 
{

    Merchant getProfile(String email);

    Merchant updateProfile(String email, Merchant updatedMerchant);

    String changePassword(String email, String oldPassword, String newPassword);

    void deleteAccount(String email);
}