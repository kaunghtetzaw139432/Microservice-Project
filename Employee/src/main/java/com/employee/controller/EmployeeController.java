package com.employee.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commonlib.exception.MissingParameterException;
import com.employee.dtos.EmployeeDto;
import com.employee.dtos.Msg;
import com.employee.models.Employee;
import com.employee.services.EmployeeService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> all() {
        List<Employee> allEmployees = employeeService.allEmployee();
        return ResponseEntity.ok(allEmployees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> singleEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/add")
    public ResponseEntity<Msg> addEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmpName(employeeDto.getEmpName());
        employee.setEmpEmail(employeeDto.getEmpEmail());
        employee.setEmpCode(employeeDto.getEmpCode());
        employee.setCompanyName(employeeDto.getCompanyName());
        employee.setCreatedAt(LocalDateTime.now());
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok(new Msg("Employee added successfully", HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Msg> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmpName(employeeDto.getEmpName());
        employee.setEmpEmail(employeeDto.getEmpEmail());
        employee.setEmpCode(employeeDto.getEmpCode());
        employee.setCompanyName(employeeDto.getCompanyName());
        employee.setCreatedAt(LocalDateTime.now());
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(new Msg("Employee updated successfully", HttpStatus.OK.value()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Msg> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new Msg("Employee deleted successfully", HttpStatus.OK.value()));
    }

    @GetMapping("/get-by-empCode-And-companyName")
    public ResponseEntity<Employee> getEmployeebyempCodeAndcompanyName(@RequestParam(required = false) String empCode,
            @RequestParam(required = false) String companyName) {
        List<String> missingParameters = new ArrayList<>();
        if (empCode == null || empCode.trim().isEmpty()) {
            missingParameters.add("empCode");
        }
        if (companyName == null || companyName.trim().isEmpty()) {
            missingParameters.add("companyName");
        }
        if (!missingParameters.isEmpty()) {
            String finalMessage = missingParameters.stream().collect(Collectors.joining(","));
            throw new MissingParameterException("Please provide " + finalMessage);
        }
        Employee response = employeeService.getEmployeeByempCodeAndcompanyName(empCode, companyName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
