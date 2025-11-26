package com.jeopardy;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {

    public void createReport(Game game, String format) {

        if (!format.equalsIgnoreCase("TXT")) {
            System.out.println("Unsupported format. Only TXT is supported right now.");
            return;
        }

        String fileName = "game_report.txt";

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("========== JEOPARDY GAME REPORT ==========\n");
            writer.write("Session ID: " + game.getSessionID() + "\n");
            writer.write("Start Time: " +
                    game.getStartTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    "\n\n");

            writer.write("--------------- FINAL SCORES --------------\n");
            for (Player p : game.getPlayers()) {
                writer.write(p.getName() + ": " + p.getScore() + " points\n");
            }

            writer.write("\n----------- TURN-BY-TURN SUMMARY ----------\n");

            List<GameEvent> events = game.getLog().getEvents();

            for (GameEvent e : events) {

                if (e.getActivity().equals("Select Question") ||
                    e.getActivity().equals("Answer Question")) {

                    writer.write("\n[" + e.getTimestamp() + "]\n");
                    writer.write("Player ID: " + e.getPlayerID() + "\n");
                    writer.write("Activity: " + e.getActivity() + "\n");
                    writer.write("Category: " + e.getCategory() + "\n");
                    writer.write("Value: " + e.getQuestionValue() + "\n");
                    writer.write("Answer: " + e.getPlayerAnswer() + "\n");
                    writer.write("Result: " + e.getResult() + "\n");
                    writer.write("Score After Play: " + e.getFinalScore() + "\n");
                }
            }

            writer.write("\n===========================================\n");
            writer.write("Report successfully generated.\n");

            System.out.println("Report created: " + fileName);

        } catch (IOException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }
}
