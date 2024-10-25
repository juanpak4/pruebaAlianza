package com.uptc.frw.pruebaalianzaback.domain;

import com.uptc.frw.pruebaalianzaback.domain.implementation.CustomerServiceImpl;
import com.uptc.frw.pruebaalianzaback.domain.repository.ICustomerRepository;
import com.uptc.frw.pruebaalianzaback.interfaces.dto.input.filterCustomerInputDTO;
import com.uptc.frw.pruebaalianzaback.interfaces.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CustomerServiceTest {

    private final static int numero_cero = 0;

    private final static int numero_uno = 1;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private ICustomerRepository customerRepository;

    /**
     * Prueba unitaria encargada de validar que se realice
     * el correspondiente guardado de los clientes
     */
    @Test
    @Sql(scripts = "/deleteCustomer.sql")
    public void saveCustomer() {
        Customer customer = new Customer(null,
                "Juan Pablo Gonzalez",
                "juanpgonzalez@gmail.com",
                "3235050506", LocalDate.now(), LocalDate.now(), LocalDate.now());

        customerService.saveCustomer(customer);

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).isNotNull();
        assertThat(customerList).isNotEmpty();
        assertThat(customerList.size()).isEqualTo(numero_uno);
        assertThat(customerList.get(0).getSharedKey()).isEqualTo("juanpgonzalez");
    }

    /**
     * Prueba unitaria encargada de buscar los clientes
     * dependiendo del filtrado que le es enviado.</br>
     *
     * Como primera prueba se realiza el filtrado por medio del email
     * esperando que se retorne el primer registro con la sharedKey NomPer1.</br>
     *
     * Como segunda prueba se realiza el filtrado por medio del email
     * esperando que se retorne el segundo registro con la sharedKey NomPer2.</br>
     *
     * Como tercera prueba se realiza el filtrado por medio del telefono
     * esperando que se retorne los dos registros ingresados</br>
     */
    @Test
    @Sql(scripts = "/createCustomers.sql")
    public void searchCustomers() {
        Customer customer1 = new Customer("NomApe1",
                "NomPer1 ApePer1",
                "NomApe1@gmail.com",
                "3235050506", LocalDate.now(), LocalDate.now(),LocalDate.now());
        Customer customer2 = new Customer("NomApe2",
                "NomPer2 ApePer2",
                "NomApe2@gmail.com",
                "3235050506", LocalDate.now(), LocalDate.now(),LocalDate.now());

        filterCustomerInputDTO filter = new filterCustomerInputDTO();
        filter.setEmail("NomApe1@gmail.com");
        List<Customer> foundCustomers = customerService.searchCustomers(filter);
        assertThat(foundCustomers).isNotNull();
        assertThat(foundCustomers).isNotEmpty();
        assertThat(foundCustomers.size()).isEqualTo(numero_uno);
        validarUsusario(foundCustomers.get(numero_cero),customer1);

        filter = new filterCustomerInputDTO();
        filter.setIdBusiness("NomPer2 ApePer2");

        foundCustomers = customerService.searchCustomers(filter);

        assertThat(foundCustomers).isNotNull();
        assertThat(foundCustomers).isNotEmpty();
        assertThat(foundCustomers.size()).isEqualTo(numero_uno);
        validarUsusario(foundCustomers.get(numero_cero),customer2);

        filter = new filterCustomerInputDTO();
        filter.setPhone("3235050506");

        foundCustomers = customerService.searchCustomers(filter);

        assertThat(foundCustomers).isNotNull();
        assertThat(foundCustomers).isNotEmpty();
        assertThat(foundCustomers.size()).isEqualTo(2);
        validarUsusario(foundCustomers.get(numero_cero),customer1);
        validarUsusario(foundCustomers.get(1),customer2);
    }

    /**
     * Prueba unitaria encargada de realizar la actualizacion del usuario
     * con el sharedKey NomApe1, se espera que sus valores sean iguales
     * a los creados en el objeto customer.</br>
     */
    @Test
    @Sql(scripts = "/createCustomers.sql")
    public void updateCustomer(){
        Customer customer = new Customer("NomApe1",
                "NomPer1Nuevo ApePer1Nuevo",
                "NomApe1NUevo@gmail.com",
                "3221111111", LocalDate.now(), LocalDate.now(),LocalDate.now());
        customerService.updateCustomer("NomApe1", customer);
        Customer customerUpdated = customerRepository.findById("NomApe1").get();
        assertThat(customerUpdated).isNotNull();
        validarUsusario(customerUpdated,customer);
    }

    /**
     *  Prueba unitaria encargada de realizar la eliminacion del registro
     *  con sharedKey NomApe1 y validando que el registro con la sharedKey
     *  NomApe2 aun exista y solo se encuentre un registro en la tabla0</br>.
     */
    @Test
    @Sql(scripts = "/createCustomers.sql")
    public void deleteCustomer(){
        Customer customer = new Customer("NomApe2",
                "NomPer2 ApePer2",
                "NomApe2@gmail.com",
                "3235050506", LocalDate.now(), LocalDate.now(),LocalDate.now());
        customerService.deleteCustomer("NomApe1");
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).isNotNull();
        assertThat(customers).isNotEmpty();
        assertThat(customers.size()).isEqualTo(numero_uno);
        validarUsusario(customers.get(numero_cero),customer);

    }


    public void validarUsusario(Customer foundCustomers, Customer customer) {
        assertThat(foundCustomers.getSharedKey()).isEqualTo(customer.getSharedKey());
        assertThat(foundCustomers.getEmail()).isEqualTo(customer.getEmail());
        assertThat(foundCustomers.getPhone()).isEqualTo(customer.getPhone());
        assertThat(foundCustomers.getIdBusiness()).isEqualTo(customer.getIdBusiness());
    }

}
