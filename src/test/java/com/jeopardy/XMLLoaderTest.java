package com.jeopardy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class XMLLoaderTest {

    @Test
    void loadSimpleXmlCreatesQuestions() throws IOException {
        Path tempFile = Files.createTempFile("jeopardy_xml", ".xml");
        String xml = """
                <Jeopardy>
                    <QuestionItem>
                        <Category>Science</Category>
                        <Value>100</Value>
                        <QuestionText>What is H2O?</QuestionText>
                        <OptionA>Water</OptionA>
                        <OptionB>Hydrogen</OptionB>
                        <OptionC>Oxygen</OptionC>
                        <OptionD>Carbon</OptionD>
                        <CorrectAnswer>A</CorrectAnswer>
                    </QuestionItem>
                    <QuestionItem>
                        <Category>Math</Category>
                        <Value>200</Value>
                        <QuestionText>2 + 2 = ?</QuestionText>
                        <OptionA>3</OptionA>
                        <OptionB>4</OptionB>
                        <OptionC>5</OptionC>
                        <OptionD>6</OptionD>
                        <CorrectAnswer>B</CorrectAnswer>
                    </QuestionItem>
                </Jeopardy>
                """;
        Files.writeString(tempFile, xml);

        XMLLoader loader = new XMLLoader();
        JeopardyBoard board = loader.loadGameData(tempFile.toString());

        assertNotNull(board);
        assertNotNull(board.getCategoryByName("Science"));
        assertNotNull(board.getCategoryByName("Math"));
        assertEquals("Water", board.getQuestion("Science", 100).getOptions()[0]);
    }

    @Test
    void invalidXmlReturnsNull() throws IOException {
        Path tempFile = Files.createTempFile("jeopardy_xml_invalid", ".xml");
        Files.writeString(tempFile, "<QuestionItem>");

        XMLLoader loader = new XMLLoader();
        JeopardyBoard board = loader.loadGameData(tempFile.toString());

        assertNull(board, "Malformed XML should return null");
    }
}
