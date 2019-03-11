
package main;

import java.util.ArrayList;

/**
 *
 * @author lukewyeth
 */
public class createQuestions 
{
    public createQuestions()
    {}
    
    //used to generate the list objects to save to file
    //Question q = new Question( ,"",new String[]{"","","",""}, );
    public ArrayList create()
    {
        ArrayList<Question> level0 = new ArrayList();
        ArrayList<Question> level1 = new ArrayList();
        ArrayList<Question> level2 = new ArrayList();
        
        
        Question q0 = new Question(0,"How many Shrek movies are there in total?",new String[]{"2","4","5","6"},2);
        Question q1 = new Question(0,"Which is the closest star to the Earth?",new String[]{"The Sun","Mars","Alpha Centauri A","Alpha Centauri B"},1);
        Question q2 = new Question(0,"What animal was Flipper from the popular 1964 children's show 'Flipper'?",new String[]{"Whale","Seal","Dog","Dolphin"},4);
        Question q3 = new Question(0,"What are the names of the two main characters from the cartoon 'Gravity Falls'?",new String[]{"MaryAnn and Dylan","Megan and Dylan","Mabel and Skipper","Mabel and Dipper"},4);
        Question q4 = new Question(0,"Which of these is not a book of the Bible?",new String[]{"Kings","Judges","Numbers","Letters"},4);
        Question q5 = new Question(0,"What part of a chicken is commonly called the drumstick?",new String[]{"Breast","Knee","Leg","Ankle"},3);
        Question q6 = new Question(0,"The British clay animation series featuring a man and his dog is called Wallace and who?",new String[]{"Gromit","Timmy","Wayne","Thomas"},1);
        Question q7 = new Question(0,"What sound does a goose make?",new String[]{"Chirp","Honk","Beep","Snort"},2);
        
        level0.add(q0);
        level0.add(q1);
        level0.add(q2);
        level0.add(q3);
        level0.add(q4);
        level0.add(q5);
        level0.add(q6);
        level0.add(q7);
        
        Question q8 = new Question(1,"In 'SpongeBob SquarePants,' what was the name of the Krusty Krab before Mr. Krabs bought it?",new String[]{"The Busty Dab","The Chum Bucket","The Rusty Krab","The Lusty Cab"},3);
        Question q9 = new Question(1,"What year was the game 'Nintendogs' released for the Nintendo DS console?",new String[]{"2004","2005","2006","2007"},2);
        Question q10 = new Question(1,"What was the most popular baby boy name in the year 1999?",new String[]{"Jacob","Luke","Nicholas","Matthew"},1);
        Question q11 = new Question(1,"Who first figured out that the Earth is round?",new String[]{"Julius Caesar","Abel Tasman","Aristotle","Pythagoras"},4);
        Question q12 = new Question(1,"Which is the most popularly used programming language in 2019?",new String[]{"Java","Python","C++","JavaScript"},1);
        Question q13 = new Question(1,"In the context of computer displays, what does LCD stand for?",new String[]{"Light Colour Display","Liquid Crystal Display","Liquid Colour Display","Light Cathode Display"},2);
        Question q14 = new Question(1,"Which one of these characters does not have a star in the Hollywood Walk of Fame?",new String[]{"Mickey Mouse","Minnie Mouse","SpongeBob SquarePants","Shrek"},3);
        Question q15 = new Question(1,"What is the most visible colour in the dark for humans?",new String[]{"Red","White","Yellow","Green"},3);
        Question q16 = new Question(1,"Which character does Asa Butterfield play in the Netflix original show 'Sex Education'",new String[]{"Jackson","Eric","Jakob","Otis"},4);
        
        level1.add(q8);
        level1.add(q9);
        level1.add(q10);
        level1.add(q11);
        level1.add(q12);
        level1.add(q13);
        level1.add(q14);
        level1.add(q15);
        level1.add(q16);
        
        Question q17 = new Question(2,"What year was the 'Burger King' restaurant chain founded?",new String[]{"1654","1927","1948","1999"},1);
        Question q18 = new Question(2,"Which type of insect shorted out a supercomputer and inspired the term 'computer bug'?",new String[]{"Ant","Moth","Cockroach","Cicada"},2);
        Question q19 = new Question(2,"Which of the following car manufacturers competed in Google's 'Lunar XPRIZE,' a competition to land on the moon?",new String[]{"Suzuki","Ford","Volkswagen","Mercedes"},1);
        Question q20 = new Question(2,"Which was the first high-level programming language?",new String[]{"C","COBOL","BASIC","Fortran"},4);
        Question q21 = new Question(2,"What was the highest grossing movie of the year 2000?",new String[]{"Inception","Iron Man 1","Toy Story 2","Shrek Forever After"},3);
        Question q22 = new Question(2,"What is the highest grossing horror film ever?",new String[]{"It","The Exorcist","The Conjuring 1","The Grudge"},1 );
        Question q23 = new Question(2,"The lowest grossing movie ever, 'ZYZZYX Road,' made how much money at the box office?",new String[]{"$0","$30","$225","$12,400"},2);
        
        level2.add(q17);
        level2.add(q18);
        level2.add(q19);
        level2.add(q20);
        level2.add(q21);
        level2.add(q22);
        level2.add(q23);
        
        ArrayList lists = new ArrayList<>();
        lists.add(level0);
        lists.add(level1);
        lists.add(level2);
        
        return lists;
    }
    
    
   
}
