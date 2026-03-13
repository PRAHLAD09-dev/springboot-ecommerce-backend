//package com.prahlad.ecommerce.service.admin;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import com.prahlad.ecommerce.entity.Merchant;
//import com.prahlad.ecommerce.repository.MerchantRepository;
//
//@Service
//@RequiredArgsConstructor
//public class AdminServiceImpl implements AdminService 
//{
//
//    private final MerchantRepository merchantRepository;
//
//    @Override
//    public String approveMerchant(Long merchantId) 
//    {
//
//        Merchant merchant = merchantRepository.findById(merchantId)
//                .orElseThrow(() -> new RuntimeException("Merchant not found"));
//
//        if (merchant.isApproved()) 
//        {
//            return "Merchant already approved";
//        }
//
//        merchant.setApproved(true);
//        merchantRepository.save(merchant);
//
//        return "Merchant approved successfully";
//    }
//}

package com.prahlad.ecommerce.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.Merchant;
import com.prahlad.ecommerce.entity.Order;
import com.prahlad.ecommerce.entity.User;
import com.prahlad.ecommerce.repository.MerchantRepository;
import com.prahlad.ecommerce.repository.OrderRepository;
import com.prahlad.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService 
{

	private final MerchantRepository merchantRepository;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;

	
	@Override
	public String approveMerchant(Long merchantId) 
	{

		Merchant merchant = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new RuntimeException("Merchant not found"));

		if (merchant.isApproved()) 
		{
			return "Merchant already approved";
		}

		merchant.setApproved(true);
		merchant.setActive(true); 

		merchantRepository.save(merchant);

		return "Merchant approved successfully";
	}

	@Override
	public List<User> getAllUsers() 
	{
		return userRepository.findAll();
	}

	
	@Override
	public List<Merchant> getAllMerchants() 
	{
		return merchantRepository.findAll();
	}

	
	@Override
	public String blockMerchant(Long merchantId) 
	{

		Merchant merchant = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new RuntimeException("Merchant not found"));

		merchant.setActive(false);
		merchantRepository.save(merchant);

		return "Merchant blocked successfully";
	}

	@Override
	public String unblockMerchant(Long merchantId) 
	{
		Merchant merchant = merchantRepository.findById(merchantId)
				.orElseThrow(() -> new RuntimeException("Merchant not found"));

		merchant.setActive(true);
		merchantRepository.save(merchant);

		return "Merchant unblocked successfully";
	}

	
	@Override
	public List<Order> getAllOrders() 
	{
		return orderRepository.findAll();
	}

}