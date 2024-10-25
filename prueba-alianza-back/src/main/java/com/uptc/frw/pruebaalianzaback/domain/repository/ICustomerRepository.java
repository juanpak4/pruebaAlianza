package com.uptc.frw.pruebaalianzaback.domain.repository;


import com.uptc.frw.pruebaalianzaback.interfaces.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer,String> {

    @Query("SELECT c FROM Customer c WHERE " +
            "(:idBusiness IS NULL OR c.idBusiness = :idBusiness) AND " +
            "(:phone IS NULL OR c.phone = :phone) AND " +
            "(:email IS NULL OR c.email = :email) AND" +
            "(:startDate IS NULL OR c.startDate = :startDate) AND" +
            "(:endDate IS NULL OR c.endDate = :endDate)"
    )
    List<Customer> findByAdvancedSearch(
            @Param("idBusiness") String idBusiness,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
