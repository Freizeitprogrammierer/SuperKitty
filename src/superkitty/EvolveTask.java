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
    
    @Override
    public void run() {
        if(running){
            GoLField.getInstance().calculateNextGeneration();
            MainForm.getInstance().updateField();
            MainForm.getInstance().jMGeneration.setText("Generation: " + String.valueOf(GoLField.getInstance().getGeneration()));
        }
    }
    
    public void toggle(){
        running = ! running;
    }
    
    
}
