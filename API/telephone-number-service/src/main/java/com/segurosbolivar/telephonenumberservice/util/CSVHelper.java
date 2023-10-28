package com.segurosbolivar.telephonenumberservice.util;

import com.segurosbolivar.telephonenumberservice.dto.NumberHistoryRowDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {

    public static ByteArrayInputStream historyToCSV(List<NumberHistoryRowDTO> telephoneNumberRecords) {
        final CSVFormat format = CSVFormat.DEFAULT.builder()
                .setQuoteMode(QuoteMode.MINIMAL)
                .setHeader(
                        "CUSTOMER_DOCUMENT_TYPE", "CUSTOMER_DOCUMENT", "TELEPHONE_NUMBER",
                        "CENTER_ID", "ASSIGNMENT_DATE", "RELEASE_DATE"
                ).build();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (NumberHistoryRowDTO numberRecord : telephoneNumberRecords) {
                List<String> rowData = Arrays.asList(
                        numberRecord.getCustomerDocumentType(),
                        numberRecord.getCustomerDocument(),
                        String.valueOf(numberRecord.getTelephoneNumber()),
                        String.valueOf(numberRecord.getCenterId()),
                        numberRecord.getAssignmentDate(),
                        numberRecord.getReleaseDate()
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
