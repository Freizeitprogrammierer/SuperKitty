/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkitty.GUI;

import superkitty.Field.AbstractGoLField;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import superkitty.Utils.Config;

/**
 *
 * @author Tobias
 */
public class GraphicGoLField extends JPanel{
    private AbstractGoLField field = null;
    private final Config config = Config.getInstance();
    private final int elementSize = 5;
    
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
     * @param field the field to set
     */
    public void setField(AbstractGoLField field) {
        this.field = field;
    }
}
