package com.prahlad.ecommerce.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.User;
import com.prahlad.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService 
{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User getProfile(String email) 
	{

		return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public User updateProfile(String email, User updatedUser) 
	{

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		user.setName(updatedUser.getName());

		userRepository.save(user);

		return user;
	}

	@Override
	public void deleteAccount(String email) 
	{

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		userRepository.delete(user);
	}

	@Override
	public String changePassword(String email, String oldPassword, String newPassword) 
	{

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) 
		{
			throw new RuntimeException("Old password is incorrect");
		}

		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);

		return "Password changed successfully";
	}
}