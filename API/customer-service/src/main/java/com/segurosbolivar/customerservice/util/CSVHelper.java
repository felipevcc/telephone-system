package com.segurosbolivar.customerservice.util;

import com.segurosbolivar.customerservice.dto.CustomerRowDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {

    public static ByteArrayInputStream customersToCSV(List<CustomerRowDTO> customers) {
        final CSVFormat format = CSVFormat.DEFAULT.builder()
                .setQuoteMode(QuoteMode.MINIMAL)
                .setHeader(
                        "CUSTOMER_TYPE_ID", "NAME", "LAST_NAME", "BIRTHDATE", "DOCUMENT_TYPE_ID",
                        "DOCUMENT", "ADDRESS", "AREA_CODE", "EMAIL", "PHONE_NUMBER"
                ).build();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out, false, StandardCharsets.UTF_8), format)) {
            for (CustomerRowDTO customer : customers) {
                List<String> rowData = Arrays.asList(
                        String.valueOf(customer.getCustomerTypeId()),
                        customer.getName(),
                        customer.getLastName(),
                        customer.getBirthdate(),
                        String.valueOf(customer.getDocumentTypeId()),
                        customer.getDocument(),
                        customer.getAddress(),
                        String.valueOf(customer.getAreaCode()),
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

    public static boolean isCSV(MultipartFile file) {
        String fileType = "text/csv";
        return fileType.equals(file.getContentType());
    }
}
