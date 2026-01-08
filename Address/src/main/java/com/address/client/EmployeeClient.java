package com.address.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.address.models.Employee;

@FeignClient(name = "employeeClient",url = "${employee.service.url}")
public interface EmployeeClient {

      @GetMapping("/{id}")
    Employee singleEmployee(@PathVariable Long id);
} 
