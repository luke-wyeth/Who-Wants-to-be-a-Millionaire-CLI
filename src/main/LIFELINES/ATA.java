package main.LIFELINES;

import main.Question;

/**
 *
 * @author Luke
 */
public class ATA extends lifeline
{

    @Override
    public int use(Question question) 
    {
        if (super.getUsed())
        {
            System.out.println("This lifeline has already been used! Select a different one or answer the question.");
            return -1;
        }
        
        System.out.println("You have used the Ask the Audience lifeline!");
        
        
        super.setUsed();
        
        return -1;
    }
    
}
