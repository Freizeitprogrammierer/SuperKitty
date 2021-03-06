package superkitty.Utils;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Konfiguration des Spiels. Wird aus Datei ausgelesen.
 * 
 * Nach Singleton-Designpattern. Es gibt also keinen öffentlichen Constructor.
 * Auf die Klasse wird mit getInstance() zugegriffen.
 * 
 * @author Tobias
 */
public class Config {
    // Für PropertyChangeSupport
    public static final String PROP_SIZEX = "PROP_SIZEX";       
    public static final String PROP_SIZEY = "PROP_SIZEY";
    public static final String PROP_COLOR1 = "PROP_COLOR1";
    public static final String PROP_COLOR2 = "PROP_COLOR2";
    public static final String PROP_SPEED = "PROP_SPEED";
    public static final String PROP_RULES = "PROP_RULES";
    
    // Hier werden die Properties gespeichert
    Properties props = new Properties();
    
    // Spielfeldgröße in x-Richtung und y-Richtung
    private int sizeX = 20;
    private int sizeY = 20;
    // Farbe für lebende und tote Zellen
    private Color color1 = Color.BLACK;
    private Color color2 = Color.WHITE;
    // Änderungsgeschwindigkeit
    private int speed = 2;
    // Regelwerk
    private int rules = 0;
    // ProperyChangeSupport, damit andere Komponenten Änderungen mitbekommen.
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    
    // Singleton-Objekt
    private static final Config config = new Config();
    
    /**
     * Gibt das Config-Objekt zurück. Hierüber wird verhindert, dass mehrere 
     * Config-Objekte gleichzeitig existieren.
     * @return Singleton-Objekt
     */
    public static Config getInstance(){
        return config;
    }
    
    /**
     * Construktor auf private gesetzt, damit keine zusätzlichen Config-Objekte
     * erzeugt werden können.
     */
    private Config(){
        readConfig();
    }
    
    /**
     * Lesen der Konfiguration aus der Datei "config.txt" im Projektordner. 
     * Falls mit der Datei etwas nicht stimmt (falsch formatiert, nicht 
     * vorhanden oder sonst nicht lesbar), wird false zurück gegeben, ansonsten 
     * true. Wird beim erzeugen aufgerufen.
     * @return 
     */
    public final boolean readConfig(){        
        InputStream input = null;
        
        try{
            input = new FileInputStream("config.txt");
            
            props.load(input);  // Properties laden
            
            setSizeX(Integer.parseInt(props.getProperty("sizeX", "20")));
            setSizeY(Integer.parseInt(props.getProperty("sizeY", "20")));
            setColor1(Color.decode(props.getProperty("color1", "#000000")));
            setColor2(Color.decode(props.getProperty("color2", "#FFFFFF")));
            setSpeed(Integer.parseInt(props.getProperty("speed", "2")));
            setRules(Integer.parseInt(props.getProperty("rules", "0")));
            
            return true;
        }catch(FileNotFoundException E){
            System.out.println("Config-Datei nicht gefunden.");
            System.err.println(E.getMessage());
            return false;
        }catch(IOException E){
            System.out.println("Config-Datei konnte nicht gelesen werden.");
            System.err.println(E.getMessage());
            return false;
        }catch(NumberFormatException E){
            System.out.println("Config-Datei fehlerhaft.");
            System.err.println(E.getMessage());
            return false;
        }
    }  
    
    /**
     * Schreiben der Konfiguration in die Datei "config.txt" im Projektordner. 
     * Falls die Datei nicht geschrieben werden kann, wird false zurück gegeben,
     * ansonsten true.
     * @return 
     */
    public boolean writeConfig(){
        OutputStream output = null;
        try{
            output = new FileOutputStream("config.txt");
            
            props.setProperty("sizeX", String.valueOf(getSizeX()));
            props.setProperty("sizeY", String.valueOf(getSizeY()));
            props.setProperty("color1", String.valueOf(getColor1().getRGB()));
            props.setProperty("color2", String.valueOf(getColor2().getRGB()));
            props.setProperty("speed", String.valueOf(getSpeed()));
            props.setProperty("rules", String.valueOf(getRules()));
            
            props.store(output, null);
            return true;
        }catch(FileNotFoundException E){
            System.out.println("Config-Datei nicht gefunden.");
            System.err.println(E.getMessage());
            return false;
        }catch(IOException E){
            System.out.println("Config-Datei konnte nicht gelesen werden.");
            System.err.println(E.getMessage());
            return false;
        }
    }

    /**
     * @return the sizeX
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * @param sizeX the sizeX to set
     */
    public void setSizeX(int sizeX) {
        if(!checkFieldSize(sizeX))return;
        
        int oldSizeX = this.sizeX;
        this.sizeX = sizeX;
        propertyChangeSupport.firePropertyChange(PROP_SIZEX, oldSizeX, sizeX);
    }

    /**
     * @return the sizeY
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * @param sizeY the sizeY to set
     */
    public void setSizeY(int sizeY) {
        if(!checkFieldSize(sizeY))return;
        int oldSizeY = this.sizeY;
        this.sizeY = sizeY;
        propertyChangeSupport.firePropertyChange(PROP_SIZEY, oldSizeY, sizeY);
    }
    
    /**
     * Überprüft, ob die Feldgröße gültig ist (10...200).
     * @param newSize
     * @return 
     */
    private boolean checkFieldSize(int newSize) {
        if (newSize < 10) {
            System.err.println("Feldgröße muss mindestens 10x10 sein");
            return false;
        }
        if (newSize > 200) {
            System.err.println("Feldgröße darf maximal 200x200 sein");
            return false;
        }
        return true;
    }

    /**
     * @return the color1
     */
    public Color getColor1() {
        return color1;
    }

    /**
     * @param color1 the color1 to set
     */
    public void setColor1(Color color1) {
        java.awt.Color oldColor1 = this.color1;
        this.color1 = color1;
        propertyChangeSupport.firePropertyChange(PROP_COLOR1, oldColor1, color1);
    }

    /**
     * @return the color2
     */
    public Color getColor2() {
        return color2;
    }

    /**
     * @param color2 the color2 to set
     */
    public void setColor2(Color color2) {
        java.awt.Color oldColor2 = this.color2;
        this.color2 = color2;
        propertyChangeSupport.firePropertyChange(PROP_COLOR2, oldColor2, color2);
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        if(speed < 1){
            return;
        }
        int oldSpeed = this.speed;
        this.speed = speed;
        propertyChangeSupport.firePropertyChange(PROP_SPEED, oldSpeed, speed);
    }

    /**
     * @return the rules
     */
    public int getRules() {
        return rules;
    }

    /**
     * @param rules the rules to set
     */
    public void setRules(int rules) {
        int oldRules = this.rules;
        this.rules = rules;
        propertyChangeSupport.firePropertyChange(PROP_RULES, oldRules, rules);
    }
    
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener pcl){
        propertyChangeSupport.addPropertyChangeListener(propertyName, pcl);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pcl){
        propertyChangeSupport.removePropertyChangeListener(pcl);
    }
    
    
}
