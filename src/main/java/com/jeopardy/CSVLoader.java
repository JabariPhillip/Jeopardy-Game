package com.jeopardy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVLoader implements Loader {

    @Override
    public JeopardyBoard loadGameData(String filePath) {

        Map<String, List<Question>> categoryMap = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 8){
                    continue;
                }
                
                String category = parts[0].trim();
                int points = Integer.parseInt(parts[1].trim());
                String questionText = parts[2].trim();

                String[] options = new String[] {
                    parts[3].trim(),
                    parts[4].trim(),
                    parts[5].trim(),
                    parts[6].trim()
                };
                
                String correctAnswer = parts[7].trim();
                
                Question q = new Question(category, points, questionText, options, correctAnswer);
                categoryMap.putIfAbsent(category, new ArrayList<>());
                categoryMap.get(category).add(q);
            } 
        } catch (IOException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
            return null;
        }

        List<Category> categories = new ArrayList<>();
        
        for (String catName : categoryMap.keySet()) {
            categories.add(new Category(catName, categoryMap.get(catName)));
        }
        
        return new JeopardyBoard(categories);
    }
}

