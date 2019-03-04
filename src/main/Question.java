
package main;

import java.io.Serializable;

public class Question implements Serializable
{ 
    // private and final to avoid tampering by other classes
    private final String question;
    private final String[] answers;
    private final char correctAns;
    public final int level; 
    
    public Question(int level, String question, String[] answers, char correctAns)
    {
        this.level = level;
        this.question = question;
        this.answers = answers;
        this.correctAns = correctAns;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public String getQuestion()
    {
        return question;
    }
    
    public String[] getAnswers()
    {
        return answers;
    }
    
    public char getCorrectAns()
    {
        return correctAns;
    }
    
    public void printQuestion()
    {
        System.out.println(question);
        
        for (int count = 0; count < 4; count++)
        {
            System.out.println(answers[count]);
        }
    }
}
