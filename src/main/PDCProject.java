package main;


public class PDCProject 
{
    public static void main(String[] args) 
    {
        Game game = new Game();
        
        while (game.isPlaying())
        {
            game.askQuestion();
        }
        
    }

}
