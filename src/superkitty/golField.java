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
public class golField {
    private int sizeX;
    private int sizeY;
    private boolean[][] field;
    private boolean[][] nextGen;
    
    private static golField theField = new golField(50, 50);
    
    private List<example> examples = new ArrayList<>();
    
    public static golField getInstance(){
        return theField;
    }
    
    private golField(int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
        field = new boolean[sizeX][sizeY];
        nextGen = new boolean[sizeX][sizeY];
        loadExamples();
    }
    
    public void clearField(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeX(); y++){
                setField(x, y, false);
            }
        }
    }
    
    public void clearNextGen(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeX(); y++){
                setNextGen(x, y, false);
            }
        }
    }
    
    public void createRandomStartField(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeX(); y++){
                setField(x, y, (Math.random()<.5));
            }
        }
    }
    
    public void evolve(){
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeX(); y++){
                setField(x, y, getNextGen(x, y));
            }
        }
    }
    
    public void calculateNextGeneration(){
        clearNextGen();
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeX(); y++){
                boolean theCell = getField(x, y);
                int n = countNeighbours(x, y);
                if((!theCell) && (n==3)){
                    setNextGen(x, y, true);
                    continue;
                }
                if(theCell && (n<2)){
                    setNextGen(x, y, false);
                    continue;
                }
                if(theCell && ((n==2)||(n==3))){
                    setNextGen(x, y, true);
                    continue;
                }
                if(theCell && (n>3)){
                    setNextGen(x, y, false);
                    continue;
                }
            }
        }
        evolve();
    }
    
    
    
    private int countNeighbours(int x, int y){
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
    
    // Beispiele
    
    /**
     * Gleiter
     *       *
     *        *
     *      ***
     */
    public void exampleGleiter(){
        clearField();
        example e = getExamples().get(0);
        int xOffs = (sizeX - e.getSizeX())/2;
        int yOffs = (sizeY - e.getSizeY())/2;
        for(int x = 0; x<e.getSizeX(); x++){
            for(int y = 0; y<e.getSizeY(); y++){
                field[xOffs + x][yOffs + y] = e.getField(x, y);
            }
        }
        
    }
    
    /**
     * Segler LWSS
     *       ****   
     *      *   *
     *          *
     *      *  *
     */
    public void exampleSeglerLWSS(){
        clearField();
        setField(1, 2, true);
        setField(1, 3, true);
        setField(1, 4, true);
        setField(1, 5, true);
        setField(2, 1, true);
        setField(2, 5, true);
        setField(3, 5, true);
        setField(4, 1, true);
        setField(4, 4, true);
    }
    
    private void loadExamples(){
        File folder = new File("bsp/");
        for(int i = 0; i < folder.list().length; i++){
            if(folder.listFiles()[i].isFile()){
                example e = new example();
                e.loadFromFile(folder.getPath() + "\\" + folder.list()[i]);
                getExamples().add(e);
            }
        }
    }
    
    public void getExample(String name){
        example e = null;
        
        for (example example : getExamples()) {
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

    /**
     * @return the examples
     */
    public List<example> getExamples() {
        return examples;
    }
    
}
