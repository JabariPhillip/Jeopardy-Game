package com.jeopardy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameEvent
{
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final String caseID;        
    private final int playerID;           
    private final String activity;        
    private final LocalDateTime timestamp;
    private final String category;        
    private final int questionValue;      
    private final String playerAnswer;     
    private final String result;          
    private final int finalScore;
    
    public GameEvent(String caseID, int playerID, String activity, LocalDateTime timestamp,
                     String category, int questionValue, String playerAnswer,
                     String result, int finalScore)
    {
        this.caseID = validate(caseID, "Unknown");
        this.playerID = playerID;
        this.activity = validate(activity, "Invalid Activity");
        this.timestamp = (timestamp == null) ? LocalDateTime.now() : timestamp;
        this.category = validate(category, "-");
        this.questionValue = questionValue;
        this.playerAnswer = validate(playerAnswer, "-");
        this.result = validate(result, "-");
        this.finalScore = finalScore;
    }

    private String validate(String input, String defaultValue) {
        if (input == null || input.isBlank()) {
            return defaultValue;
        }
        return input.trim();
    }

    public String getCaseID() {
        return caseID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getActivity() {
        return activity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCategory() {
        return category;
    }

    public int getQuestionValue() {
        return questionValue;
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }

    public String getResult() {
        return result;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public static String csvHeader() {
        return "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Player_Answer,Result,Final_Score";
    }

    public String toCsvRow() {
        String timeString = ISO.format(timestamp);

        return String.join(",",
                escape(caseID),
                String.valueOf(playerID),
                escape(activity),
                escape(timeString),
                escape(category),
                String.valueOf(questionValue),
                escape(playerAnswer),
                escape(result),
                String.valueOf(finalScore)
        );
    }

    private String escape(String input) {
        if (input == null) return "-";
        input = input.trim();
        if (!input.contains(",") && !input.contains("\"")) {
            return input;
        }
        String escaped = input.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}

