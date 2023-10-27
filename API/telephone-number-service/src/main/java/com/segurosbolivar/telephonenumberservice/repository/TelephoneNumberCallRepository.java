package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.dto.TelephoneNumberAssignmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.Objects;

@Repository
public class TelephoneNumberCallRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void runNumberTrackingProcess() {
        try {
            Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PCK_TELEPHONE_NUMBER.Proc_TrackingNumbers}");
            callableStatement.execute();
            callableStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long assignTelephoneNumber(TelephoneNumberAssignmentDTO telephoneNumberData) {
        Long newNumberRecordId = null;
        try {
            Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PCK_TELEPHONE_NUMBER.Proc_AssignTelephoneNumber(?, ?, ?)}");
            callableStatement.setLong(1, telephoneNumberData.getCenterId());
            callableStatement.setLong(2, telephoneNumberData.getCustomerId());
            callableStatement.setInt(3, telephoneNumberData.getPhoneNumber());
            callableStatement.registerOutParameter(4, Types.NUMERIC);

            callableStatement.execute();
            newNumberRecordId = callableStatement.getLong(4);

            callableStatement.close();
            connection.close();

            return newNumberRecordId;

        } catch (Exception e) {
            e.printStackTrace();
            return newNumberRecordId;
        }
    }

    public void releaseTelephoneNumber(Integer phoneNumber) {
        try {
            Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PCK_TELEPHONE_NUMBER.Proc_ReleaseTelephoneNumber(?)}");
            callableStatement.setInt(1, phoneNumber);
            callableStatement.execute();

            callableStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
