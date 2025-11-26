package com.jeopardy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventLogTest {

    private final Path logPath = Path.of("game_event_log.csv");

    @Test
    void constructorCreatesFileWithHeader() throws IOException {
        EventLog log = new EventLog();
        assertNotNull(log);

        List<String> lines = Files.readAllLines(logPath);
        assertFalse(lines.isEmpty());
        assertEquals(GameEvent.csvHeader(), lines.get(0));
    }

    @Test
    void logEventWritesToMemoryAndFile() throws IOException {
        EventLog log = new EventLog();
        GameEvent event = new GameEvent("CASE1", 1, "Select Question", LocalDateTime.now(),
                "Science", 200, "A", "Pending", 0);

        log.logEvent(event);

        assertEquals(1, log.getEvents().size());
        List<String> lines = Files.readAllLines(logPath);
        assertTrue(lines.size() >= 2);
        String lastLine = lines.get(lines.size() - 1);
        assertTrue(lastLine.contains("CASE1"));
        assertTrue(lastLine.contains("Select Question"));
    }
}
