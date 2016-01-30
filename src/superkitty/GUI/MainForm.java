package superkitty.GUI;

import superkitty.Field.AbstractGoLField;
import superkitty.Field.LabyrinthGoLField;
import superkitty.Field.CopyGoLField;
import superkitty.Field.StandardGoLField;
import superkitty.Field.ExplodingGoLField;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import javax.swing.JMenuItem;
import superkitty.Utils.Config;
import superkitty.Utils.EvolveTask;
import superkitty.Utils.Example;

/**
 * Das Hauptformular...
 * Als Singleton
 * 
 * @author Tobias
 */
public class MainForm extends javax.swing.JFrame {
    // Konfiguration
    private final Config config = Config.getInstance();
    
    // Panel, auf dem das Spielfeld aufgebaut wird
    private GraphicGoLField panel = null;
    
    // Timer, um das Spielfeld zu aktualisieren
    private Timer evolveTimer = new Timer();
    
    // das Spielfeld, abhängig von den Regeln wird das entsprechende erzeugt.
    private AbstractGoLField theField;
    
    // das Options-Formular
    private static final OptionsForm of = OptionsForm.getInstance();
    
    // der Task, der vom Timer aufgerufen wird
    private EvolveTask et = null;
    
    private final PropertyChangeListener pcl = (PropertyChangeEvent evt) -> {
        // Beim ändern der Spielfeldgröße muss auch die Panelgröße angepasst werden.
        if(evt.getPropertyName().equals("PROP_SIZEX") || evt.getPropertyName().equals("PROP_SIZEX")){
            this.setSize(panel.getElementSize()*theField.getSizeX()+30, panel.getElementSize()*theField.getSizeY()+80);
        }
        
        if(evt.getPropertyName().equals("PROP_SPEED")){
            if(evolveTimer!=null){
                evolveTimer.cancel();
                evolveTimer.purge();
                evolveTimer = new Timer();
                et = new EvolveTask(theField);
                evolveTimer.schedule(et, 0, 1000/config.getSpeed());
            }
        }
        
        if(evt.getPropertyName().equals("PROP_RULES")){
            createField();
            if(et!=null){
                et.setField(theField);
            }
            panel.setField(theField);
            updateField();
        }
        
        if(et!=null){
            et.stop();
        }
    };
    
    // Singleton-Objekt
    private static final MainForm mf = new MainForm();
    
    /**
     * Gibt das MainForm-Objekt zurück. Hierüber wird verhindert, dass mehrere 
     * MainForm-Objekte gleichzeitig existieren.
     * @return Singleton-Objekt
     */
    public static MainForm getInstance(){
        return mf;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        menuActions = new javax.swing.JMenu();
        btnClear = new javax.swing.JMenuItem();
        btnRandom = new javax.swing.JMenuItem();
        btnNextGen = new javax.swing.JMenuItem();
        btnAuto = new javax.swing.JMenuItem();
        menuExamples = new javax.swing.JMenu();
        menuOptions = new javax.swing.JMenu();
        btnOptions = new javax.swing.JMenuItem();
        menuGeneration = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Projekt: Kitty");

        menuActions.setText("Aktionen");

        btnClear.setText("Feld löschen");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        menuActions.add(btnClear);

        btnRandom.setText("Zufälliges Startmuster erzeugen");
        btnRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomActionPerformed(evt);
            }
        });
        menuActions.add(btnRandom);

        btnNextGen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        btnNextGen.setText("Nächste Generation");
        btnNextGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextGenActionPerformed(evt);
            }
        });
        menuActions.add(btnNextGen);

        btnAuto.setText("Automatisch Weiter");
        btnAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutoActionPerformed(evt);
            }
        });
        menuActions.add(btnAuto);

        jMenuBar1.add(menuActions);

        menuExamples.setText("Beispiele");
        jMenuBar1.add(menuExamples);

        menuOptions.setText("Optionen");

        btnOptions.setText("Optionen");
        btnOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOptionsActionPerformed(evt);
            }
        });
        menuOptions.add(btnOptions);

        jMenuBar1.add(menuOptions);

        menuGeneration.setText("Generation: 0");
        menuGeneration.setBorderPainted(true);
        menuGeneration.setDoubleBuffered(true);
        menuGeneration.setFocusable(false);
        jMenuBar1.add(menuGeneration);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 325, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        theField.clearField();
        updateField();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomActionPerformed
        theField.createRandomStartField();
        updateField();
    }//GEN-LAST:event_btnRandomActionPerformed

    private void btnNextGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextGenActionPerformed
        theField.calculateNextGeneration();
        updateField();
    }//GEN-LAST:event_btnNextGenActionPerformed

    private void btnAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutoActionPerformed
        if(et == null){
            et = new EvolveTask(theField);
            evolveTimer.schedule(et, 0, 1000/config.getSpeed());
        }
        et.toggle();
    }//GEN-LAST:event_btnAutoActionPerformed

    private void btnOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOptionsActionPerformed
        of.setVisible(true);
        of.initValues();
    }//GEN-LAST:event_btnOptionsActionPerformed
    
    /**
     * Feld neu Zeichnen
     */
    public final void updateField(){
        this.repaint();
        menuGeneration.setText("Generation: " + String.valueOf(theField.getGeneration()));
    }
    
    /**
     * Erstellt eine konkrete Implementation des Spielfeld-Objektes abhängig von
     * den gewählten Regeln.
     */
    private void createField(){
        switch(config.getRules()){
            case 0:
                theField = new StandardGoLField();
                break;
            case 1: 
                theField = new CopyGoLField();
                break;
            case 2: 
                theField = new ExplodingGoLField();
                break;
            case 3: 
                theField = new LabyrinthGoLField();
                break;
            default: 
                theField = new StandardGoLField();
                break;
        }
    }
    
    /**
     * Constructor
     */
    private MainForm() {
        initComponents();
        createField();
        
        // GraphicGoLField erzeugen und auf dem Formular platzieren
        this.setLayout(new GridLayout(1,1));
        panel = new GraphicGoLField(theField);
        this.getContentPane().add(panel);
        this.pack();
        this.setSize(panel.getElementSize()*theField.getSizeX()+30, panel.getElementSize()*theField.getSizeY()+80);

        // Für jedes Beispiel einen Button zum laden hinzufügen
        for(Example e: theField.getExamples()){
            JMenuItem item = new JMenuItem();
            item.setText(e.getName());
            item.addActionListener((java.awt.event.ActionEvent evt) -> {
                theField.getExample(evt.getActionCommand());
                updateField();
            });
            menuExamples.add(item);
        }
        
        // PropertyChangeListener hinzufügen
        config.addPropertyChangeListener("PROP_SIZEX", pcl);
        config.addPropertyChangeListener("PROP_SIZEY", pcl);
        config.addPropertyChangeListener("PROP_SPEED", pcl);
        config.addPropertyChangeListener("PROP_RULES", pcl);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnAuto;
    private javax.swing.JMenuItem btnClear;
    private javax.swing.JMenuItem btnNextGen;
    private javax.swing.JMenuItem btnOptions;
    private javax.swing.JMenuItem btnRandom;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuActions;
    private javax.swing.JMenu menuExamples;
    public javax.swing.JMenu menuGeneration;
    private javax.swing.JMenu menuOptions;
    // End of variables declaration//GEN-END:variables
}
