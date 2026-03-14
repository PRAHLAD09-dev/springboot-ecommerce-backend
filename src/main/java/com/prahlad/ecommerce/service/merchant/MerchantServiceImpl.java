package com.prahlad.ecommerce.service.merchant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.prahlad.ecommerce.entity.Merchant;
import com.prahlad.ecommerce.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService 
{

	private final MerchantRepository merchantRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Merchant getProfile(String email) 
	{

		return merchantRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Merchant not found"));
	}

	@Override
	public Merchant updateProfile(String email, Merchant updatedMerchant) 
	{

		Merchant merchant = merchantRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Merchant not found"));

		merchant.setBusinessName(updatedMerchant.getBusinessName());

		return merchantRepository.save(merchant);
	}

	@Override
	public String changePassword(String email, String oldPassword, String newPassword) 
	{

		Merchant merchant = merchantRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Merchant not found"));

		if (!passwordEncoder.matches(oldPassword, merchant.getPassword())) 
		{
			throw new RuntimeException("Old password is incorrect");
		}

		merchant.setPassword(passwordEncoder.encode(newPassword));

		merchantRepository.save(merchant);

		return "Password changed successfully";
	}

	@Override
	public void deleteAccount(String email) 
	{

		Merchant merchant = merchantRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Merchant not found"));

		merchantRepository.delete(merchant);
	}
}
