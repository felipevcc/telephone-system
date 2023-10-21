package com.segurosbolivar.centerservice.repository;

import com.segurosbolivar.centerservice.dto.CenterCreationDTO;
import jakarta.persistence.EntityManager;
import org.hibernate.dialect.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Objects;

@Repository
public class CenterProceduresRepository {
    @Autowired
    EntityManager entityManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Gets DataSource and avoids null pointer exception
    private final DataSource getDataSource() {
        return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
    }

    public Long createCenter(CenterCreationDTO newCenterData) throws SQLException {
        Connection connection = null;
        CallableStatement callableStatement = null;
        Long newCenterId = null;

        try {
            connection = Objects.requireNonNull(getDataSource()).getConnection();
            callableStatement = connection.prepareCall("{call PCK_CENTER.Proc_CreateCenter(?, ?, ?, ?, ?, ?, ?)}");

            callableStatement.setString(1, newCenterData.getName());
            callableStatement.setString(2, newCenterData.getAddress());
            callableStatement.setString(3, newCenterData.getEmail());
            callableStatement.setString(4, newCenterData.getPhoneNumber());
            callableStatement.setInt(5, newCenterData.getInitialNumber());
            callableStatement.setInt(6, newCenterData.getFinalNumber());
            callableStatement.registerOutParameter(7, Types.NUMERIC);

            callableStatement.execute();

            newCenterId = (Long) callableStatement.getLong(7);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return newCenterId;
    }
}
