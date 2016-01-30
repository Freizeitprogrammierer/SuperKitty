package superkitty.Field;

/**
 * Kopierwelt, nach 4 Generationen (oder Vielfachen) entsteht das Muster erneut.
 * Regelbezeichnung: 1357/1357
 * 
 * Weitere Informationen siehe:
 * https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens#Abweichende_Regeln
 * 
 * @author Tobias
 */
public class CopyGoLField extends AbstractGoLField{  
    
    @Override
    /**
     * Nächste Generation berechnen.
     */
    public void calculateNextGeneration(){
        // nächste Generation zurücksetzen
        clearNextGen();
        for(int x = 0; x < getSizeX(); x++){
            for(int y = 0; y < getSizeY(); y++){
                // Nachbarzellen zählen
                int n = countNeighbours(x, y);
                // Lebens-/Sterberegeln
                if((n==0)||(n==2)||(n==4)||(n==6)||(n==8)){
                    setNextGen(x, y, false);
                }else{
                    setNextGen(x, y, true);
                }
            }
        }
        evolve();
    }
}
