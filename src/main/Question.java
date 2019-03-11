
package main;

public class Question
{ 
    // private and final to avoid tampering by other classes
    private final String question;
    private final String[] answers;
    private final int correctAns;
    private final int level; 
    
    public Question(int level, String question, String[] answers, int correctAns)
    {
        this.level = level;
        this.question = question;
        this.answers = answers;
        this.correctAns = correctAns;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public String getQuestion()
    {
        return question;
    }
    
    public String[] getAnswers()
    {
        return answers;
    }
    
    // 50/50 lifeline uses this class to remove 2 answers
    public void setAnswers(int index, String newAns) 
    {
        answers[index] = newAns;
    }
    
    public int getCorrectAns()
    {
        return correctAns;
    }
    
    public void printQuestion()
    {
        System.out.println(question);
        
        for (int count = 0; count < 4; count++)
        {
            System.out.println((count+1) + ") " + answers[count]);
        }
    }
}
