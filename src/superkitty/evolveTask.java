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
public class evolveTask extends TimerTask{
    private boolean running = false;
    
    @Override
    public void run() {
        if(running){
            golField.getInstance().calculateNextGeneration();
            MainForm.getInstance().updateField();
        }
    }
    
    public void toggle(){
        running = ! running;
    }
    
    
}
