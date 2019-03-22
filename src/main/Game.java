package main;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import main.LIFELINES.*;

public class Game
{
    public ArrayList<ArrayList> questions;
    private final Lifeline ATA; // ask the audience
    private final Lifeline PAF; // phone a friend
    private final Lifeline fiftyFifty; // 50:50 lifeline
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
        questions = createQuestions(); 
        
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
        fiftyFifty = new FiftyFifty();
        
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
        boolean file;

        file = scoreFile.exists();

            // file exists so we know there are scores to read in
            if (file)
            {
                try(ObjectInputStream in = new ObjectInputStream(new ObjectInputStream(new FileInputStream("scores")))) 
                {
                    scores = (ArrayList<HighScore>)in.readObject();

                } catch (IOException | ClassNotFoundException e)
                {
                    // TODO: fix this scanning empty file
                    System.err.println(e);
                }
            }

    }
    
    // save scores + new score to file 
    private void saveScore()
    {
        File scoreFile = new File("scores");
        try
        { 
            // try to create new file in case this is the first time the game has been played
            scoreFile.createNewFile(); 

        } catch (IOException ex)
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        scores.add(finalScore); // add users score to list
        Collections.sort(scores); // sort low to high
        Collections.reverse(scores); // reverse to high to low
        
        // if adding new score will make list > 15, remove lowest score to 
        // keep score list 15 scores maximum
        if (scores.size() > 16) 
        {
            scores.remove(16);
        }
        
        boolean success;

        do
        {
            try(ObjectOutputStream out = new ObjectOutputStream(new ObjectOutputStream(new FileOutputStream("scores")))) 
            {
                success = true;
                out.writeObject(scores);

            } catch (Exception e) 
            {
                System.out.println("Error saving to high score file!\n" + e);
                success = false;
            }
        } while (!success); // retry process in case of error
        
        printScoreBoard();
        
    }
    
    public void printScoreBoard()
    {
        System.out.println("  ----- HIGH SCORES -----");
        for (int i = 0; i < scores.size(); i++)
        {
            System.out.printf("\n%2d) %-20s %d", i+1, scores.get(i).getName(), scores.get(i).getScore());
        }
    }
    
    // create question lists
    public ArrayList createQuestions()
    {
        ArrayList<Question> level0 = new ArrayList();
        ArrayList<Question> level1 = new ArrayList();
        ArrayList<Question> level2 = new ArrayList();
        
        
        Question q0 = new Question(0,"How many Shrek movies are there in total?",new String[]{"2","4","5","6"},2);
        Question q1 = new Question(0,"Which is the closest star to the Earth?",new String[]{"The Sun","Mars","Alpha Centauri A","Alpha Centauri B"},1);
        Question q2 = new Question(0,"What animal was Flipper from the popular 1964 children's show 'Flipper'?",new String[]{"Whale","Seal","Dog","Dolphin"},4);
        Question q3 = new Question(0,"What are the names of the two main characters from the cartoon 'Gravity Falls'?",new String[]{"MaryAnn and Dylan","Megan and Dylan","Mabel and Skipper","Mabel and Dipper"},4);
        Question q4 = new Question(0,"Which of these is not a book of the Bible?",new String[]{"Kings","Judges","Numbers","Letters"},4);
        Question q5 = new Question(0,"What part of a chicken is commonly called the drumstick?",new String[]{"Breast","Knee","Leg","Ankle"},3);
        Question q6 = new Question(0,"The British clay animation series featuring a man and his dog is called Wallace and who?",new String[]{"Gromit","Timmy","Wayne","Thomas"},1);
        Question q7 = new Question(0,"What sound does a goose make?",new String[]{"Chirp","Honk","Beep","Snort"},2);
        
        level0.add(q0);
        level0.add(q1);
        level0.add(q2);
        level0.add(q3);
        level0.add(q4);
        level0.add(q5);
        level0.add(q6);
        level0.add(q7);
        
        Question q8 = new Question(1,"In 'SpongeBob SquarePants,' what was the name of the Krusty Krab before Mr. Krabs bought it?",new String[]{"The Busty Dab","The Chum Bucket","The Rusty Krab","The Lusty Cab"},3);
        Question q9 = new Question(1,"What year was the game 'Nintendogs' released for the Nintendo DS console?",new String[]{"2004","2005","2006","2007"},2);
        Question q10 = new Question(1,"What was the most popular baby boy name in the year 1999?",new String[]{"Jacob","Luke","Nicholas","Matthew"},1);
        Question q11 = new Question(1,"Who first figured out that the Earth is round?",new String[]{"Julius Caesar","Abel Tasman","Aristotle","Pythagoras"},4);
        Question q12 = new Question(1,"Which is the most popularly used programming language in 2019?",new String[]{"Java","Python","C++","JavaScript"},1);
        Question q13 = new Question(1,"In the context of computer displays, what does LCD stand for?",new String[]{"Light Colour Display","Liquid Crystal Display","Liquid Colour Display","Light Cathode Display"},2);
        Question q14 = new Question(1,"Which one of these characters does not have a star in the Hollywood Walk of Fame?",new String[]{"Mickey Mouse","Minnie Mouse","SpongeBob SquarePants","Shrek"},3);
        Question q15 = new Question(1,"What is the most visible colour in the dark for humans?",new String[]{"Red","White","Yellow","Green"},3);
        Question q16 = new Question(1,"Which character does Asa Butterfield play in the Netflix original show 'Sex Education'",new String[]{"Jackson","Eric","Jakob","Otis"},4);
        
        level1.add(q8);
        level1.add(q9);
        level1.add(q10);
        level1.add(q11);
        level1.add(q12);
        level1.add(q13);
        level1.add(q14);
        level1.add(q15);
        level1.add(q16);
        
        Question q17 = new Question(2,"What year was the 'Burger King' restaurant chain founded?",new String[]{"1654","1927","1948","1999"},1);
        Question q18 = new Question(2,"Which type of insect shorted out a supercomputer and inspired the term 'computer bug'?",new String[]{"Ant","Moth","Cockroach","Cicada"},2);
        Question q19 = new Question(2,"Which of the following car manufacturers competed in Google's 'Lunar XPRIZE,' a competition to land on the moon?",new String[]{"Suzuki","Ford","Volkswagen","Mercedes"},1);
        Question q20 = new Question(2,"Which was the first high-level programming language?",new String[]{"C","COBOL","BASIC","Fortran"},4);
        Question q21 = new Question(2,"What was the highest grossing movie of the year 2000?",new String[]{"Inception","Iron Man 1","Toy Story 2","Shrek Forever After"},3);
        Question q22 = new Question(2,"What is the highest grossing horror film ever?",new String[]{"It","The Exorcist","The Conjuring 1","The Grudge"},1 );
        Question q23 = new Question(2,"The lowest grossing movie ever, 'ZYZZYX Road,' made how much money at the box office?",new String[]{"$0","$30","$225","$12,400"},2);
        
        level2.add(q17);
        level2.add(q18);
        level2.add(q19);
        level2.add(q20);
        level2.add(q21);
        level2.add(q22);
        level2.add(q23);
        
        ArrayList lists = new ArrayList<>();
        lists.add(level0);
        lists.add(level1);
        lists.add(level2);
        
        return lists;
    }
}
