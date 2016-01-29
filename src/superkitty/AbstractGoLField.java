/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkitty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tobias
 */
public abstract class AbstractGoLField {
    protected int sizeX;
    protected int sizeY;
    protected boolean[][] field;
    protected boolean[][] nextGen;
    protected int generation = 0;
    protected Config config = Config.getInstance();
    protected List<Example> examples = new ArrayList<>();
    
    public abstract void calculateNextGeneration();
    
    public AbstractGoLField(){
        this.sizeX = config.getSizeX();
        this.sizeY = config.getSizeY();
        
        field = new boolean[sizeX][sizeY];
        nextGen = new boolean[sizeX][sizeY];
        loadExamples();
    }
    
    public void clearNextGen(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                setNextGen(x, y, false);
            }
        }
    }
    
    public void createRandomStartField(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                setField(x, y, (Math.random()<.3));
            }
        }
        generation = 0;
    }
    
    public void evolve(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                setField(x, y, getNextGen(x, y));
            }
        }
        generation ++;
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
        this.sizeX = sizeX;
        
        field = new boolean[sizeX][sizeY];
        nextGen = new boolean[sizeX][sizeY];
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
        this.sizeY = sizeY;
        
        field = new boolean[sizeX][sizeY];
        nextGen = new boolean[sizeX][sizeY];
    }

    /**
     * @return the field
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

    /**
     */
    public void setField(int x, int y, boolean value) {
        this.field[x][y] = value;
    }

    /**
     * @return the field
     */
    public boolean getNextGen(int x, int y) {
        return nextGen[x%getSizeX()][y%getSizeY()];
    }

    /**
     */
    public void setNextGen(int x, int y, boolean value) {
        this.nextGen[x][y] = value;
    }
    
    protected final void loadExamples(){
        File folder = new File("bsp/");
        for(int i = 0; i < folder.list().length; i++){
            if(folder.listFiles()[i].isFile()){
                Example e = new Example();
                e.loadFromFile(folder.getPath() + "\\" + folder.list()[i]);
                getExamples().add(e);
            }
        }
    }
    
    public void getExample(String name){
        Example e = null;
        
        for (Example example : getExamples()) {
            e = example;
            if(name.equals(e.getName())){
                break;
            }
        }
        if(!(e == null)){
            clearField();
            int xOffs = (sizeX - e.getSizeX())/2;
            int yOffs = (sizeY - e.getSizeY())/2;
            for(int x = 0; x<e.getSizeX(); x++){
                for(int y = 0; y<e.getSizeY(); y++){
                    field[xOffs + x][yOffs + y] = e.getField(x, y);
                }
            }
        }
    }
    
    public void clearField(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                setField(x, y, false);
            }
        }
        generation = 0;
    }

    /**
     * @return the examples
     */
    public List<Example> getExamples() {
        return examples;
    }

    /**
     * @return the generation
     */
    public int getGeneration() {
        return generation;
    }
}
