/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userGUI;

/**
 *Classe astratta estesa da tutte le classi a cui serve conoscere l'indirizzo del'user corrente
 * 
 * @author Alessandro
 */
public abstract class mainFrame extends javax.swing.JFrame {

    protected static String USER_EMAIL;
    /**
     * Creates new form mainFrame
     */
    public mainFrame() {
        initComponents();
    }
    
     public mainFrame(String email) {
        initComponents();
        mainFrame.USER_EMAIL = email;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
