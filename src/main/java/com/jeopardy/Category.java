package com.jeopardy;

import java.util.List;

public class Category {
    private String name;
    private List<Question> questions;

    public Category(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Question getQuestionByValue(int value) {
        for (Question q : questions) {
            if (q.getPoints() == value && !q.isUsed()) {
                return q;
            }
        }
        return null;
    }

    public String getAvailabilityString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": ");

        for (Question q : questions) {
            if (!q.isUsed()) {
                sb.append(q.getPoints()).append(" ");
            }
        }
        return sb.toString().trim();
    }

    public boolean allUsed() {
        for (Question q : questions) {
            if (!q.isUsed()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Category: " + name + " (" + questions.size() + " questions)";
    }
}
