package com.uptc.frw.pruebaalianzaback.interfaces.dto.input;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class filterCustomerInputDTO {

    private String idBusiness;
    private String email;
    private String phone;
    private LocalDate startDate;
    private LocalDate endDate;
}