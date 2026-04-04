package com.prahlad.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prahlad.ecommerce.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>
{
	  List<Address> findByUserId(Long userId);
	
}
