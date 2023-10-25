package com.segurosbolivar.customerservice.util;

import com.segurosbolivar.customerservice.model.Customer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {

    public static ByteArrayInputStream customersToCSV(List<Customer> customers) {
        final CSVFormat format = CSVFormat.DEFAULT;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (Customer customer : customers) {
                List<String> rowData = Arrays.asList(
                        String.valueOf(customer.getCustomerTypeId()),
                        customer.getName(),
                        customer.getLastName(),
                        String.valueOf(customer.getBirthdate()),
                        String.valueOf(customer.getDocumentTypeId()),
                        customer.getDocument(),
                        customer.getAddress(),
                        String.valueOf(customer.getAreaId()),
                        customer.getEmail(),
                        customer.getPhoneNumber()
                );
                csvPrinter.printRecord(rowData);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate CSV file: " + e.getMessage());
        }
    }
}
