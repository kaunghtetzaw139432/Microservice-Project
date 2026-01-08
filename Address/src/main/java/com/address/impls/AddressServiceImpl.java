package com.address.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.address.client.EmployeeClient;
import com.address.dtos.AddressDto;
import com.address.dtos.AddressRequest;
import com.address.dtos.AddressRequestDto;
import com.address.exception.AddressNotFoundException;
import com.address.models.Address;
import com.address.repos.AddressRepo;
import com.address.services.AddressService;
import org.slf4j.Logger;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private final AddressRepo addressRepo;
    @Autowired
    private final ModelMapper mapper;

    private final EmployeeClient employeeClient;

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        // Todo :check if employee exists
        employeeClient.singleEmployee(addressRequest.getEmpId());
        List<Address> listToSave = this.saveOrUpdateAddressRequest(addressRequest);
        List<Address> saveAddress = addressRepo.saveAll(listToSave);
        return saveAddress.stream().map(addresses -> mapper.map(addresses, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> updateAddress(AddressRequest addressRequest) {
        // Todo :check if employee exists
        employeeClient.singleEmployee(addressRequest.getEmpId());
        List<Address> addressByEmpId = addressRepo.findAllByEmpId(addressRequest.getEmpId());
        if (addressByEmpId.isEmpty()) {
            logger.info("No address found for employee id {}", addressRequest.getEmpId());
            logger.info("Creating new address for employee id{}", addressRequest.getEmpId());
        }
        List<Address> listToUpdate = this.saveOrUpdateAddressRequest(addressRequest);
        List<Long> upcomingNonNullIds = listToUpdate.stream().map(Address::getId).filter(Objects::nonNull).toList();
        List<Long> existingIds = addressByEmpId.stream().map(Address::getId).toList();
        List<Long> idsToDelete = existingIds.stream().filter(id -> !upcomingNonNullIds.contains(id)).toList();
        if (!idsToDelete.isEmpty()) {
            addressRepo.deleteAllById(idsToDelete);
        }
        List<Address> updatedAddresses = addressRepo.saveAll(listToUpdate);
        return updatedAddresses.stream()
                .map(address -> mapper.map(address, AddressDto.class)) // updatedAddresses အစား address (မှန်ကန်သော
                                                                       // item) ကို သုံးပါ
                .toList();

    }

    @Override
    public AddressDto getSingleAddress(Long id) {
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with that id : " + id));
        return mapper.map(address, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddress() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Address> all = addressRepo.findAll();
        if (all.isEmpty()) {
            throw new RuntimeException("No addrress found");
        }
        return all.stream().map(address -> mapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with that id : " + id));
        addressRepo.delete(address);
    }

    @Override
    public List<AddressDto> getAddressByempId(Long empId) {
        List<Address> addresseByempId = addressRepo.findAllByEmpId(empId);
        if (addresseByempId.isEmpty()) {
            throw new AddressNotFoundException("No address found for employee id " + empId);
        }
        return addresseByempId.stream().map(address -> mapper.map(address, AddressDto.class)).toList();
    }

    private List<Address> saveOrUpdateAddressRequest(AddressRequest addressRequest) {
        List<Address> listToSave = new ArrayList<>();
        for (AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtosList()) {
            Address address = new Address();
            address.setStreet(addressRequestDto.getStreet());
            address.setCity(addressRequestDto.getCity());
            address.setId(addressRequestDto.getId() != null ? addressRequestDto.getId() : null);
            address.setCountry(addressRequestDto.getCountry());
            address.setPinCode(addressRequestDto.getPinCode());
            address.setAddressType(addressRequestDto.getAddressType());
            address.setEmpId(addressRequest.getEmpId());
            listToSave.add(address);

        }
        return listToSave;
    }

}
