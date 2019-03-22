package main.LIFELINES;

import java.util.ArrayList;
import java.util.Random;
import main.Question;

/**
 *
 * @author Luke
 */
public class PAF extends Lifeline
{
    @Override
    public int use(Question question) 
    {
       if (super.getUsed())
       {
           System.out.println("Phone a Friend has already been used! Select a different lifeline or answer the question.");
           return -1;
       }
       
       Random rand = new Random();
       
       ArrayList<String> responses = new ArrayList();
       responses.add("I'm pretty sure the answer is number ");
       responses.add("I don't know! If I had to guess, I'd say number ");
       responses.add("Uhhhh.... probably number ");
       responses.add("I know the answer! It's number ");
       responses.add("Why did you call me??? We're not even friends. The answer is number ");
       responses.add("I think the answer is number ");
       
       // randomly select one of the responses from the list
       String reply = responses.get(rand.nextInt(responses.size()));
       
       // the answer the "friend" will give
       int answer = -1;
       
       // chance of giving correct answer is 70%
       // 1-7 = correct answer, 8-10 = random false answer
       int correct = rand.nextInt(10);
       
       if (correct < 7)
       {
           answer = question.getCorrectAns();
       }
       else
       {
           while (answer == -1) // generate random incorrect answer
           {
               answer = rand.nextInt(4) + 1; // adjust for 0-3 != 1-4
               
               if (answer == question.getCorrectAns()) // confirm is not correct
               {
                   answer = -1;
               }
           }
       }
       
       reply = reply + answer;
       
        System.out.println("Your friend says...");
       System.out.println(reply);
       
       System.out.println("Enter your answer now: ");
       
       super.setUsed();
       return -1; // returning -1 triggers re-scan for answer
    }
    
}
