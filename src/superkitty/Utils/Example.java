package superkitty.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasse für ein Beispiel. Die Beispiele müssen im bsp/ Ordner liegen und werden
 * automatisch beim Start geladen.
 * 
 * Dateiformat:
 * 
 * Bezeichnung
 * Größe (Zeilen)x(Spalten)
 * Inhalt (lebende Zellen sind *, tote Zellen sind beliebiges anderes Zeichen)
 * 
 * @author Tobias
 */
public class Example {
    // Größe des Beispiels
    private int sizeX;
    private int sizeY;
    
    // Bezeichnung
    private String name;
    
    // Tote und lebende Zellen
    private boolean[][] field;
    
    // Pfad zur Datei
    private File file;
    
    
    /**
     * Lädt ein Beispiel aus einer Datei.
     * @param filename - Pfad zur Datei
     */
    public void loadFromFile(String filename){
        file = new File(filename);
        BufferedReader  reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            // Name einlesen
            name = reader.readLine();
            // Größe einlesen
            String size = reader.readLine();
            int pos = size.indexOf("x"); 
            sizeX = Integer.parseInt(size.substring(0, pos));
            sizeY = Integer.parseInt(size.substring(pos+1));
            // Feld erstellen
            field = new boolean[getSizeX()][getSizeY()];
            for(int i = 0; i < getSizeY(); i++){
                String line = reader.readLine();
                for(int j = 0; j < getSizeX(); j++){
                    field[j][i] = (line.charAt(j) == '*');
                }
            }
        }catch(IOException e){
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            try {
                // zum Schluss die Datei schließen
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    // Ab hier nur noch Getter und Setter
    
    public int getSizeX() {
        return sizeX;
    }
    
    public int getSizeY() {
        return sizeY;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean getField(int x, int y) {
        return field[x][y];
    }
    
    
}
