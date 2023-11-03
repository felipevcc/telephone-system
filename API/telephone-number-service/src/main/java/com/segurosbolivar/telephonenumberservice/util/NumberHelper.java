package com.segurosbolivar.telephonenumberservice.util;

import com.segurosbolivar.telephonenumberservice.dto.CenterDTO;
import com.segurosbolivar.telephonenumberservice.dto.RangeDTO;

import java.text.Normalizer;
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

    public static String mapNameToNumbers(String fullName) {
        // Map the first four letters of the name to numbers
        StringBuilder result = new StringBuilder();

        String normalized = Normalizer.normalize(fullName, Normalizer.Form.NFD);
        String name = normalized.replaceAll("[^\\p{ASCII}]", "").replaceAll("\\s", "");
        String splitName = name.substring(0, 4).toLowerCase();
        for (char letter : splitName.toCharArray()) {
            char mappedChar = switch (letter) {
                case 'a', 'b', 'c' -> '2';
                case 'd', 'e', 'f' -> '3';
                case 'g', 'h', 'i' -> '4';
                case 'j', 'k', 'l' -> '5';
                case 'm', 'n', 'o' -> '6';
                case 'p', 'q', 'r', 's' -> '7';
                case 't', 'u', 'v' -> '8';
                case 'w', 'x', 'y', 'z' -> '9';
                default -> letter;
            };
            result.append(mappedChar);
        }
        return result.toString();
    }

    public static List<RangeDTO> rangesByNameNumber(String nameNumber, CenterDTO center) {
        // Generate the ranges within the range of the center that have from the second digit the four numbers of the customer name
        int centerRangeStart = center.getInitialNumber();
        int centerRangeEnd = center.getFinalNumber();

        String centerRangeStartStr = String.valueOf(centerRangeStart);

        String centerRangeFirstDigit = centerRangeStartStr.substring(0, 1);

        // Generation of final digits according to the size of the initial number of the center range
        String endInitialNumber = null;
        String endFinalNumber = null;
        if (centerRangeStartStr.length() == 7) {
            endInitialNumber = "00";
            endFinalNumber = "99";
        } else if (centerRangeStartStr.length() == 8) {
            endInitialNumber = "000";
            endFinalNumber = "999";
        }

        // Generation of the complete starting number
        String generatedInitialNumberStr = centerRangeFirstDigit + nameNumber + endInitialNumber;
        String generatedFinalNumberStr = centerRangeFirstDigit + nameNumber + endFinalNumber;

        List<RangeDTO> ranges = new ArrayList<>();

        int startOfRange = Integer.parseInt(generatedInitialNumberStr);
        int endOfRange = Integer.parseInt(generatedFinalNumberStr);

        while (startOfRange <= centerRangeEnd) {
            // Range validations
            if (centerRangeStart >= startOfRange && centerRangeStart <= endOfRange) {
                ranges.add(new RangeDTO(centerRangeStart, Math.min(endOfRange, centerRangeEnd)));
            } else if (startOfRange >= centerRangeStart) {
                ranges.add(new RangeDTO(startOfRange, Math.min(endOfRange, centerRangeEnd)));
            }

            if (String.valueOf(startOfRange).length() == 7) {
                startOfRange += 1000000;
                endOfRange += 1000000;

                // Validation of the next iteration, so that if it goes to 8 digits, it is restructured
                // to keep the customer name number from the second digit onwards
                if (String.valueOf(startOfRange).length() == 8) {
                    String startOfRangeStr = String.valueOf(startOfRange);
                    startOfRange = Integer.parseInt(startOfRangeStr.charAt(0) + nameNumber + "000");
                    endOfRange = Integer.parseInt(startOfRangeStr.charAt(0) + nameNumber + "999");
                }
                continue;
            }
            startOfRange += 10000000;
            endOfRange += 10000000;
        }
        return ranges;
    }
}
