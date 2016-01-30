package superkitty.Field;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import superkitty.Utils.Config;
import superkitty.Utils.Example;

/**
 * Abstrakte Klasse für das Spielfeld. Alle Methoden (z.B. Getter und Setter), 
 * die in den Implementations gleich sind, werden hier definiert.
 * 
 * @author Tobias
 */
public abstract class AbstractGoLField {
    // Größe des Feldes, aus Config
    protected int sizeX;
    protected int sizeY;
    
    // Feld als booleans, lebende Zelle = true, tote Zelle = false
    protected boolean[][] field;
    
    // Nächste Generation
    protected boolean[][] nextGen;
    
    // Counter für aktuelle Generation
    protected int generation = 0;
    
    // Konfiguration
    protected Config config = Config.getInstance();
    
    // Liste mit Beispielen, Beispiele werden aus Dateien geladen und können 
    // selbständig erweitert werden
    protected List<Example> examples = new ArrayList<>();
    
    // Zum Bemerken, ob die Größe oder die Regeln geändert wurden.
    protected PropertyChangeListener pcl;
    
    // Funktion um nächste Generation zu berechnen. Muss in der Implementation 
    // überschrieben werden, hängt vom Regelwerk ab.
    public abstract void calculateNextGeneration();
    
    /**
     * Constructor
     */
    public AbstractGoLField(){
        this.pcl = (PropertyChangeEvent evt) -> {
            // Bei Änderung der Größe in der Config soll das Spielfeld automatisch
            // mit angepasst werden.
            if(evt.getPropertyName().equals("PROP_SIZEX")){
                setSizeX((int)evt.getNewValue());
                clearField();
            }
            if(evt.getPropertyName().equals("PROP_SIZEY")){
                setSizeY((int)evt.getNewValue());
                clearField();
            }
            // Wenn sich die Regeln ändern, wird dieses Spielfeld nicht mehr 
            // benötigt und der auch der pcl nicht mehr.
            if(evt.getPropertyName().equals("PROP_RULES")){
                config.removePropertyChangeListener(this.pcl);
            }
        };
        
        // Größe setzen
        this.sizeX = config.getSizeX();
        this.sizeY = config.getSizeY();
        
        // leeres Feld für aktuelle und nächste Generation erzeugen
        field = new boolean[sizeX][sizeY];
        nextGen = new boolean[sizeX][sizeY];
        
        // Beispiele laden
        loadExamples();
        
        // Listener für PropertyChangeSupport hinzufügen.
        config.addPropertyChangeListener("PROP_SIZEX", pcl);
        config.addPropertyChangeListener("PROP_SIZEY", pcl);
        config.addPropertyChangeListener("PROP_RULES", pcl);
    }
    
    /**
     * Feld für nächste Generation zurücksetzen
     */
    public void clearNextGen(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                setNextGen(x, y, false);
            }
        }
    }
    
    /**
     * Feld für aktuelle Generation und Counter zurücksetzen
     */
    public final void clearField(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                setField(x, y, false);
            }
        }
        generation = 0;
    }
    
    /**
     * Feld zufällig füllen (30% Füllung) und Counter zurücksetzen
     */
    public void createRandomStartField(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                setField(x, y, (Math.random()<.3));
            }
        }
        generation = 0;
    }
    
    /**
     * Aktuelle Generation mit nächste Generation ersetzen
     */
    public void evolve(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                setField(x, y, getNextGen(x, y));
            }
        }
        generation ++;
    }
    
    /**
     * Zählen der Nachbarzellen.
     * 
     * @param x
     * @param y
     * @return - Anzahl der Zellen
     */
    protected int countNeighbours(int x, int y){
        int n = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if(!((i==x)&&(j==y))){ // sich selbst nicht mitzählen
                    n += (getField(i, j)?1:0);
                }
            }
        }
        return n;
    }
    
    /**
     * Beispiele laden. Beispiele müssen im Ordner bsp liegen. Zum erstellen
     * eigener Beispiele einfach das Dateiformat beachten und im Ordner speichern.
     * Die Beispiele werden dann beim nächsten Start automatisch mit geladen.
     */
    protected final void loadExamples(){
        File folder = new File("bsp/");
        // alle Dateien im Ordner durchgehen
        for(int i = 0; i < folder.list().length; i++){
            if(folder.listFiles()[i].isFile()){
                Example e = new Example();
                e.loadFromFile(folder.getPath() + "\\" + folder.list()[i]);
                getExamples().add(e);
            }
        }
    }
    
    /**
     * Beispiel anhand des Namens auswählen und ins Spielfeld laden
     * @param name - Bezeichnung des Beispiels 
     */
    public void getExample(String name){
        Example e = null;
        
        // Beispiel auswählen
        for (Example example : getExamples()) {
            e = example;
            if(name.equals(e.getName())){
                break;
            }
        }
        
        // Beispiel laden
        if(!(e == null)){
            if((e.getSizeX()>getSizeX())||(e.getSizeY()>getSizeY())){
                JOptionPane.showMessageDialog(null, "Das Beispiel ist zu groß für "
                        + "dieses Spielfeld (mindestens " + e.getSizeX() + "x" + 
                        e.getSizeX() + "). Vergrößern Sie zuerst das Spielfeld.");
                return;
            }
            clearField();
            // Beispiel soll in der Mitte angeordnet sein.
            int xOffs = (sizeX - e.getSizeX())/2;
            int yOffs = (sizeY - e.getSizeY())/2;
            for(int x = 0; x<e.getSizeX(); x++){
                for(int y = 0; y<e.getSizeY(); y++){
                    field[xOffs + x][yOffs + y] = e.getField(x, y);
                }
            }
        }
    }
    
    // Ab hier nur noch Getter und Setter
    
    public int getSizeX() {
        return sizeX;
    }
    
    public final void setSizeX(int sizeX) {
        this.sizeX = sizeX;
        
        // Wenn sich die Größe ändert, muss auch ein neues Feld erzeugt werden.
        field = new boolean[sizeX][sizeY];
        nextGen = new boolean[sizeX][sizeY];
    }
    
    public int getSizeY() {
        return sizeY;
    }
    
    public final void setSizeY(int sizeY) {
        this.sizeY = sizeY;
        
        field = new boolean[sizeX][sizeY];
        nextGen = new boolean[sizeX][sizeY];
    }
    
    /**
     * Zelle im Feld ermitteln. x und y können auch außerhalb des Feldes liegen,
     * werden dann aber entsprechend herunter gerechner.
     * @param x
     * @param y
     * @return 
     */
    public boolean getField(int x, int y) {
        while(x<0){
            x+=getSizeX();
        }
        while(y<0){
            y+=getSizeY();
        }
        return field[x%getSizeX()][y%getSizeY()];
    }
    
    public void setField(int x, int y, boolean value) {
        this.field[x][y] = value;
    }
    
    public boolean getNextGen(int x, int y) {
        return nextGen[x%getSizeX()][y%getSizeY()];
    }
    
    public void setNextGen(int x, int y, boolean value) {
        this.nextGen[x][y] = value;
    }
    
    public List<Example> getExamples() {
        return examples;
    }
    
    public int getGeneration() {
        return generation;
    }
}
