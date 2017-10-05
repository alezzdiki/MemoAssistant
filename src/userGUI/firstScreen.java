/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userGUI;

import MemoThread.ActionThread;
import MemoThread.BirthdayThread;
import MemoThread.EventThread;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import memoassistant.*;

/**
 *
 * @author Alessandro
 */
public class firstScreen extends mainFrame {

   
   private static User user;
   private myPosition myposition;
   
   /**
    * Riferimenti ai thread, necessari nel caso in cui si effettua il logout e debbano essere interrotti. 
    */
   EventThread eventThread; 
   BirthdayThread birthdayThread;
   ActionThread actionThread;
   
   
   
    /**
     * Creates new form firstScreen
     */
    public firstScreen() {
        initComponents();
        user_emailLabel.requestFocus();
        user_emailLabel.setText(null);
    }
    
    
    /**
     * Si passa al costruttore, l'oggetto user creato con la query del login. Si imposta l'attributo static USER_EMAIL
     * della spuerclasse mainFrame. Si avviano i thread
     * 
     * @param user 
     */
    public firstScreen(User user) {
        initComponents();
        firstScreen.user = user;
        user_emailLabel.requestFocus();
        user_emailLabel.setText(user.getEmail());
        super.USER_EMAIL = user.getEmail();
        myposition = new myPosition();
        eventThread = new EventThread(USER_EMAIL);
        actionThread = new ActionThread(USER_EMAIL);
        birthdayThread = new BirthdayThread(USER_EMAIL);
        eventThread.start();
        actionThread.start();
        birthdayThread.start();
    }
    

   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        circlesButton = new javax.swing.JButton();
        plannerButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        mypositionButton = new javax.swing.JButton();
        userButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        favoritesButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        user_emailLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Welcome - Memo Assistant");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        circlesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/groups-64.png"))); // NOI18N
        circlesButton.setToolTipText("Circles");
        circlesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circlesButtonActionPerformed(evt);
            }
        });
        jPanel3.add(circlesButton);

        plannerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/planner-64.png"))); // NOI18N
        plannerButton.setToolTipText("Planner");
        plannerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plannerButtonActionPerformed(evt);
            }
        });
        jPanel3.add(plannerButton);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 340, 100));

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        mypositionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/worldwide_location-64.png"))); // NOI18N
        mypositionButton.setToolTipText("Position");
        mypositionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mypositionButtonActionPerformed(evt);
            }
        });
        jPanel4.add(mypositionButton);

        userButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/user-64.png"))); // NOI18N
        userButton.setToolTipText("User");
        userButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userButtonActionPerformed(evt);
            }
        });
        jPanel4.add(userButton);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 340, 100));

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        favoritesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/star-64.png"))); // NOI18N
        favoritesButton.setToolTipText("Favorites");
        favoritesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoritesButtonActionPerformed(evt);
            }
        });
        jPanel2.add(favoritesButton);

        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/settings-64.png"))); // NOI18N
        settingsButton.setToolTipText("Settings");
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });
        jPanel2.add(settingsButton);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 340, 100));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Estrangelo Edessa", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 255));
        jLabel3.setText("Hi ,   ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        user_emailLabel.setFont(new java.awt.Font("Estrangelo Edessa", 0, 18)); // NOI18N
        user_emailLabel.setForeground(new java.awt.Color(0, 102, 255));
        user_emailLabel.setText("Alessandro");
        user_emailLabel.setToolTipText("Your name");
        jPanel1.add(user_emailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 220, -1));

        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/logout-26.png"))); // NOI18N
        logoutButton.setToolTipText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        jPanel1.add(logoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 70, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/sfondo399.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
           this.dispose();
           /*eventThread.interrupt();
           actionThread.interrupt();
           birthdayThread.interrupt();*/ // vengono omessi poichè ho inserito le sleep nel ciclo di ogni thread, per scopo didattico
           loginUser log = new loginUser();
           log.setVisible(true);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void mypositionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mypositionButtonActionPerformed

        
        //myPosition myposition = new myPosition();
        myposition.setVisible(true);
    }//GEN-LAST:event_mypositionButtonActionPerformed

    private void circlesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_circlesButtonActionPerformed
        
        circlesScreen circlesscreen = new circlesScreen();
        circlesscreen.setVisible(true);
    }//GEN-LAST:event_circlesButtonActionPerformed

    private void plannerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plannerButtonActionPerformed
        
        Planner plannerframe = new Planner();
        plannerframe.setVisible(true);
    }//GEN-LAST:event_plannerButtonActionPerformed

    private void favoritesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoritesButtonActionPerformed
        
        favoritesScreen favorites = new favoritesScreen();
        favorites.setVisible(true);
    }//GEN-LAST:event_favoritesButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        
        settings settingframe = new settings();
        settingframe.setVisible(true);
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void userButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userButtonActionPerformed
        
       if (user != null){
            newUser newuser = new newUser(user);
            newuser.setVisible(true);
            newuser.setNameButton("Apply");  
       }
       else  JOptionPane.showMessageDialog(this, "Server out of services", "Error", JOptionPane.ERROR_MESSAGE);
        

        
    }//GEN-LAST:event_userButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
       int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the application?",
                    "Exit Application", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION)
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }//GEN-LAST:event_formWindowClosing

    
    public static void updateUser(User user){
    
        firstScreen.user = user;
    }

  
       
    
       
    public static void stampaList(LinkedList<? extends DataAccount> list){
    
        Iterator<? extends DataAccount> itr = list.iterator();
        DataAccount data;
        while (itr.hasNext()){
        
            data = (DataAccount)itr.next();
            System.out.println(data.toString());
         }
    }   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(firstScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(firstScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(firstScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(firstScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new firstScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton circlesButton;
    private javax.swing.JButton favoritesButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton mypositionButton;
    private javax.swing.JButton plannerButton;
    private javax.swing.JButton settingsButton;
    private javax.swing.JButton userButton;
    private javax.swing.JLabel user_emailLabel;
    // End of variables declaration//GEN-END:variables
}
