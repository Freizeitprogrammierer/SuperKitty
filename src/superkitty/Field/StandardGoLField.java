package superkitty.Field;

/**
 * Spielfeld nach Standardregeln.
 * Regelbezeichnung: 23/3
 * 
 * Weitere Informationen siehe:
 * https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens#Abweichende_Regeln
 * 
 * @author Tobias
 */
public class StandardGoLField extends AbstractGoLField{  
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
                }else if(theCell && (n<2)){
                    setNextGen(x, y, false);
                }else if(theCell && ((n==2)||(n==3))){
                    setNextGen(x, y, true);
                }else if(theCell && (n>3)){
                    setNextGen(x, y, false);
                }
            }
        }
        evolve();
    }    
}
