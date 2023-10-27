package com.segurosbolivar.customerservice.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {
    public static String dateStringFormat(String date) {
        String formattedDate = date.replace("/", "-").replaceAll("\\s", "");

        String[] dateParts = formattedDate.split("-");
        if (dateParts.length != 3) {
            return null;
        } else if (dateParts[0].length() == 2 && dateParts[1].length() == 2 && dateParts[2].length() == 4) {
            formattedDate = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
        }

        if (!isValidDate(formattedDate)) {
            return null;
        }
        return formattedDate;
    }

    public static boolean isValidDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date parsedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);

            int year = calendar.get(Calendar.YEAR);

            // Validate date and that the year is before 5 years ago from the current year
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            return year < currentYear - 5;
        } catch (Exception e) {
            return false;
        }
    }
}
