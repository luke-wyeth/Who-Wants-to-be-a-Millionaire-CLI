package main;

import java.util.Scanner;

public class PDCProject
{

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        char play;

        do
        {
            System.out.println("Welcome to Who Wants to be a Millionaire!"
                    + "\nThe controls are:"
                    + "\nNumbers 1-4: answer questions! Type "
                    + "the number of the question you think is correct, then press enter."
                    + "\n8: 50/50 lifeline. Use this lifeline to remove two incorrect answers."
                    + "\n9: Phone a Friend (PaF). Use this lifeline to call a friend for help!"
                    + "\n0: Ask the Audience (AtA). Use this lifeline to get the audience to help."
                    + "\n6: You can enter the number 6 to walk away with your current winnings at any time during the game."
                    + "\nAre you ready to begin? Enter Y to continue, or Q to quit!");

            if (scan.next().toUpperCase().charAt(0) == 'Y') // start game
            {
                Game game = new Game();

                while (game.isPlaying())
                {
                    game.askQuestion();
                }
            }

            System.out.println("\n\nPlay again? (y/n)");
            play = scan.next().toUpperCase().charAt(0);
            
        } while (play == 'Y');

    }
}
