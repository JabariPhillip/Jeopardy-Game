package com.jeopardy;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LoggerToolTest {

    @Test
    void timestampIsIsoFormatted() {
        String timestamp = LoggerTool.getTimestamp();
        LocalDateTime parsed = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        assertNotNull(parsed);
    }

    @Test
    void formatTimestampHandlesNullAndValidTimes() {
        LocalDateTime time = LocalDateTime.of(2024, 1, 1, 10, 30);
        String formatted = LoggerTool.formatTimestamp(time);
        assertEquals("2024-01-01T10:30:00", formatted);

        String fallback = LoggerTool.formatTimestamp(null);
        assertNotNull(LocalDateTime.parse(fallback, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @Test
    void escapeForCsvWrapsSpecialValues() {
        assertEquals("Hello", LoggerTool.escapeForCSV("Hello"));
        assertEquals("\"Hello,World\"", LoggerTool.escapeForCSV("Hello,World"));
        assertEquals("\"He said \"\"Hi\"\"\"", LoggerTool.escapeForCSV("He said \"Hi\""));
        assertEquals("-", LoggerTool.escapeForCSV(null));
    }

    @Test
    void sessionIdStartsWithPrefix() {
        String session = LoggerTool.generateSessionID();
        assertTrue(session.startsWith("GAME"));
    }
}
