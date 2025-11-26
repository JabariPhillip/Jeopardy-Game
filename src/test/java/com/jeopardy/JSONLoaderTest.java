package com.jeopardy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JSONLoaderTest {

    private String resourcePath(String fileName) {
        return Path.of("src", "main", "resources", fileName).toString();
    }

    @Test
    void loadSampleJsonBuildsBoard() {
        JSONLoader loader = new JSONLoader();
        JeopardyBoard board = loader.loadGameData(resourcePath("sample_game_JSON.json"));

        assertNotNull(board);
        assertNotNull(board.getCategoryByName("Arrays"));

        Question question = board.getQuestion("Variables & Data Types", 500);
        assertNotNull(question);
        assertEquals("Variables & Data Types", question.getCategory());
        assertEquals("B", question.getCorrectAnswer());
    }

    @Test
    void invalidJsonReturnsNull() throws IOException {
        Path tempFile = Files.createTempFile("jeopardy_json", ".json");
        Files.writeString(tempFile, "[invalid json");

        JSONLoader loader = new JSONLoader();
        JeopardyBoard board = loader.loadGameData(tempFile.toString());

        assertNull(board, "Parsing failures should return null");
    }
}
