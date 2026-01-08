package com.address.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.address.dtos.AddressDto;
import com.address.dtos.AddressRequest;
import com.address.services.AddressService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {
    @Autowired
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<List<AddressDto>> saveAddress(@RequestBody AddressRequest addressRequest) {
        List<AddressDto> response = addressService.saveAddress(addressRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<List<AddressDto>> updateAddress(
            @RequestBody AddressRequest addressRequest) {
        List<AddressDto> response = addressService.updateAddress(addressRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/allAddress")
    public ResponseEntity<List<AddressDto>> getAllAddress() {
        List<AddressDto> response = addressService.getAllAddress();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long addressId) {
        AddressDto response = addressService.getSingleAddress(addressId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>("Address deleted successfကlly", HttpStatus.OK);
    }

    @GetMapping("/empId/{empId}")
    public ResponseEntity<List<AddressDto>> getAddressByempId(@PathVariable Long empId) {
        List<AddressDto> response = addressService.getAddressByempId(empId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
