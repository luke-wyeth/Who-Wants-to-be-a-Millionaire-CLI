package main.LIFELINES;

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
    
    public abstract void use(); // method called when player wants to use a particular lifeline
}
