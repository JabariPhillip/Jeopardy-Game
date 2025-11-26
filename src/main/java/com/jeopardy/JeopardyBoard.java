package com.jeopardy;

import java.util.List;

public class JeopardyBoard {
    private List<Category> categories;

    public JeopardyBoard(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Category getCategoryByName(String name) {
        if (name == null) {
            return null;
        }

        for (Category c : categories) {
            if (c.getName().equalsIgnoreCase(name.trim())) {
                return c;
            }
        }
        return null;
    }

    public Question getQuestion(String categoryName, int value) {
        Category cat = getCategoryByName(categoryName);

        if (cat == null) {
            return null;
        }

        return cat.getQuestionByValue(value);
    }

    public boolean allUsed() {
        for (Category c : categories) {
            if (!c.allUsed()) {
                return false;
            }
        }
        return true;
    }

    public String getBoardStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Question Board Status ===\n");

        for (Category c : categories) {
            sb.append(c.getAvailabilityString()).append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        String out = "Jeopardy Board:\n";
        for (Category c : categories) {
            out += " - " + c.getName() + "\n";
        }
        return out;
    }
}
