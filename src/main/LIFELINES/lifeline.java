package main.LIFELINES;

import main.Question;

/**
 *
 * @author Luke
 */
public abstract class lifeline 
{
    // a lifeline can only be used once - 'used' is true once the lifeline has been used
    private boolean used = false;
    
    public void setUsed()
    {
        this.used = true;
    }
    
    public boolean getUsed()
    {
        return used;
    }
    
    public boolean validateAns(int ans)
    {
        boolean valid = false;
        
        if (ans == 1 || ans == 2 || ans == 3)
        {
            valid = true;
        }
        
        return valid;
    }
    
    public abstract int use(Question question); // method called when player wants to use a particular lifeline
}
