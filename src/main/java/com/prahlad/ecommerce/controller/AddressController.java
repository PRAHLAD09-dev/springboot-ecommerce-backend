package com.prahlad.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prahlad.ecommerce.entity.Address;
import com.prahlad.ecommerce.service.address.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController 
{

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> addAddress(@RequestBody Address address) 
    {

        return ResponseEntity.ok(addressService.addAddress(address));
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() 
    {

        return ResponseEntity.ok(addressService.getUserAddresses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(
            @PathVariable Long id,
            @RequestBody Address address) 
    {

        return ResponseEntity.ok(addressService.updateAddress(id, address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) 
    {

        addressService.deleteAddress(id);

        return ResponseEntity.noContent().build();
    }
}