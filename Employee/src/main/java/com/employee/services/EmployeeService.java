package com.employee.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.employee.models.Employee;

@Service
public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> allEmployee();

    void updateEmployee(Long id, Employee employee);

    Employee getEmployeeById(Long id);

    void deleteEmployee(Long id);

    Employee getEmployeeByempCodeAndcompanyName(String empCode, String companyName);
}
