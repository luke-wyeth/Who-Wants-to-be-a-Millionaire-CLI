package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import main.LIFELINES.ATA;
import main.LIFELINES.PAF;
import main.LIFELINES.fiftyFifty;
import main.LIFELINES.lifeline;

public class Game
{
    public ArrayList<ArrayList> questions;
    public lifeline ATA; // ask the audience
    public lifeline PAF; // phone a friend
    public lifeline fiftyFifty; // 50:50 lifeline
    private int currentLevel;
    private int levelProgression; // how far through the level player is (1-5)
    private Random rand;
    private Scanner scan;
    private boolean isPlaying;
    private String[] prize;
    private int prizeNum;
    
    public Game()
    {
        createQuestions setup = new createQuestions();
        questions = setup.create(); 
        
        currentLevel = 0; // start on base level & progression
        levelProgression = 1;
        rand = new Random();
        scan = new Scanner(System.in);
        isPlaying = true;
        
        prizeNum = 0; // incremement for each correct question
        prize = new String[]{"$100","$200","$300","$500","$1,000","$2,000",
            "$4,000","$8,000","$16,000","$32,000","$64,000","$125,000","$25,000","$500,000","$1 MILLION"};
        
        // setup lifelines
        ATA = new ATA();
        PAF = new PAF();
        fiftyFifty = new fiftyFifty();
    }
    
    public void askQuestion()
    {
        System.out.println("For " + prize[prizeNum]);
        // select random question from current level
        int qNum = rand.nextInt(questions.get(currentLevel).size());
        Question selectedQ = (Question) questions.get(currentLevel).get(qNum);
        
        selectedQ.printQuestion();
        
        int pAns = scan.nextInt();
        
        if (pAns == selectedQ.getCorrectAns()) // answer is CORRECT
        {
            System.out.println("Correct!");
            incrementProg();
            questions.get(currentLevel).remove(qNum); // remove used question from list
        }
        else // answer is INCORRECT
        {
            System.out.println("No! You lose"); 
            isPlaying = false;
        }
    }
 
    // should only be called when a question has been answered CORRECTLY
    public void incrementProg() // used to move progress OR move up a level
    {
        if (levelProgression < 5) // user cannot progress to next leve
        {
            levelProgression++;
        }
        if (levelProgression == 5 && currentLevel < 2)
        {
            System.out.println("level moved up");
            currentLevel++; // move up a level
            levelProgression = 1; // reset progression of current level
        }
        else if (currentLevel == 2)
        {
            // TODO: win conditions + results
            isPlaying = false;
        }
        
        prizeNum++; // increment prize num player is currently on
    }
    
    public int getLevel()
    {
        return currentLevel;
    }
    
    public boolean isPlaying()
    {
        return isPlaying;
    }
}
