package com.jeopardy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Game {
    private List<Player> players;
    private JeopardyBoard board;
    private EventLog log;
    private ReportGenerator report;
    private ScoreManager scoreManager;
    private Loader loader;
    private boolean gameEnd;
    private int playerNum;
    private LocalDateTime startTime;
    private String sessionID;

    public Game() {
        this.players = new ArrayList<>();
        this.log = new EventLog();
        this.scoreManager = new ScoreManager();
        this.report = new ReportGenerator();
        this.gameEnd = false;
        this.playerNum = 0;
        this.sessionID = "GAME" + System.currentTimeMillis();
    }

    public String getSessionID() {
        return sessionID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public EventLog getLog() {
        return log;
    }

    public JeopardyBoard getBoard() {
        return board;
    }

    public void startGame() {
        startTime = LocalDateTime.now();
        log.logEvent(new GameEvent(sessionID, 0, "Start Game", startTime, "-", 0, "-", "-", 0));
        System.out.println("=== Welcome to Jeopardy! ===");
        System.out.println("Game session: " + sessionID);
    }

    public void loadGameData(String filePath) {
        FileTypeSelector selector = new FileTypeSelector();
        loader = selector.selectLoader(filePath);

        if (loader != null) {
            board = loader.loadGameData(filePath);
            log.logEvent(new GameEvent(sessionID, 0, "File Loaded Successfully",
                    LocalDateTime.now(), "-", 0, "-", "Success", 0));
        } else {
            System.out.println("Unsupported file type.");
        }
    }

    public void setupPlayers(int playerCount, List<String> names) {
        for (int i = 0; i < playerCount; i++) {
            Player p = new Player(i + 1, names.get(i));
            players.add(p);
            log.logEvent(new GameEvent(sessionID, p.getID(), "Player Joined",
                    LocalDateTime.now(), "-", 0, "-", "Ready", 0));
        }
    }

    public void playTurn() {
        Player current = getCurrentPlayer();

        System.out.println("\nIt's " + current.getName() + "'s turn.");
        System.out.println(board.getBoardStatus());
        String category = InputHandler.promptCategory(board);
        int value = InputHandler.promptValue(category);

        Question q = board.getQuestion(category, value);
        if (q == null) {
            System.out.println("That question has already been used. Pick another.");
            return;
        }

        log.logEvent(new GameEvent(sessionID, current.getID(), "Select Question",
                LocalDateTime.now(), category, value, "-", "-", current.getScore()));

        System.out.println("\n" + q.toString());
        String userAnswer = InputHandler.getUserAnswer();

        checkAnswer(userAnswer, current, q);
    }

    public void checkAnswer(String userAnswer, Player current, Question q) {
        boolean correct = q.isCorrect(userAnswer);

        if (correct) {
            scoreManager.addScore(current, q.getPoints());
            System.out.println("Correct! +" + q.getPoints() + " points.");
        } else {
            scoreManager.subtractScore(current, q.getPoints());
            System.out.println("Wrong! -" + q.getPoints() + " points.");
        }

        log.logEvent(new GameEvent(sessionID, current.getID(), "Answer Question",
                LocalDateTime.now(), q.getCategory(), q.getPoints(), userAnswer,
                correct ? "Correct" : "Incorrect", current.getScore()));

        q.markUsed();
    }

    public void switchPlayer() {
        playerNum = (playerNum + 1) % players.size();
    }

    public boolean poolEmpty() {
        return board.allUsed();
    }

    public void endGame() {
        gameEnd = true;
        System.out.println("\n=== Game Over ===");
        log.logEvent(new GameEvent(sessionID, 0, "End Game", LocalDateTime.now(),
                "-", 0, "-", "-", 0));

        makeReport("TXT");
    }

    public void makeReport(String format) {
        report.createReport(this, format);

        log.logEvent(new GameEvent(sessionID, 0, "Generate Report", LocalDateTime.now(),
                "-", 0, "-", "Completed", 0));
    }

    public Player getCurrentPlayer() {
        return players.get(playerNum);
    }

    public boolean isGameOver() {
        return gameEnd || poolEmpty();
    }
}
