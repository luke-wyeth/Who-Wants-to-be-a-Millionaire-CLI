package main;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import main.LIFELINES.*;

public class Game
{
    public ArrayList<ArrayList> questions;
    private final lifeline ATA; // ask the audience
    private final lifeline PAF; // phone a friend
    private final lifeline fiftyFifty; // 50:50 lifeline
    private int currentLevel;
    private int levelProgression; // how far through the level player is [1-5]
    private final Random rand;
    private final Scanner scan;
    private boolean isPlaying;
    private final String[] prize; // corr. to prizeNum
    private int prizeNum; // corr. to current prize player is on 
    public ArrayList<HighScore> scores;
    // end conditions, use to determine game end state
    private boolean walkedAway;
    private boolean lost;
    private boolean won;
    private int lifelinesUsed; // tally lifelines used to calculate score
    private HighScore finalScore;
    
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
            "$4,000","$8,000","$16,000","$32,000","$64,000","$125,000","$250,000","$500,000","$1 MILLION"};
        
        // setup lifelines
        ATA = new ATA();
        PAF = new PAF();
        fiftyFifty = new fiftyFifty();
        
        // setup end conditions
        walkedAway = false;
        lost = false;
        won = false;
        
        // load in previous scores to scores list
        getScores();
    }
    
    public void askQuestion()
    {
        System.out.println("______________________________________________________________________________________");
        System.out.println("COMMANDS: (1-4: Answer Questions) - (6: WALK AWAY) - (8: 50/50) - (9: PaF) - (0: AtA)");
        System.out.println("\nFor " + prize[prizeNum]);
        
        // select random question from current level
        int qNum = rand.nextInt(questions.get(currentLevel).size());
        Question selectedQ = (Question) questions.get(currentLevel).get(qNum);
        
        selectedQ.printQuestion();
        processAnswer(selectedQ, qNum);
    }
    
    private void processAnswer(Question selectedQ, int qNum)
    {
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
                else if (pAns == 6) // player wants to walk away
                {
                    if (prizeNum == 0)
                    {
                        System.out.println("Are you sure you want to walk away with $0? (y/n)");
                    }
                    else
                    {
                         System.out.println("Are you sure you want to walk away with " + prize[prizeNum-1] + "? (y/n)");
                    }
                   
                    // confirm walk away action
                    char response = scan.next().charAt(0);      
                    if (response == 'y')
                    {
                        walkedAway = true;
                        end();
                    }
                    else
                    {
                        System.out.println("Enter your answer:");
                        pAns = -1;
                    }
                }
                else if (pAns == 8) // 50/50 lifeline selected
                {
                    pAns = fiftyFifty.use(selectedQ);
                    lifelinesUsed++;
                }
                else if (pAns == 9) // phone a friend lifeline
                {
                    pAns = PAF.use(selectedQ);
                    lifelinesUsed++;
                }
                else if (pAns == 0) // ask the audience lifeline
                {
                    pAns = ATA.use(selectedQ);
                    lifelinesUsed++;
                }
                          
            } catch (java.util.InputMismatchException e)
            {
                System.out.println("Please enter the number of your answer, "
                        + "the number of the lifeline you would like to use,"
                        + "or 6 to walk away!");
                scan.next();
            }
        }
        
        // check once valid answer is recieved (from this object OR from lifeline object)
        // do not check for 6 (player has walked away)
        if (pAns != 6)
        {
            checkAnswer(selectedQ, qNum, pAns);
        }
    }
    
    private void checkAnswer(Question q, int qNum, int pAns)
    {
        if (pAns == q.getCorrectAns()) // answer is CORRECT
        {
            System.out.println("Correct!");
            incrementProg();
            questions.get(currentLevel).remove(qNum); // remove used question from list
        }
        else // answer is INCORRECT
        {
            lost = true;
            end();
        }
    }
 
    private void end()
    {
        if (walkedAway)
        {   
            if (prizeNum != 0)
            {
                System.out.println("Congratulations! You walked away with " + prize[prizeNum-1]);
            }
            else
            {
                System.out.println("You walked away with $0. You quit before it even started.");
            } 
        }
        if (lost)
        {
            if (currentLevel == 0)
            {
                System.out.println("That is incorrect! You lose, and unfortunately you walk away with $0");
            }
            else if (currentLevel == 1)
            {
                System.out.println("That is incorrect! You lose, but you still get to walk away with " + prize[5]);
            }
            else if (currentLevel == 3)
            {
                System.out.println("That is incorrect! You lose, but you still get to walk away with " + prize[10]);
            }
        }
        if (won)
        {
            System.out.println("-----------------------------------------"
                           + "\nCONGRATULATIONS! You have won $1 MILLION."
                           + "\n-----------------------------------------");
        }
        
        // calculate score = prizeNum * 5 - amount of lifelines used * 3
        int score = (prizeNum * 5) - (lifelinesUsed * 3);
        
        System.out.println("\nYour score is " + score);
        String saveName;
        System.out.println("Enter your name to save your score, or enter 6 to quit without saving!");
        saveName = scan.next();
        
        // if user wants to save score, create new score object and
        // call method to save score to file
        if (saveName.charAt(0) != '6')
        {
            finalScore = new HighScore(saveName, score);
            saveScore();
        }
        
        isPlaying = false;
    }
    
    // should only be called when a question has been answered CORRECTLY
   private void incrementProg() // used to move progress OR move up a level
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
            won = true;
            end();
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
    
    //--------- SCORE MANAGEMENT  ---------
    
    
    // load scores from file into program
    private void getScores()
    {
        scores = new ArrayList<HighScore>();
        File scoreFile = new File("scores");
        boolean newFile = false;
        
        try
        { 
            // try to create new file in case this is the first time the game has been played
            // if newFile = true then know not to scan for scores (there arent any)
            newFile = scoreFile.createNewFile(); 
            
        } catch (IOException ex)
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        // a new file has not been created so there are previous scores to load
        if (newFile == false)
        {
            try(ObjectInputStream in = new ObjectInputStream(new ObjectInputStream(new FileInputStream("scores")))) 
            {
                scores = (ArrayList<HighScore>)in.readObject();

            } catch (IOException | ClassNotFoundException e)
            {
                System.err.println(e);
            }
        }
    }
    
    // save scores + new score to file 
    private void saveScore()
    {
        scores.add(finalScore); // add users score to file
        // TODO: format output of scores etc
        Collections.sort(scores); // sort low to high
        Collections.reverse(scores); // reverse to high to low
        
        // if adding new score will make list > 15, remove lowest score to 
        // keep score list 15 scores maximum
        if (scores.size() > 15) 
        {
            scores.remove(15);
        }
        
        boolean success = true;

        do
        {
            try(ObjectOutputStream out = new ObjectOutputStream(new ObjectOutputStream(new FileOutputStream("scores")))) 
            {
                out.writeObject(scores);

            } catch (Exception e) 
            {
                System.out.println("Error saving to high score file!\n" + e);
                success = false;
            }
        } while (success = false); // retry process in case of error
        
        printScoreBoard();
        
    }
    
    public void printScoreBoard()
    {
        System.out.println("  ----- HIGH SCORES -----");
        for (int i = 0; i < scores.size() - 1; i++)
        {
            System.out.printf("\n%2d) %-20s %d", i+1, scores.get(i).getName(), scores.get(i).getScore());
        }
    }
}
