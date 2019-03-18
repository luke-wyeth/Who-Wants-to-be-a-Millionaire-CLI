package main;

import java.util.*;

public class PDCProject
{

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to Who Wants to be a Millionaire!"
                + "\nThe controls are:"
                + "\nNumbers 1-4: answer questions! Type "
                + "the number of the question you think is correct, then press enter."
                + "\n8: 50/50 lifeline. Use this lifeline to remove two incorrect answers."
                + "\n9: Phone a Friend (PaF). Use this lifeline to call a friend for help!"
                + "\n0: Ask the Audience (AtA). Use this lifeline to get the audience to help."
                + "\n6: You can enter the number 6 to walk away with your current winnings at any time during the game."
                + "\nAre you ready to begin? Enter Y to continue, S to view the high score board, or Q to quit!");

        char scanned = scan.next().toUpperCase().charAt(0);
        char playing = ' '; 
        
        do
        {
            if (scanned == 'Y') // start game
            {
                Game game = new Game();

                while (game.isPlaying())
                {
                    game.askQuestion();
                }
                
                System.out.println("\nPlay again? (Y/N)");
                playing = scan.next().toUpperCase().charAt(0);
            }
            else if (scanned == 'S')
            {
                Game game = new Game();
                game.printScoreBoard();
                
                System.out.println("\n\nPlay Who Wants to be a Millionaire? (Y/N)");
                playing = scan.next().toUpperCase().charAt(0);
                scanned = playing; // start fresh game if user selects to play after viewing scores
            }
            
        } while (playing == 'Y');
    }
}
