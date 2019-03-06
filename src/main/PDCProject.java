package main;

import java.util.ArrayList;
import java.util.Scanner;

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
