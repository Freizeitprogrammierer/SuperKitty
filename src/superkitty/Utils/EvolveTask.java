package superkitty.Utils;

import superkitty.GUI.MainForm;
import superkitty.Field.AbstractGoLField;
import java.util.TimerTask;


/**
 * Task, der für das erzeugen der nächsten Generation zuständig ist.
 * 
 * @author Tobias
 */
public class EvolveTask extends TimerTask{
    // gibt an, ob der Task gerade läuft oder gestoppt ist
    private boolean running = false;
    
    private final MainForm mf = MainForm.getInstance();
    private AbstractGoLField field;
    
    /**
     * Constructor
     * @param field
     */
    public EvolveTask(AbstractGoLField field){
        super();
        this.field = field;
    }
    
    @Override
    /**
     * Die run-Funktion. Ruft die Methode zum berechnen der nächsten Generation
     * auf und zeigt die Generation an.
     */
    public void run() {
        if(running){
            field.calculateNextGeneration();
            mf.updateField();
            mf.menuGeneration.setText("Generation: " + String.valueOf(field.getGeneration()));
        }
    }
    
    /**
     * Umschalten zwischen laufend und gestoppt.
     */
    public void toggle(){
        running = ! running;
    }
    
    public void stop(){
        running = false;
    }
    
    public void start(){
        running = true;
    }
    
    public void setField(AbstractGoLField field) {
        this.field = field;
    }
}
