package com.jeopardy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JeopardyBoardTest {

    @Test
    void getCategoryByNameIsCaseInsensitive() {
        JeopardyBoard board = new JeopardyBoard(sampleCategories());

        Category category = board.getCategoryByName("science");
        assertNotNull(category);
        assertEquals("Science", category.getName());
    }

    @Test
    void questionsCannotBeReused() {
        JeopardyBoard board = new JeopardyBoard(sampleCategories());

        Question first = board.getQuestion("Science", 100);
        assertNotNull(first);
        first.markUsed();

        Question reused = board.getQuestion("Science", 100);
        assertNull(reused, "Used questions should not be returned");
    }

    @Test
    void allUsedReturnsTrueWhenEveryQuestionIsConsumed() {
        JeopardyBoard board = new JeopardyBoard(sampleCategories());

        for (Category category : board.getCategories()) {
            for (Question question : category.getQuestions()) {
                question.markUsed();
            }
        }

        assertTrue(board.allUsed());
    }

    private List<Category> sampleCategories() {
        List<Category> categories = new ArrayList<>();
        List<Question> science = new ArrayList<>();
        science.add(new Question("Science", 100, "Q1", new String[]{"A", "B", "C", "D"}, "A"));
        science.add(new Question("Science", 200, "Q2", new String[]{"A", "B", "C", "D"}, "B"));
        categories.add(new Category("Science", science));

        List<Question> math = new ArrayList<>();
        math.add(new Question("Math", 100, "Q3", new String[]{"A", "B", "C", "D"}, "C"));
        categories.add(new Category("Math", math));

        return categories;
    }
}
