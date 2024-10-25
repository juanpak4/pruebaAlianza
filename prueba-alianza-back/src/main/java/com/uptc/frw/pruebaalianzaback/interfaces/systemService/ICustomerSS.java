package com.uptc.frw.pruebaalianzaback.interfaces.systemService;

import com.uptc.frw.pruebaalianzaback.interfaces.dto.input.filterCustomerInputDTO;
import com.uptc.frw.pruebaalianzaback.interfaces.entity.Customer;
import com.uptc.frw.pruebaalianzaback.utils.CustomDetailMessage;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICustomerSS {

    CustomDetailMessage getAll();
    CustomDetailMessage searchCustomers(filterCustomerInputDTO filter);
    CustomDetailMessage saveCustomer(Customer customer);
    CustomDetailMessage updateCustomer(String sharedKey, Customer customer);
    CustomDetailMessage deleteCustomer(String sharedKey);
}
