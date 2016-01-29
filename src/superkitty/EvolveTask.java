/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkitty;

import java.util.TimerTask;


/**
 *
 * @author Tobias
 */
public class EvolveTask extends TimerTask{
    private boolean running = false;
    private final MainForm mf = MainForm.getInstance();
    private final AbstractGoLField field;
    
    public EvolveTask(){
        super();
        field = mf.getField();
    }
    
    @Override
    public void run() {
        if(running){
            field.calculateNextGeneration();
            mf.updateField();
            mf.jMGeneration.setText("Generation: " + String.valueOf(field.getGeneration()));
        }
    }
    
    public void toggle(){
        running = ! running;
    }
    
    
}
