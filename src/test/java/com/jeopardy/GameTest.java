package com.jeopardy;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void checkAnswerUpdatesScoreAndLogsResult() {
        Game game = new Game();
        Player player = new Player(1, "Alice");
        game.getPlayers().add(player);

        String[] options = {"A", "B", "C", "D"};
        Question question = new Question("Science", 200, "What?", options, "A");

        int startingEvents = game.getLog().getEvents().size();

        game.checkAnswer("A", player, question);

        assertEquals(200, player.getScore());
        assertTrue(question.isUsed());
        List<GameEvent> events = game.getLog().getEvents();
        assertEquals(startingEvents + 1, events.size());
        GameEvent last = events.get(events.size() - 1);
        assertEquals("Answer Question", last.getActivity());
        assertEquals("Correct", last.getResult());
        assertEquals(200, last.getFinalScore());
    }

    @Test
    void gameEndsWhenAllQuestionsUsed() {
        Game game = new Game();
        game.startGame();
        game.loadGameData(PathHelper.sampleCsv());

        JeopardyBoard board = game.getBoard();
        assertNotNull(board);

        for (Category category : board.getCategories()) {
            for (Question question : category.getQuestions()) {
                question.markUsed();
            }
        }

        assertTrue(game.isGameOver(), "Game should end when the board is empty");
    }

    private static class PathHelper {
        static String sampleCsv() {
            return java.nio.file.Path.of("src", "main", "resources", "sample_game_CSV.csv").toString();
        }
    }
}
