package main;

import java.util.ArrayList;
import main.LIFELINES.ATA;
import main.LIFELINES.PAF;
import main.LIFELINES.fiftyFifty;
import main.LIFELINES.lifeline;

public class Game
{
    public ArrayList<ArrayList> questions;
    public lifeline ATA;
    public lifeline PAF;
    public lifeline fiftyFifty;
    
    public Game()
    {
        createQuestions setup = new createQuestions();
        questions = setup.create(); 
        
        // setup lifelines
        this.ATA = new ATA();
        this.PAF = new PAF();
        this.fiftyFifty = new fiftyFifty();
    }
}
