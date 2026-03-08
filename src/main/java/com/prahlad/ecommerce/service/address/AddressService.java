package com.prahlad.ecommerce.service.address;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prahlad.ecommerce.entity.Address;

@Service
public interface AddressService 
{

    Address addAddress(Address address);

    List<Address> getUserAddresses();

    Address updateAddress(Long id, Address address);

    void deleteAddress(Long id);

}