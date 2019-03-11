/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.Serializable;
import java.util.ArrayList;

public class HighScore implements Serializable
{
    private String name;
    private int score;
    
    public HighScore(String name, int score)
    {
        this.name = name;
        this.score = score;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getScore()
    {
       return score;
    }
    
    public void saveScore()
    {
        System.out.println("score saved to file");
        // TODO: add save to file
    }
    
    public ArrayList<HighScore> getHighScores()
    {
        ArrayList<HighScore> scores = new ArrayList();
        
        // TODO: add scan from file
        
        return scores;
    }
}
