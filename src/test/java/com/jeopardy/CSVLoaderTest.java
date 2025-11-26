package com.jeopardy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CSVLoaderTest {

    private String resourcePath(String fileName) {
        return Path.of("src", "main", "resources", fileName).toString();
    }

    @Test
    void loadSampleCsvCreatesBoardWithCategories() {
        CSVLoader loader = new CSVLoader();
        JeopardyBoard board = loader.loadGameData(resourcePath("sample_game_CSV.csv"));

        assertNotNull(board);
        assertNotNull(board.getCategoryByName("Variables & Data Types"));
        assertNotNull(board.getCategoryByName("Control Structures"));
        assertEquals(5, board.getCategories().size());

        Question question = board.getQuestion("Control Structures", 300);
        assertNotNull(question);
        assertEquals("Control Structures", question.getCategory());
        assertEquals(300, question.getPoints());
    }

    @Test
    void loaderIgnoresRowsWithMissingColumns() throws IOException {
        Path tempFile = Files.createTempFile("jeopardy_csv", ".csv");
        String content = "Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer\n" +
                "Science,100,Valid question,A,B,C,D,A\n" +
                "Invalid,row,missing,columns\n";
        Files.writeString(tempFile, content);

        CSVLoader loader = new CSVLoader();
        JeopardyBoard board = loader.loadGameData(tempFile.toString());

        assertNotNull(board);
        Category science = board.getCategoryByName("Science");
        assertNotNull(science);
        assertEquals(1, science.getQuestions().size(), "Invalid rows should be skipped");
    }
}
