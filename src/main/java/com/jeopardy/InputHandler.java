package com.jeopardy;

import java.util.Scanner;

public class InputHandler {

    private static Scanner scanner = new Scanner(System.in);

    public static String promptCategory(JeopardyBoard board) {

        System.out.println("\nAvailable Categories:");
        for (Category c : board.getCategories()) {
            System.out.println(" - " + c.getName());
        }

        System.out.print("Select a category: ");
        String category = scanner.nextLine().trim();

        // Validate category
        while (board.getCategoryByName(category) == null) {
            System.out.println("Invalid category. Try again.");
            System.out.print("Select a category: ");
            category = scanner.nextLine().trim();
        }

        return category;
    }

    public static int promptValue(String categoryName) {

        System.out.print("Enter point value for " + categoryName + ": ");

        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());

                if (value == 100 || value == 200 || value == 300 || value == 400 || value == 500) {
                    return value;
                }

                System.out.print("Invalid value. Choose 100, 200, 300, 400, or 500: ");

            } catch (NumberFormatException e) {
                System.out.print("Please enter a NUMBER (100, 200, 300, 400, 500): ");
            }
        }
    }

    public static String getUserAnswer() {
        System.out.print("Your answer (A/B/C/D): ");
        String answer = scanner.nextLine().trim();

        while (!(answer.equalsIgnoreCase("A") ||
                 answer.equalsIgnoreCase("B") ||
                 answer.equalsIgnoreCase("C") ||
                 answer.equalsIgnoreCase("D"))) 
        {
            System.out.print("Invalid answer. Enter A, B, C, or D: ");
            answer = scanner.nextLine().trim();
        }

        return answer.toUpperCase();
    }

    // Optional helper
    public static int getInt(String msg) {
        System.out.print(msg);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a NUMBER: ");
            }
        }
    }
}

