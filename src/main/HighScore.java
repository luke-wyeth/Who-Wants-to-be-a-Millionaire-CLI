/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import java.io.*;
import java.util.ArrayList;

public class HighScore implements Serializable
{
    private String name;
    private int score;
    private ArrayList<HighScore> scores;
    
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
    
    @Override
    public String toString()
    {
        return name + " " + score;
    }
    
}
