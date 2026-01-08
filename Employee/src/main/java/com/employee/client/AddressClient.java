package com.employee.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.employee.models.Address;

@FeignClient(name = "ADDRESS")
public interface AddressClient {
    @GetMapping("/address/empId/{empId}")
    List<Address> getAddressByempId(@PathVariable Long empId);
}
