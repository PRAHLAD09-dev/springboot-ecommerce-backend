package com.prahlad.ecommerce.service.user;


import org.springframework.stereotype.Service;
import com.prahlad.ecommerce.dto.user.UserResponse;
import com.prahlad.ecommerce.dto.user.UserUpdateRequest;

@Service
public interface UserService 
{

	
	UserResponse getProfile(String email);

	
	  UserResponse updateProfile(String email, UserUpdateRequest request);

    void deleteAccount(String email , String otp);

    String changePassword(String email, String oldPassword, String newPassword);


	void requestDeleteAccount(String email);
}