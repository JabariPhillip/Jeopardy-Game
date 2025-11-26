package com.jeopardy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    private final Path reportPath = Path.of("game_report.txt");

    @Test
    void reportIncludesSessionAndScores() throws IOException {
        Files.deleteIfExists(reportPath);

        Game game = new Game();
        game.startGame();
        Player alice = new Player(1, "Alice");
        Player bob = new Player(2, "Bob");
        alice.setScore(400);
        bob.setScore(200);
        game.getPlayers().add(alice);
        game.getPlayers().add(bob);

        game.getLog().logEvent(new GameEvent(game.getSessionID(), 1, "Select Question",
                LocalDateTime.now(), "Science", 200, "-", "-", 400));
        game.getLog().logEvent(new GameEvent(game.getSessionID(), 1, "Answer Question",
                LocalDateTime.now(), "Science", 200, "A", "Correct", 400));

        ReportGenerator generator = new ReportGenerator();
        generator.createReport(game, "TXT");

        assertTrue(Files.exists(reportPath));
        String content = Files.readString(reportPath);
        assertTrue(content.contains(game.getSessionID()));
        assertTrue(content.contains("Alice: 400"));
        assertTrue(content.contains("Answer Question"));
    }

    @Test
    void unsupportedFormatSkipsFileCreation() throws IOException {
        Files.deleteIfExists(reportPath);

        Game game = new Game();
        ReportGenerator generator = new ReportGenerator();

        generator.createReport(game, "PDF");

        assertFalse(Files.exists(reportPath));
    }
}
