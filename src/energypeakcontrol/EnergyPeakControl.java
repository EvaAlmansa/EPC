/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package energypeakcontrol;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Main class of this application.
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class EnergyPeakControl {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">       
        //setNimbusLF();
        //</editor-fold>        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
        }      
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EPCFrame frame = new EPCFrame();               
                frame.setSize(1300,800);
                frame.setLocationRelativeTo(null);                  
                frame.setVisible(true);                
            }
        });
    }
    
}
