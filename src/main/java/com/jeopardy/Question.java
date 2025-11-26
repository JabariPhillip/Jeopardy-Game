package com.jeopardy;

public class Question
{
    private String category;
    private int points;
    private String question;
    private String[] options;
    private String correctAnswer;
    private boolean used;

    public Question(String category, int points, String question,String[] options, String correctAnswer)
    {
        this.category = category;
        this.points = points;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.used = false;
    }

    public String getCategory()
    {
        return category;
    }
    public int getPoints()
    {
        return points;
    }
    public String getQuestion()
    {
        return question;
    }
    public String[] getOptions()
    {
        return options;
    }
    public String getCorrectAnswer()
    {
        return correctAnswer;
    }
    public boolean isCorrect(String answer) {
        if (answer == null) {
            return false; // no answer given
        }
        return answer.trim().equalsIgnoreCase(correctAnswer.trim());
    }
    public Boolean isUsed()
    {
        return used;
    }
    public void markUsed()
    {
        this.used = true;
    }
    
    @Override
    public String toString() {
        return category + " (" + points + " pts): " + question +
                "\nA. " + options[0] +
                "\nB. " + options[1] +
                "\nC. " + options[2] +
                "\nD. " + options[3];
    }

}
