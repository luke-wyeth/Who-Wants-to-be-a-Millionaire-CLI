package main.LIFELINES;

import java.util.Random;
import main.Question;

/**
 *
 * @author Luke
 */
public class FiftyFifty extends Lifeline
{
    @Override
    public int use(Question question) 
    {
        if (super.getUsed())
        {
            System.out.println("50/50 has already been used! Select a different lifeline or answer the question.");
            return -1;
        }
        
        Random rand = new Random();
        System.out.println("You have used the 50/50 lifeline! 2 incorrect answers have been removed.");
        
        int remove1 = -1;
        int remove2 = -1;
        
        boolean valid1 = false;
        boolean valid2 = false;
        
        while (valid1 == false)
        {
            remove1 = rand.nextInt(4);
            if ((remove1 + 1) != question.getCorrectAns())
            {
                valid1 = true;
            }
        }
        
        while (valid2 == false)
        {
            remove2 = rand.nextInt(4);
            if ((remove2 + 1) != question.getCorrectAns() && remove2 != remove1)
            {
                valid2 = true;
            }
        }
        
        question.setAnswers(remove1, " ");
        question.setAnswers(remove2, " ");
        
        question.printQuestion();
        
        System.out.println("Enter your answer now: ");

        super.setUsed();
        return -1; // returning -1 triggers re-scan for answer
    }
    
}
