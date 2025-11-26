package com.jeopardy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Game game = new Game();

        game.startGame();

        String filePath = choosePath(scanner);

        game.loadGameData(filePath);

        if (game.getBoard() == null) {
            System.out.println("Game could not start because data did not load correctly.");
            return;
        }

        System.out.print("How many players? ");
        int playerCount = Integer.parseInt(scanner.nextLine().trim());

        List<String> names = new ArrayList<>();

        for (int i = 1; i <= playerCount; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            names.add(scanner.nextLine().trim());
        }

        game.setupPlayers(playerCount, names);

        while (!game.isGameOver()) {
            game.playTurn();
            game.switchPlayer();
        }

        game.endGame();

        System.out.println("Game session complete. Report generated.");
    }

    private static String choosePath(Scanner scanner) {

        System.out.println("\nChoose a question file to load:");
        System.out.println("1. CSV file");
        System.out.println("2. JSON file");
        System.out.println("3. XML file");
        System.out.println("4. Enter a custom file path");

        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {

            case "1":
                return "src/main/resources/sample_game_CSV.csv";

            case "2":
                return "src/main/resources/sample_game_JSON.json";

            case "3":
                return "src/main/resources/sample_game_XML.xml";

            case "4":
                System.out.print("Enter full file path: ");
                return scanner.nextLine().trim();

            default:
                System.out.println("Invalid option. Loading CSV by default.");
                return "src/main/resources/sample_game_CSV.csv";
        }
    }
}
