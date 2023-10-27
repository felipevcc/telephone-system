package com.segurosbolivar.customerservice.repository;

import com.segurosbolivar.customerservice.dto.CustomerCreationDTO;
import com.segurosbolivar.customerservice.dto.CustomerRowDTO;
import com.segurosbolivar.customerservice.dto.CustomerUpdateDTO;
import com.segurosbolivar.customerservice.mapper.CustomerRowDTORowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomerCallRepository {

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
            newCustomerId = callableStatement.getLong(11);

            callableStatement.close();
            connection.close();

            return newCustomerId;

        } catch (Exception e) {
            e.printStackTrace();
            return newCustomerId;
        }
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CustomerRowDTO> findAllCustomersToFile() {
        String sql = "SELECT c.CUSTOMER_TYPE_ID, c.NAME, c.LAST_NAME, c.BIRTHDATE, c.DOCUMENT_TYPE_ID, " +
                "c.DOCUMENT, c.ADDRESS, area.CODE AS AREA_CODE, c.EMAIL, c.PHONE_NUMBER " +
                "FROM CUSTOMER c, GEOGRAPHIC_AREA area " +
                "WHERE c.AREA_ID = area.AREA_ID " +
                "ORDER BY c.CUSTOMER_ID ASC";
        return jdbcTemplate.query(sql, new CustomerRowDTORowMapper());
    }
}
