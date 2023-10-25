package com.segurosbolivar.customerservice.repository;

import com.segurosbolivar.customerservice.dto.CustomerCreationDTO;
import com.segurosbolivar.customerservice.dto.CustomerUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Repository
public class CustomerProcedureRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Long createCustomer(CustomerCreationDTO newCustomer) {
        Long newCustomerId = null;
        try {
            Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PCK_CUSTOMER.Proc_CreateCustomer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            callableStatement.setLong(1, newCustomer.getCustomerTypeId());
            callableStatement.setString(2, newCustomer.getName());
            callableStatement.setString(3, newCustomer.getLastName());
            callableStatement.setDate(4, Date.valueOf(newCustomer.getBirthdate()));
            callableStatement.setLong(5, newCustomer.getDocumentTypeId());
            callableStatement.setString(6, newCustomer.getDocument());
            callableStatement.setString(7, newCustomer.getAddress());
            callableStatement.setLong(8, newCustomer.getAreaId());
            callableStatement.setString(9, newCustomer.getEmail());
            callableStatement.setString(10, newCustomer.getPhoneNumber());
            callableStatement.registerOutParameter(11, Types.NUMERIC);

            callableStatement.execute();
            newCustomerId = (Long) callableStatement.getLong(11);

            callableStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCustomerId;
    }

    public void updateCustomer(Long customerId, CustomerUpdateDTO customerData) {
        try {
            Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PCK_CUSTOMER.Proc_UpdateCustomer(?, ?, ?, ?)}");

            callableStatement.setLong(1, customerId);
            callableStatement.setString(2, customerData.getAddress());
            callableStatement.setString(3, customerData.getEmail());
            callableStatement.setString(4, customerData.getPhoneNumber());

            callableStatement.execute();

            callableStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
