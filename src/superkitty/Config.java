/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkitty;

import java.awt.Color;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Tobias
 */
public class Config {
    public static final String PROP_SIZEX = "PROP_SIZEX";
    public static final String PROP_SIZEY = "PROP_SIZEY";
    public static final String PROP_COLOR1 = "PROP_COLOR1";
    public static final String PROP_COLOR2 = "PROP_COLOR2";
    Properties props = new Properties();
    
    private int sizeX = 20;
    private int sizeY = 20;
    private Color color1 = Color.BLACK;
    private Color color2 = Color.WHITE;
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    
    private static Config config = new Config();
    
    public static Config getInstance(){
        return config;
    }
    
    private Config(){
        readConfig();
    }
    
    
    public final boolean readConfig(){        
        InputStream input = null;
        
        try{
            input = new FileInputStream("config.txt");
            
            props.load(input);  // Properties laden
            
            setSizeX(Integer.parseInt(props.getProperty("sizeX", "20")));
            setSizeY(Integer.parseInt(props.getProperty("sizeY", "20")));
            setColor1(Color.decode(props.getProperty("color1", "#000000")));
            setColor2(Color.decode(props.getProperty("color2", "#FFFFFF")));
            
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
    
    public boolean writeConfig(){
        OutputStream output = null;
        try{
            output = new FileOutputStream("config.txt");
            
            props.setProperty("sizeX", String.valueOf(getSizeX()));
            props.setProperty("sizeY", String.valueOf(getSizeY()));
            props.setProperty("color1", String.valueOf(getColor1().getRGB()));
            props.setProperty("color2", String.valueOf(getColor2().getRGB()));
            
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

    private boolean checkFieldSize(int newSize) {
        if (newSize < 10) {
            System.err.println("Feldgröße muss mindestens 10x10 sein");
            return false;
        }
        if (newSize > 500) {
            System.err.println("Feldgröße darf maximal 500x500 sein");
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
    
}
