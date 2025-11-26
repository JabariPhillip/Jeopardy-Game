package com.jeopardy;

import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class XMLLoader implements Loader {

    @Override
    public JeopardyBoard loadGameData(String filePath) {

        Map<String, List<Question>> categoryMap = new HashMap<>();

        try {
            File file = new File(filePath);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("QuestionItem");

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    String category = getTagValue("Category", element);
                    int points = Integer.parseInt(getTagValue("Value", element));
                    String questionText = getTagValue("QuestionText", element);

                    String optionA = getTagValue("OptionA", element);
                    String optionB = getTagValue("OptionB", element);
                    String optionC = getTagValue("OptionC", element);
                    String optionD = getTagValue("OptionD", element);

                    String[] options = { optionA, optionB, optionC, optionD };

                    String correctAnswer = getTagValue("CorrectAnswer", element);

                    Question q = new Question(category, points, questionText, options, correctAnswer);

                    categoryMap.putIfAbsent(category, new ArrayList<>());
                    categoryMap.get(category).add(q);
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading XML: " + e.getMessage());
            return null;
        }

        List<Category> categories = new ArrayList<>();
        for (String catName : categoryMap.keySet()) {
            categories.add(new Category(catName, categoryMap.get(catName)));
        }

        return new JeopardyBoard(categories);
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() == 0) return "";
        return nodeList.item(0).getTextContent().trim();
    }
}

