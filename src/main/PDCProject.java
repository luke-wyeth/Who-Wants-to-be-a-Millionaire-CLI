package main;

import java.util.ArrayList;
import java.util.Scanner;

public class PDCProject 
{
    public static void main(String[] args) 
    {
        Game game = new Game();
        
        Question testQ = (Question) game.questions.get(0).get(0);
        

        
        Scanner scan = new Scanner(System.in);

        testQ.printQuestion();
        int response = scan.nextInt();
        
        if (response == testQ.getCorrectAns())
        {
            System.out.println("yes");
        }
        else
        {
            System.out.println("no");
        }
    }

}
