package com.jeopardy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreManagerTest {

    @Test
    void addScoreIncreasesPlayerScore() {
        Player player = new Player(1, "Test");
        ScoreManager manager = new ScoreManager();

        manager.addScore(player, 300);

        assertEquals(300, player.getScore());
    }

    @Test
    void subtractScoreCannotGoNegative() {
        Player player = new Player(1, "Test");
        player.setScore(100);
        ScoreManager manager = new ScoreManager();

        manager.subtractScore(player, 300);

        assertEquals(0, player.getScore());
    }

    @Test
    void resetScoreSetsPlayerToZero() {
        Player player = new Player(1, "Test");
        player.setScore(500);
        ScoreManager manager = new ScoreManager();

        manager.resetScore(player);

        assertEquals(0, player.getScore());
    }

    @Test
    void negativeInputsAreIgnored() {
        Player player = new Player(1, "Test");
        ScoreManager manager = new ScoreManager();

        manager.addScore(player, -50);
        manager.subtractScore(player, -10);

        assertEquals(0, player.getScore(), "Negative values should be treated as zero");
    }
}
