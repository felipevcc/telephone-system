package com.segurosbolivar.customerservice.mapper;

import com.segurosbolivar.customerservice.dto.CustomerRowDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowDTORowMapper implements RowMapper<CustomerRowDTO> {
    @Override
    public CustomerRowDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerRowDTO customer = new CustomerRowDTO();
        customer.setCustomerTypeId(rs.getLong("CUSTOMER_TYPE_ID"));
        customer.setName(rs.getString("NAME"));
        customer.setLastName(rs.getString("LAST_NAME"));
        customer.setBirthdate(rs.getTimestamp("BIRTHDATE").toLocalDateTime());
        customer.setDocumentTypeId(rs.getLong("DOCUMENT_TYPE_ID"));
        customer.setDocument(rs.getString("DOCUMENT"));
        customer.setAddress(rs.getString("ADDRESS"));
        customer.setAreaCode(rs.getLong("AREA_CODE"));
        customer.setEmail(rs.getString("EMAIL"));
        customer.setPhoneNumber(rs.getString("PHONE_NUMBER"));
        return customer;
    }
}
