package com.prahlad.ecommerce.service.user;


import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.User;

@Service
public interface UserService 
{

    User getProfile(String email);

    User updateProfile(String email, User updatedUser);

    void deleteAccount(String email);

    String changePassword(String email, String oldPassword, String newPassword);

}