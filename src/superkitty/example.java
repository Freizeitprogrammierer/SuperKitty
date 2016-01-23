/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkitty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tobias
 */
public class example {
    private int sizeX;
    private int sizeY;
    private String name;
    private boolean[][] field;
    private File file;
    
    public void loadFromFile(String filename){
        file = new File(filename);
        BufferedReader  reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            name = reader.readLine();
            String size = reader.readLine();
            int pos = size.indexOf("x");
            sizeX = Integer.parseInt(size.substring(0, pos));
            sizeY = Integer.parseInt(size.substring(pos+1));
            
            field = new boolean[getSizeX()][getSizeY()];
            for(int i = 0; i < getSizeY(); i++){
                String line = reader.readLine();
                for(int j = 0; j < getSizeX(); j++){
                    field[j][i] = (line.charAt(j) == '*');
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(example.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the sizeX
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * @return the sizeY
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the field
     */
    public boolean getField(int x, int y) {
        return field[x][y];
    }
    
    
}
