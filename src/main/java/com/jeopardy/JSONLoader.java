package com.jeopardy;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONLoader implements Loader {

    @Override
    public JeopardyBoard loadGameData(String filePath) {

        Map<String, List<Question>> categoryMap = new HashMap<>();

        try {
            String jsonText = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray arr = new JSONArray(jsonText);

            for (int i = 0; i < arr.length(); i++) {
          
                JSONObject obj = arr.getJSONObject(i);

                String category = obj.getString("Category");
                int points = obj.getInt("Value");
                String questionText = obj.getString("Question");

                JSONObject opts = obj.getJSONObject("Options");

                String[] options = new String[] {
                    opts.getString("A"),
                    opts.getString("B"),
                    opts.getString("C"),
                    opts.getString("D")
                };

                String correct = obj.getString("CorrectAnswer");

                Question q = new Question(category, points, questionText, options, correct);

                categoryMap.putIfAbsent(category, new ArrayList<>());
                categoryMap.get(category).add(q);
            }

        } catch (Exception e) {
            System.out.println("Error loading JSON: " + e.getMessage());
            return null;
        }

        List<Category> categories = new ArrayList<>();
        for (String catName : categoryMap.keySet()) {
            categories.add(new Category(catName, categoryMap.get(catName)));
        }

        return new JeopardyBoard(categories);
    }
}

