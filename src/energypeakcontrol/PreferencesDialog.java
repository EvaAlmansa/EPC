
package energypeakcontrol;

/**
 * Diálogo para mostrar/modificar los parámetros globales (preferencias) de la
 * aplicación
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class PreferencesDialog extends javax.swing.JDialog {

    /**
     * Valor devuelto en caso de cancelación
     */
    public static final int CANCEL_OPTION = 1;
    /**
     * Valor devuelto en caso de aceptación
     */
    public static final int APPROVE_OPTION = 0;
    /**
     * Valor devuelto (por defecto, cancelación)
     */
    private int returnStatus = CANCEL_OPTION;
    
    public PreferencesDialog(java.awt.Frame parent) {
        super(parent, true);           //Siempre modal
        initComponents();              //Inicializacón de componentes -> NetBeans 
        inicializaGlobalSetings();     //Inicializamos variables globales
        setLocationRelativeTo(parent); //Posicionamos en centro
    }

    /**
     * Inicializa los campos del diálogo con los valores globales de la aplicación
     */
    private void inicializaGlobalSetings() {
        this.mfHighA.setValue(GlobalSettings.getHighA());
        this.mfHighB.setValue(GlobalSettings.getHighB());
        this.mfHighC.setValue(GlobalSettings.getHighC());
        this.mfHighD.setValue(GlobalSettings.getHighD());
    }
    
    /**
     * Actualiza las preferencias de la aplicación (global settings) con los 
     * valores introducidos en los campos del diálogo.Este método se llama en 
     * caso de que el usuario pulse el botón 'Aceptar"
     */
    private void actualizaGlobalSetings(){  
        float a = ((Number) this.mfHighA.getValue()).floatValue();
        float b = ((Number) this.mfHighB.getValue()).floatValue();
        float c = ((Number) this.mfHighC.getValue()).floatValue();
        float d = ((Number) this.mfHighD.getValue()).floatValue();
        GlobalSettings.setHighA(a);
        GlobalSettings.setHighB(b);
        GlobalSettings.setHighC(c);
        GlobalSettings.setHighD(d);
    }
    
    
    /**
     * Muestra este diálogo de forma modal
     * 
     * @return el estado final (aceptado o cancelado)
     */
    public int showDialog(){
        this.setVisible(true);
        return returnStatus; //Dialogo modal -> no ejecutará el return hasta que no se cierre el diálogo
    }
    
    /**
     * Cierra este diálogo, actualizando previamente el estado
     * @param retStatus el estado del diálogo (aceptado o cancelado)
     */
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    
    private void setAutoBD(){
        float a = ((Number) this.mfHighA.getValue()).floatValue();
        float d = ((Number) this.mfHighD.getValue()).floatValue();
        double b = a + (d-a)*0.25;
        double c = a + (d-a)*0.75;
        this.mfHighB.setValue(b);
        this.mfHighC.setValue(c);
    }
    
    /*
     * Código generado por Netbeans para el diseño del interfaz
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBotones = new javax.swing.JPanel();
        botonAceptar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        panelTabulado = new javax.swing.JTabbedPane();
        panelShape = new javax.swing.JPanel();
        labelGridSize = new javax.swing.JLabel();
        mfHighA = new javax.swing.JSpinner();
        mfHighD = new javax.swing.JSpinner();
        mfHighB = new javax.swing.JSpinner();
        mfHighC = new javax.swing.JSpinner();
        autoDB = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");

        panelBotones.setPreferredSize(new java.awt.Dimension(100, 30));
        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        botonAceptar.setText("OK");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });
        panelBotones.add(botonAceptar);

        botonCancelar.setText("Cancel");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });
        panelBotones.add(botonCancelar);

        getContentPane().add(panelBotones, java.awt.BorderLayout.SOUTH);

        labelGridSize.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        labelGridSize.setText("Margin: ");

        mfHighA.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(220.0f), Float.valueOf(1.0f), Float.valueOf(250.0f), Float.valueOf(1.0f)));
        mfHighA.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mfHighAStateChanged(evt);
            }
        });

        mfHighD.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(240.0f), Float.valueOf(1.0f), Float.valueOf(250.0f), Float.valueOf(1.0f)));
        mfHighD.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mfHighDStateChanged(evt);
            }
        });

        mfHighB.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(225.0f), Float.valueOf(1.0f), Float.valueOf(250.0f), Float.valueOf(1.0f)));
        mfHighB.setEnabled(false);
        mfHighB.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mfHighBStateChanged(evt);
            }
        });

        mfHighC.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(235.0f), Float.valueOf(1.0f), Float.valueOf(250.0f), Float.valueOf(1.0f)));
        mfHighC.setEnabled(false);
        mfHighC.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mfHighCStateChanged(evt);
            }
        });

        autoDB.setSelected(true);
        autoDB.setText("Auto");
        autoDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoDBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelShapeLayout = new javax.swing.GroupLayout(panelShape);
        panelShape.setLayout(panelShapeLayout);
        panelShapeLayout.setHorizontalGroup(
            panelShapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShapeLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelShapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelShapeLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(mfHighB, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(mfHighC, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(autoDB))
                    .addGroup(panelShapeLayout.createSequentialGroup()
                        .addComponent(labelGridSize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mfHighA, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(mfHighD, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        panelShapeLayout.setVerticalGroup(
            panelShapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShapeLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelShapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGridSize, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mfHighA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mfHighD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelShapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mfHighB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mfHighC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(autoDB))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        panelTabulado.addTab("Fexibility", panelShape);

        getContentPane().add(panelTabulado, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(416, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        actualizaGlobalSetings();
        doClose(APPROVE_OPTION);
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        doClose(CANCEL_OPTION);
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void mfHighAStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mfHighAStateChanged
        float a = ((Number) this.mfHighA.getValue()).floatValue();
        float b = ((Number) this.mfHighB.getValue()).floatValue();
        if(a >= b) {
            this.mfHighA.setValue(b-1);
        }  
        if(autoDB.isSelected()){
            this.setAutoBD();
        }
    }//GEN-LAST:event_mfHighAStateChanged

    private void mfHighBStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mfHighBStateChanged
        float a = ((Number) this.mfHighA.getValue()).floatValue();
        float b = ((Number) this.mfHighB.getValue()).floatValue();
        float c = ((Number) this.mfHighC.getValue()).floatValue();
        if(b >= c) {
            this.mfHighB.setValue(c-1);
        }
        if(b <= a) {
            this.mfHighB.setValue(a+1);
        }
    }//GEN-LAST:event_mfHighBStateChanged

    private void mfHighCStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mfHighCStateChanged
        float b = ((Number) this.mfHighB.getValue()).floatValue();
        float c = ((Number) this.mfHighC.getValue()).floatValue();
        float d = ((Number) this.mfHighD.getValue()).floatValue();
        if (c >= d) {
            this.mfHighC.setValue(d - 1);
        }
        if (c <= b) {
            this.mfHighC.setValue(b + 1);
        }
    }//GEN-LAST:event_mfHighCStateChanged

    private void mfHighDStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mfHighDStateChanged
        float c = ((Number) this.mfHighC.getValue()).floatValue();
        float d = ((Number) this.mfHighD.getValue()).floatValue();
        if (d <= c) {
            this.mfHighD.setValue(c + 1);
        }
        if(autoDB.isSelected()){
            this.setAutoBD();
        }
    }//GEN-LAST:event_mfHighDStateChanged

    private void autoDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoDBActionPerformed
        mfHighB.setEnabled(!autoDB.isSelected());
        mfHighC.setEnabled(!autoDB.isSelected());
    }//GEN-LAST:event_autoDBActionPerformed

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoDB;
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JLabel labelGridSize;
    private javax.swing.JSpinner mfHighA;
    private javax.swing.JSpinner mfHighB;
    private javax.swing.JSpinner mfHighC;
    private javax.swing.JSpinner mfHighD;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelShape;
    private javax.swing.JTabbedPane panelTabulado;
    // End of variables declaration//GEN-END:variables
}
