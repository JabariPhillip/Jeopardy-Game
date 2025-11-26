package com.jeopardy;

public class Player
{
    private int id;
    private String name;
    private int score;

    public Player(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.score = 0;
    }

    public int getID()
    {
       return id; 
    }
    public String getName()
    {
        return name;
    }
    public int getScore()
    {
        return score;
    }
    public void addScore(int points)
    {
        this.score = score + points;
    }
    public void subtractScore(int points)
    {
        this.score = score - points;
    }
    public void setScore(int score)
    {
        this.score = score;
    }
    
    public String toString()
    {
        return "Player " + id + ": " + name + " | Score: " + score;
    }
}
