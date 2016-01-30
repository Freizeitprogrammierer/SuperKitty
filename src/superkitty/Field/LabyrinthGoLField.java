package superkitty.Field;

/**
 * Erzeugt eine sich ausbreitende, labyrinthartige Welt.
 * Regelbezeichnung: 12345/3
 * 
 * Weitere Informationen siehe:
 * https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens#Abweichende_Regeln
 * 
 * @author Tobias
 */
public class LabyrinthGoLField extends AbstractGoLField{  
    @Override
    /**
     * Nächste Generation berechnen.
     */
    public void calculateNextGeneration(){
        // nächste Generation zurücksetzen
        clearNextGen();
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                // ermitteln, ob aktuelle Zelle lebt
                boolean theCell = getField(x, y);
                // Nachbarzellen zählen
                int n = countNeighbours(x, y);
                // Lebens-/Sterberegeln
                if((!theCell) && (n==3)){
                    setNextGen(x, y, true);
                }else if(theCell && ((n==1)||(n==2)||(n==3)||(n==4)||(n==5))){
                    setNextGen(x, y, true);
                }else{
                    setNextGen(x, y, false);
                }
            }
        }
        evolve();
    }    
}
