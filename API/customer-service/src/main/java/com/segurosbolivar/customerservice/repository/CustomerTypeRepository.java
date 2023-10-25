package com.segurosbolivar.customerservice.repository;

import com.segurosbolivar.customerservice.model.CustomerType;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTypeRepository extends CrudRepository<CustomerType, Long> {
    @Query("SELECT CUSTOMER_TYPE_ID, CUSTOMER_TYPE_NAME FROM CUSTOMER_TYPE")
    List<CustomerType> findAllCustomerTypes();
}
