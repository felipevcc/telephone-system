package com.segurosbolivar.telephonenumberservice.util;

import java.util.ArrayList;
import java.util.List;

public class NumberHelper {

    public static Boolean isCommercial(Integer number) {
        String numberStr = String.valueOf(number);

        // Commercial telephone number checks
        return repeatedNumbers(numberStr) || equalNumberGroups(numberStr) || repeatedGroupsAtEnd(numberStr);
    }

    public static Boolean repeatedNumbers(String number) {
        // Check if there are at least four consecutive repeated numbers in the telephone number
        int requiredRepeatedNumbers = 4;
        for (int i = 0; i < number.length() - (requiredRepeatedNumbers - 1); i++) {
            String subsequence = number.substring(i, i + requiredRepeatedNumbers);
            if (subsequence.chars().distinct().count() == 1) {
                return true;
            }
        }
        return false;
    }

    public static Boolean equalNumberGroups(String number) {
        // Check if there are at least three groups of equal numbers in the telephone number
        int requiredGroups = 3;
        List<String> groups = new ArrayList<>();

        for (int i = 0; i < number.length() - 1; i++) {
            if (number.charAt(i) == number.charAt(i + 1)) {
                groups.add(number.substring(i, i + 2));
                // Skip to the next pair of digits
                i++;
            }
        }
        // Validate if there are the required number of groups
        return groups.size() >= requiredGroups;
    }

    public static boolean repeatedGroupsAtEnd(String number) {
        // Check if there are at least two groups repeated at the end of the telephone number
        int requiredRepeatedGroups = 2;
        int groupSize = 2;
        for (int firstIdx = 0; firstIdx < number.length() - groupSize * requiredRepeatedGroups + 1; firstIdx++) {
            String subsequence = number.substring(firstIdx, firstIdx + groupSize);
            int groupCounter = 1;
            int secondIdx = firstIdx + groupSize;
            while (secondIdx < number.length() - groupSize + 1 && subsequence.equals(number.substring(secondIdx, secondIdx + groupSize))) {
                groupCounter++;
                secondIdx += groupSize;
            }
            // Validate if there are the required number of groups and if the groups are at the end of the telephone number
            if (groupCounter >= requiredRepeatedGroups && secondIdx == number.length()) {
                return true;
            }
        }
        return false;
    }
}
