package com.employee.impls;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commonlib.exception.EmployeeNotFoundException;
import com.employee.client.AddressClient;

import com.employee.models.Address;
import com.employee.models.Employee;
import com.employee.repos.EmployeeRepo;
import com.employee.services.EmployeeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeImpl implements EmployeeService {
    @Autowired
    private final EmployeeRepo employeeRepo;

    private final AddressClient addressClient;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeImpl.class);

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public List<Employee> allEmployee() {
        // 1. Variable နာမည်ကို employeeList လို့ ပေးလိုက်ပါ
        List<Employee> employeeList = employeeRepo.findAll();

        if (employeeList.isEmpty()) {
            throw new com.commonlib.exception.EmployeeNotFoundException("No employee found");
        }

        // 2. response list တစ်ခု သီးသန့်ဆောက်စရာ မလိုပါ (employeeList ကိုပဲ
        // သုံးလို့ရပါတယ်)
        for (Employee emp : employeeList) {
            List<Address> addresses = new ArrayList<>();
            try {
                // Address Service ဆီကနေ Data တောင်းမယ်
                addresses = addressClient.getAddressByempId(emp.getId());
            } catch (Exception e) {
                logger.error("Address service error for employee id: {}", emp.getId());
            }

            // 3. Employee ထဲမှာ ရလာတဲ့ Address List ကို ထည့်ပေးမယ်
            emp.setAddress(addresses);
        }

        // 4. အရေးကြီးဆုံးအချက် - နောက်ဆုံးမှာ ပြင်ဆင်ပြီးသား list ကို return
        // ပြန်ပေးရမယ်
        return employeeList;
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        Employee dbEmployee = getEmployeeById(id);
        if (dbEmployee != null) {
            dbEmployee.setEmpName(employee.getEmpName());
            dbEmployee.setEmpEmail(employee.getEmpEmail());
            dbEmployee.setEmpCode(employee.getEmpCode());
            dbEmployee.setCompanyName(employee.getCompanyName());
            employeeRepo.save(dbEmployee);
        }
    }

    @Override
    public Employee getEmployeeById(Long id) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            
            e.printStackTrace();
        }
        // 1. Database မှ ဝန်ထမ်းကို အရင်ရှာပါ (ရှာမတွေ့လျှင် 404 Exception ပစ်မည်)
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("No employee with that id: " + id));

        // 2. Address Service ဆီမှ Data တောင်းယူခြင်း
        try {
            List<Address> addresses = addressClient.getAddressByempId(employee.getId());

            // Data ရလာလျှင် (null မဟုတ်လျှင်) employee ထဲသို့ ထည့်ပေးပါ
            if (addresses != null) {
                employee.setAddress(addresses);
            }
        } catch (Exception e) {
            // Address Service ပျက်နေလျှင် သို့မဟုတ် Error တက်လျှင် Log ထုတ်မည်
            // ဒါပေမဲ့ Employee data ကိုတော့ ပုံမှန်အတိုင်း return ပြန်ပေးပါမည်
            logger.error("Could not fetch addresses for employee id {}: {}", employee.getId(), e.getMessage());
        }

        return employee;
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee dbEmployee = getEmployeeById(id);
        if (dbEmployee != null) {
            employeeRepo.deleteById(id);
        }
    }

    @Override
    public Employee getEmployeeByempCodeAndcompanyName(String empCode, String companyName) {
        return employeeRepo.findByEmpCodeAndCompanyName(empCode, companyName);
    }

}
