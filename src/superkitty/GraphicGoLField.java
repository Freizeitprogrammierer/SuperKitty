/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkitty;

import java.awt.Graphics;
import java.beans.PropertyChangeSupport;
import javax.swing.JPanel;

/**
 *
 * @author Tobias
 */
public class GraphicGoLField extends JPanel{
    public static final String PROP_ELEMENTSIZE = "PROP_ELEMENTSIZE";
    private AbstractGoLField field = null;
    private final Config config = Config.getInstance();
    private int elementSize = 5;
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    
    public GraphicGoLField(AbstractGoLField field){
        super();
        this.field = field;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i < field.getSizeX(); i++){
            for(int j = 0; j < field.getSizeY(); j++){
                g.setColor((field.getField(i, j)?config.getColor1():config.getColor2()));
                g.fillRect(i*getElementSize(), j*getElementSize(), getElementSize(), getElementSize());
            }
        }
        
    }

    /**
     * @return the elementSize
     */
    public int getElementSize() {
        return elementSize;
    }

    /**
     * @param elementSize the elementSize to set
     */
    public void setElementSize(int elementSize) {
        int oldElementSize = this.elementSize;
        this.elementSize = elementSize;
        propertyChangeSupport.firePropertyChange(PROP_ELEMENTSIZE, oldElementSize, elementSize);
    }
}
