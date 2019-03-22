package main.LIFELINES;

import java.util.Random;
import main.Question;

public class ATA extends Lifeline
{
    @Override
    public int use(Question question) 
    {
        if (super.getUsed())
        {
            System.out.println("Ask the Audience has already been used! Select a different lifeline or answer the question.");
            return -1;
        }
        
        System.out.println("You have used the Ask the Audience lifeline!"
                + " The audience will now vote for what they think is the correct answer..."
                + "\nThe results are in! Here's what the audience voted for:");
        
        int[] percentages =  new int[4]; // 0-3 represents answer nums 1-4
        int corrAns = question.getCorrectAns()-1; 
        Random random = new Random();
        
        // give correct answer a base vote of 40%
        percentages[corrAns] = 40;
        
        int remaining = 60; // percent remaining to randomise over answers
        
        for (int i = 0; i < 4; i++)
        {
            int gen = 0;
                    
            if (remaining > 0)
            {
               gen = random.nextInt(remaining) + 1; 
            }
            
            percentages[i] += gen;
            remaining -= gen;
            
            System.out.print((i+1) + ") ");
            
            for (int k = 0; k < percentages[i]; k++)
            {
                System.out.print("-");
            }
            
            System.out.println(percentages[i] + "%");
        }
        
        System.out.println("Enter your answer now: ");

        super.setUsed();
        
        return -1; // returning -1 triggers re-scan for answer
    }
    
}
