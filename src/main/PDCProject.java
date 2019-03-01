package main;

import java.util.Scanner;

public class PDCProject 
{
    public static void main(String[] args) 
    {
        String test1 = "Whats my cats name????";
        String[] test2 = new String[]{"1) goose","2) ellie","3) dog","4) shit"};
        char test3 = 'd';
        
        Scanner scan = new Scanner(System.in);
        
        Question testQuestion = new Question(test1,test2,test3);
        testQuestion.printQuestion();
        int response = scan.nextInt();
        
        if (response == 4)
        {
            System.out.println("yes");
        }
        else
        {
            System.out.println("no");
        }
    }

}
