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
    private int levelProgression; // how far through the level player is [1-5]
    private Random rand;
    private Scanner scan;
    private boolean isPlaying;
    private String[] prize; // corr. to prizeNum
    private int prizeNum; // corr. to current prize player is on
    
    public Game()
    {
        createQuestions setup = new createQuestions();
        questions = setup.create(); 
        
        currentLevel = 0; // start on base level & progression
        levelProgression = 1;
        rand = new Random();
        scan = new Scanner(System.in);
        isPlaying = true;
        
        prizeNum = 0; // incremement for each correct question, correspond to prize amount
        prize = new String[]{"$100","$200","$300","$500","$1,000","$2,000",
            "$4,000","$8,000","$16,000","$32,000","$64,000","$125,000","$25,000","$500,000","$1 MILLION"};
        
        // setup lifelines
        ATA = new ATA();
        PAF = new PAF();
        fiftyFifty = new fiftyFifty();
    }
    
    public void askQuestion()
    {
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("COMMANDS: (1-4: Answer Questions) - (6: WALK AWAY) - (8: 50/50) - (9: PaF) - (0: AtA)");
        System.out.println("\nFor " + prize[prizeNum]);
        
        // select random question from current level
        int qNum = rand.nextInt(questions.get(currentLevel).size());
        Question selectedQ = (Question) questions.get(currentLevel).get(qNum);
        
        selectedQ.printQuestion();
        int pAns = -1;
        
        while (pAns == -1) // all lifelines return -1 to scan answer again
        {
            try // catches exceptions in this object AND lifelines
            {
              pAns = scan.nextInt();
              
                // check for user entering invalid numbers, re-prompt commands
                if (pAns == 5 || pAns == 7) 
                {
                    System.out.println("You need to enter a number between 1 and 4 to answer, or to use a lifeline: \n"
                            + "8: 50/50\n9: Phone a Friend\n0: Ask the Audience");
                    pAns = -1; // trigger re-scan
                }
                else if (pAns == 6)
                {
                    System.out.println("Congratulations! You walked away with " + prize[prizeNum]);
                    isPlaying = false;
                    // TODO: add WALK AWAY functionality
                }
                else if (pAns == 8) // 50/50 lifeline selected
                {
                    pAns = fiftyFifty.use(selectedQ);
                }
                else if (pAns == 9) // phone a friend lifeline
                {
                    pAns = PAF.use(selectedQ);
                }
                else if (pAns == 0) // ask the audience lifeline
                {
                    pAns = ATA.use(selectedQ);
                }
                          
            } catch (java.util.InputMismatchException e)
            {
                System.out.println("Please enter the number of your answer, "
                        + "the number of the lifeline you would like to use,"
                        + "or 6 to walk away!");
                scan.next();
            }
        }
        
        // check once validated answer is recieved (from this object OR from lifeline object)
        // do not check for 6 (walk away)
        if (pAns != 6)
        {
            checkAnswer(selectedQ, qNum, pAns);
        }

    }
    
    public void checkAnswer(Question q, int qNum, int pAns)
    {
        if (pAns == q.getCorrectAns()) // answer is CORRECT
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
        if (levelProgression < 5) // user cannot progress to next level
        {
            levelProgression++;
            prizeNum++; // increment prize num player is currently on
        }
        else if (levelProgression == 5 && prizeNum < 14)
        {
            System.out.println("level moved up");
            currentLevel++; // move up a level
            levelProgression = 1; // reset progression of current level
            prizeNum++; // increment prize num player is currently on
        }
        else if (prizeNum == 14)
        {
            // TODO: win conditions + results
            isPlaying = false;
        }
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
