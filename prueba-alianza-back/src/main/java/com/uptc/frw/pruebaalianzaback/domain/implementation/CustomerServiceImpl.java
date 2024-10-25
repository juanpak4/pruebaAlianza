package com.uptc.frw.pruebaalianzaback.domain.implementation;

import com.uptc.frw.pruebaalianzaback.interfaces.domain.ICustomerService;
import com.uptc.frw.pruebaalianzaback.interfaces.dto.input.filterCustomerInputDTO;
import com.uptc.frw.pruebaalianzaback.interfaces.entity.Customer;
import com.uptc.frw.pruebaalianzaback.domain.repository.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    ICustomerRepository customerRepository;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> searchCustomers(filterCustomerInputDTO filter) {
        return customerRepository.findByAdvancedSearch(
                filter.getIdBusiness(),
                filter.getPhone(),
                filter.getEmail(),
                filter.getStartDate(),
                filter.getEndDate()
            );
    }

    @Override
    public void saveCustomer(Customer customer) {
        customer.setSharedKey(customer.getEmail().split("@")[0]);
        customerRepository.saveAndFlush(customer);
    }

    @Override
    public void updateCustomer(String sharedKey, Customer customer) {
        customerRepository.findById(sharedKey).map(existingCustomer -> {
            existingCustomer.setIdBusiness(customer.getIdBusiness());
            existingCustomer.setPhone(customer.getPhone());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setStartDate(customer.getStartDate());
            existingCustomer.setEndDate(customer.getEndDate());
            return customerRepository.saveAndFlush(existingCustomer);
        }).orElseThrow(() -> new RuntimeException("Customer not found with shared key: " + sharedKey));
    }

    @Override
    public void deleteCustomer(String sharedKey) {
        if (customerRepository.existsById(sharedKey)) {
            customerRepository.deleteById(sharedKey);
        } else {
            throw new RuntimeException("El cliente no se encuentra registrado");
        }
    }


}
