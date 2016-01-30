package superkitty;

import superkitty.GUI.MainForm;

/**
 *
 * @author Tobias
 */
public class SuperKitty {
    
    /**
     * Es wird nur das MainForm aufgerufen. Kommandozeilenparameter werden 
     * ignoriert.
     * @param args 
     */
    public static void main(String[] args) {      
        MainForm.getInstance().setVisible(true);
    }
    
}
