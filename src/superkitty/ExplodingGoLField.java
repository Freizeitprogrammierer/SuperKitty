/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkitty;

/**
 *
 * @author Tobias
 */
public class ExplodingGoLField extends AbstractGoLField{  
    @Override
    public void calculateNextGeneration(){
        clearNextGen();
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                boolean theCell = getField(x, y);
                int n = countNeighbours(x, y);
                if((!theCell) && (n==3)){
                    setNextGen(x, y, true);
                    continue;
                }else if(theCell && ((n==2)||(n==3)||(n==6))){
                    setNextGen(x, y, true);
                    continue;
                }else{
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
    
}
