package com.uptc.frw.pruebaalianzaback.api.customer;

import com.uptc.frw.pruebaalianzaback.interfaces.domain.ICustomerService;
import com.uptc.frw.pruebaalianzaback.interfaces.dto.input.filterCustomerInputDTO;
import com.uptc.frw.pruebaalianzaback.interfaces.entity.Customer;
import com.uptc.frw.pruebaalianzaback.interfaces.systemService.ICustomerSS;
import com.uptc.frw.pruebaalianzaback.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    private final ICustomerSS customerSS;

    @Autowired
    public CustomerController(ICustomerSS customerSS) {
        this.customerSS = customerSS; // Asegúrate de usar el tipo correcto aquí
    }

    /**
     * Metodo encargado de retornar todos los clientes registrados.
     *
     * @return lista de clientes.
     */
    @GetMapping("/getAll")
    public ResponseEntity<CustomDetailMessage> getAll() {
        CustomDetailMessage response = customerSS.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Metodo encargado de traer los clientes depediendo del filtrado que le sea
     * enviado.</br>
     * @param filter objeto con la informacion a filtrar
     * @return lista de clientes
     */
    @PostMapping("/get")
    public ResponseEntity<CustomDetailMessage> searchCustomer(@RequestBody filterCustomerInputDTO filter) {
        CustomDetailMessage response = customerSS.searchCustomers(filter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Metodo encargado de realizar la creacion de un cliente.</br>
     *
     * @param input objeto con la informacion necesaria para crear un cliente
     *
     * @return retorna una respuesta positiva en caso de ingresar correctamente
     * al cliente o una erronea en caso de fallar en el proceso
     */
    @PostMapping("create")
    public ResponseEntity<CustomDetailMessage> createCustomer(
            @RequestBody Customer input ) {
        CustomDetailMessage response = customerSS.saveCustomer(input);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Metodo encargado de realizar la actualizacion de un cliente.</br>
     *
     * @param sharedKey atributo con el valor de la llave del cliente
     * @param input objeto con la informacion necesaria para crear un cliente.
     * @return retorna una respuesta positiva en caso de actualizar correctamente
     * al cliente o una erronea en caso de fallar en el proceso
     */
    @PutMapping("update/{sharedKey}")
    public ResponseEntity<CustomDetailMessage> updateCustomer(@PathVariable String sharedKey,
            @RequestBody Customer input ) {
        CustomDetailMessage response = customerSS.updateCustomer(sharedKey, input);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    /**
     * Metodo encargado de realizar la eliminacion de un cliente.</br>
     *
     * @param sharedKey atributo con el valor de la llave del cliente
     *
     * @return retorna una respuesta positiva en caso de eliminarlo correctamente
     * al cliente o una erronea en caso de fallar en el proceso
     */
    @DeleteMapping("delete/{sharedKey}")
    public ResponseEntity<CustomDetailMessage> deleteCustomer(@PathVariable String sharedKey) {
        CustomDetailMessage response = customerSS.deleteCustomer(sharedKey);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
