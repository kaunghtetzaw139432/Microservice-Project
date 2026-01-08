package com.address.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.address.dtos.AddressDto;
import com.address.dtos.AddressRequest;

@Service
public interface AddressService {
       List<AddressDto> saveAddress(AddressRequest addressRequest);

       List<AddressDto> updateAddress( AddressRequest addressRequest);

       AddressDto getSingleAddress(Long id);

       List<AddressDto> getAllAddress();

       void deleteAddress(Long id);

       List<AddressDto> getAddressByempId(Long empId);

}
