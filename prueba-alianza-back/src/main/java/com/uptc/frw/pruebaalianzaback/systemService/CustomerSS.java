package com.uptc.frw.pruebaalianzaback.systemService;

import com.uptc.frw.pruebaalianzaback.interfaces.domain.ICustomerService;
import com.uptc.frw.pruebaalianzaback.interfaces.dto.input.filterCustomerInputDTO;
import com.uptc.frw.pruebaalianzaback.interfaces.entity.Customer;
import com.uptc.frw.pruebaalianzaback.interfaces.systemService.ICustomerSS;
import com.uptc.frw.pruebaalianzaback.utils.CustomDetailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomerSS implements ICustomerSS {

    private final ICustomerService customerService;

    @Autowired
    public CustomerSS(ICustomerService customerService) {
        this.customerService = customerService; // Asegúrate de usar el tipo correcto aquí
    }

    @Override
    public CustomDetailMessage getAll() {
        try{
            List<Customer> customers = customerService.getAll();
            log.info("Cliente obtenido correctamente");
            return new CustomDetailMessage(HttpStatus.OK.value(), "Cliente obtenido correctamente", customers);
        }catch (Exception ex){
            log.error("Error al encontrar el cliente: {}", ex.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), new ArrayList<>());
        }

    }

    @Override
    public CustomDetailMessage searchCustomers(filterCustomerInputDTO filter) {

        try{
            List<Customer> customers = customerService.searchCustomers(filter);
            log.info("Cliente obtenido correctamente");
            return new CustomDetailMessage(HttpStatus.OK.value(), "Cliente obtenido correctamente", customers);
        }catch (Exception ex){
            log.error("Error al encontrar el cliente: {}", ex.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), new ArrayList<>());
        }

    }

    @Override
    public CustomDetailMessage saveCustomer(Customer customer) {
        try{
            customerService.saveCustomer(customer);
            log.info("Cliente creado correctamente");
            return new CustomDetailMessage(HttpStatus.OK.value(), "Cliente creado correctamente", new ArrayList<>());
        }catch (Exception ex){
            log.error("Error al crear el cliente: {}", ex.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), new ArrayList<>());
        }
    }

    @Override
    public CustomDetailMessage updateCustomer(String sharedKey, Customer customer) {
        try{
            customerService.updateCustomer(sharedKey,customer);
            log.info("Cliente actualizado correctamente");
            return new CustomDetailMessage(HttpStatus.OK.value(), "Cliente actualizado correctamente", new ArrayList<>());
        }catch (Exception ex){
            log.error("Error al actualizar el cliente: {}", ex.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), new ArrayList<>());
        }
    }

    @Override
    public CustomDetailMessage deleteCustomer(String sharedKey) {
        try{
            customerService.deleteCustomer(sharedKey);
            log.info("Cliente eliminado correctamente");
            return new CustomDetailMessage(HttpStatus.OK.value(), "Cliente eliminado correctamente", new ArrayList<>());
        }catch (Exception ex){
            log.error("Error al eliminar el cliente: {}", ex.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), new ArrayList<>());
        }
    }
}
