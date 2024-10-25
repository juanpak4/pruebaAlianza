package com.uptc.frw.pruebaalianzaback.interfaces.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    /**
     * Atributo encargado de definir la llave para compartir un cliente
     */
    @Id
    @Column(name = "shared_key", nullable = false, length = 25)
    private String sharedKey;

    /**
     * Atributo encargado de definir el identificador del negocio asociado
     */
    @Column(name = "id_business", nullable = false, length = 50)
    private String idBusiness;

    /**
     * Atributo encargado de definir el correo electronico de un cliente
     */
    @Column(name = "email", nullable = false, length = 50)
    private String  email;

    /**
     * Atributo encargado de definir el numero de telefono de un cliente
     */
    @Column(name = "phone", nullable = true, length = 10)
    private String phone;

    /**
     * Atributo encargado de definir la fecha en la cual se adiciono el cliente
     */
    @CurrentTimestamp
    @Column(name = "date_added")
    private LocalDate dateAdded;

    /**
     * Atributo encargado de definir la fecha de inicio del cliente
     */
    @Column(name = "startdate")
    private LocalDate startDate;

    /**
     * Atributo encargado de definir la fecha de fin del cliente
     */
    @Column(name = "enddate")
    private LocalDate endDate;

}
