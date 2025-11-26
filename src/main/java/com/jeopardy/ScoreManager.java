package com.jeopardy;

public class ScoreManager {

    public void addScore(Player player, int points) {
        if (player == null) return;
        if (points < 0) points = 0;

        int newScore = player.getScore() + points;
        player.setScore(newScore);
    }

    public void subtractScore(Player player, int points) {
        if (player == null) return;
        if (points < 0) points = 0;

        int newScore = player.getScore() - points;
        
        if (newScore < 0) {
            newScore = 0;
        }

        player.setScore(newScore);
    }

    public void resetScore(Player player) {
        if (player == null) return;
        player.setScore(0);
    }
}

