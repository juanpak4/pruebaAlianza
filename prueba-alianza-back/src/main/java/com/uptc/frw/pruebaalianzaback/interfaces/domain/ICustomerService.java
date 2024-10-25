package com.uptc.frw.pruebaalianzaback.interfaces.domain;


import com.uptc.frw.pruebaalianzaback.interfaces.dto.input.filterCustomerInputDTO;
import com.uptc.frw.pruebaalianzaback.interfaces.entity.Customer;

import java.util.List;

public interface ICustomerService {

    List<Customer> getAll();
    List<Customer> searchCustomers(filterCustomerInputDTO filter);
    void saveCustomer(Customer customer);
    void updateCustomer(String sharedKey, Customer customer);
    void deleteCustomer(String sharedKey);
}
