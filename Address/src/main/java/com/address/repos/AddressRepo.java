package com.address.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.address.models.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
   List<Address>findAllByEmpId(Long empId);
}
