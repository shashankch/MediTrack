package com.health.meditrack.util;

public final class Validator {

    private Validator() {
        // prevent instantiation
    }

    /**
     * Generic null check for any object
     */
    public static <T> boolean isNull(T value) {
        return value == null;
    }

    /**
     * Checks if a string is null or blank
     */
    public static boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Checks if the given string is a valid integer
     */
    public static boolean isNumber(String value) {

        if (isNullOrBlank(value)) {
            return false;
        }

        try {
            if (value.matches("\\d+")) {
                Integer.valueOf(value);
                return true;
            }
            return false;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}