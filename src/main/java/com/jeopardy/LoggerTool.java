package com.jeopardy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerTool {

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Returns the current timestamp in ISO format.
     */
    public static String getTimestamp() {
        return ISO.format(LocalDateTime.now());
    }

    /**
     * Formats a LocalDateTime object safely.
     */
    public static String formatTimestamp(LocalDateTime time) {
        if (time == null) {
            return ISO.format(LocalDateTime.now());
        }
        return ISO.format(time);
    }

    /**
     * Escapes commas in strings so CSV logs do not break.
     */
    public static String escapeForCSV(String input) {
        if (input == null) return "-";
        
        input = input.trim();

        // If no special characters, return as-is.
        if (!input.contains(",") && !input.contains("\"")) {
            return input;
        }

        // Escape double quotes by doubling them
        String escaped = input.replace("\"", "\"\"");

        // Wrap with quotes
        return "\"" + escaped + "\"";
    }

    /**
     * Generates a unique session ID (optional use)
     */
    public static String generateSessionID() {
        return "GAME" + System.currentTimeMillis();
    }
}
