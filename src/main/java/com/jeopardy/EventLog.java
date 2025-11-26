package com.jeopardy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventLog
{
    private List<GameEvent> events;
    private String logFile;

    public EventLog()
    {
        this.events = new ArrayList<>();
        this.logFile = "game_event_log.csv";  
        createLogFile();
    }

    private void createLogFile() {
        try (FileWriter writer = new FileWriter(logFile)) {
            writer.write(GameEvent.csvHeader() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error creating log file: " + e.getMessage());
        }
    }

    public void logEvent(GameEvent event)
    {
        events.add(event);

        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(event.toCsvRow() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }

    public List<GameEvent> getEvents() {
        return events;
    }
}

