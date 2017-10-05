/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userGUI;

import client_server.Client;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JTextField;
import memoassistant.Friend;

/**
 *
 * @author Alessandro
 */
public class newPerson extends mainFrame {

    
    private int idFriend;
    private byte alertBirthday;

    /**
     * Creates new form newPerson
     */
    public newPerson() {
        initComponents();
        
    }

     /**
      * carica il friend da modificare nella schermata
      * @param friend l'oggetto friend passato 
      */
      public newPerson(Friend friend) {
        initComponents();
        idFriend = friend.getIdfriend();
        alertBirthday = friend.getAlertBirthday();
        loadFriend(friend);
    }
      
      public boolean createFriend(){
        try {
            Friend friend = new Friend();
            friend.setName(NameTF.getText());
            friend.setSurname(SurnameTF.getText());
            String dateString = ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).getText();
            try {
                friend.setDate(dateManager(dateString));
            } catch (ParseException ex) {
                System.err.println("Error date format");
            }
            friend.setInteressi(interestsManager());
            System.out.println(friend.getInteressi());
            friend.setCategory( (String) categoryCB.getSelectedItem());
            
            if (!validInput()){
                JOptionPane.showMessageDialog(this, "All fields are required, please", "Error invalid input", ERROR_MESSAGE);
                return false;
            }
            
            LinkedList<Friend> list = new LinkedList<>();
            list.add(friend);
            
            
            Client client = new Client();
            client.send(list, "New", USER_EMAIL);
            client.closeSocket();
            
            return true;
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            return false;        
        }
    }
    
    public boolean updateFriend() {
        try {
            Friend friend = new Friend();
            friend.setIdfriend(idFriend);
            friend.setAlertBirthday(alertBirthday);
            friend.setName(NameTF.getText());
            friend.setSurname(SurnameTF.getText());
            friend.setCategory((String) categoryCB.getSelectedItem());
            friend.setInteressi(interestsManager());
            
            String dateString = ((JTextField) jDateChooser1.getDateEditor().getUiComponent()).getText();
            System.out.println(dateString);
            try {
                friend.setDate(dateManager(dateString));
            } catch (ParseException ex) {
                System.err.println("Error date format");
            }
            
            if (!validInput()) {
                JOptionPane.showMessageDialog(this, "All fields are required, please", "Error invalid input", ERROR_MESSAGE);
                return false;
            }
            
            LinkedList<Friend> list = new LinkedList<>();
            list.add(friend);
            
            Client client = new Client();
            client.send(list, "Update", null);
            client.closeSocket();

            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            return false;        
        }
    }
    
    /**
     * cambia il tipo ed il formato della data selezionata tramite jdatechooser
     * @param dateString la data inserita nel jdatechooser
     * @return date in formato e tipo sql
     * @throws ParseException 
     */
    
    public java.sql.Date dateManager(String dateString) throws ParseException  {
    
        final String OLD_FORMAT = "dd-MM-yyyy";
        final String NEW_FORMAT = "yyyy-MM-dd";
        
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        java.util.Date newDate = sdf.parse(dateString);
        sdf.applyPattern(NEW_FORMAT);
        
        java.sql.Date sqldDate = new java.sql.Date(newDate.getTime());   
        return sqldDate;
    }
    
    /**
     * Trasforma le selezioni dei checkbox in una stringa
     * 
     * @return gli interessi da salvare sul database
     */
    public String interestsManager(){
    
        String interests = "";
        if (jCheckBox1.isSelected()) interests += jCheckBox1.getText() + " ";
        if (jCheckBox2.isSelected()) interests += jCheckBox2.getText() + " ";
        if (jCheckBox3.isSelected()) interests += jCheckBox3.getText() + " ";
        if (jCheckBox4.isSelected()) interests += jCheckBox4.getText() + " ";
        if (jCheckBox5.isSelected()) interests += jCheckBox5.getText() + " ";
        if (jCheckBox6.isSelected()) interests += jCheckBox6.getText() + " ";
        
        return interests;
    }
    
    /**
     * Trasforma la stringa degli interessi, caricata dal database, in checkbox selezionati
     * 
     * @param interests la stringa da convertire
     */
    public void interestsManager(String interests){
        
        if(!interests.equals("")){
        
            String[] splits = interests.split("\\ ");
            for (String s : splits){
                if (s.equals(jCheckBox1.getText())) jCheckBox1.setSelected(true);
                if (s.equals(jCheckBox2.getText())) jCheckBox2.setSelected(true);
                if (s.equals(jCheckBox3.getText())) jCheckBox3.setSelected(true);
                if (s.equals(jCheckBox4.getText())) jCheckBox4.setSelected(true);
                if (s.equals(jCheckBox5.getText())) jCheckBox5.setSelected(true);
                if (s.equals(jCheckBox6.getText())) jCheckBox6.setSelected(true);
            }
        }
    }
    
    private boolean validInput(){
        return (!NameTF.getText().equals("")) && (!SurnameTF.getText().equals("")) && (jDateChooser1.getDate() != null);
    }
    
    /**
     * carica il friend nella schermata per la modifica
     * @param friend l'oggetto da modificare
     */
    private void loadFriend(Friend friend){
    
        NameTF.setText(friend.getName());
        SurnameTF.setText(friend.getSurname());
        jDateChooser1.setDate(friend.getDate());
        categoryCB.setSelectedItem(friend.getCategory());
        System.out.println("ci sono");
        interestsManager(friend.getInteressi());
    }
    
    /**
     * Cambia il nome del bottone in funzione dell'operazione da fare: create, edit
     * @param name 
     */  
    public void setNameButton(String name){
    
      CreateButton.setText(name);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSfondo = new javax.swing.JPanel();
        SurnameTF = new javax.swing.JTextField();
        NameTF = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        NameL = new javax.swing.JLabel();
        SurnameL = new javax.swing.JLabel();
        DateL = new javax.swing.JLabel();
        InterestsL = new javax.swing.JLabel();
        InterestsP = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        categoryCB = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        CreateButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Person");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelSfondo.setOpaque(false);
        jPanelSfondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SurnameTF.setToolTipText("");
        jPanelSfondo.add(SurnameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 260, -1));

        NameTF.setToolTipText("");
        NameTF.setName(""); // NOI18N
        jPanelSfondo.add(NameTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 260, -1));

        jDateChooser1.setDateFormatString("dd-MM-yyyy");
        jPanelSfondo.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 129, -1));

        NameL.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        NameL.setText("Name");
        jPanelSfondo.add(NameL, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        SurnameL.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        SurnameL.setText("Surname");
        jPanelSfondo.add(SurnameL, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 20));

        DateL.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        DateL.setText("Date of Birth");
        jPanelSfondo.add(DateL, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        InterestsL.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        InterestsL.setText("Intrerests");
        jPanelSfondo.add(InterestsL, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        InterestsP.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        InterestsP.setName(""); // NOI18N
        InterestsP.setOpaque(false);
        InterestsP.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 2));

        jCheckBox1.setText("Tecnologia");
        jCheckBox1.setOpaque(false);
        InterestsP.add(jCheckBox1);

        jCheckBox3.setText("Musica");
        jCheckBox3.setOpaque(false);
        InterestsP.add(jCheckBox3);

        jCheckBox4.setText("Libri");
        jCheckBox4.setOpaque(false);
        InterestsP.add(jCheckBox4);

        jCheckBox6.setText("Viaggi");
        jCheckBox6.setOpaque(false);
        InterestsP.add(jCheckBox6);

        jCheckBox2.setText("Sport");
        jCheckBox2.setOpaque(false);
        InterestsP.add(jCheckBox2);

        jCheckBox5.setText("Animali");
        jCheckBox5.setOpaque(false);
        InterestsP.add(jCheckBox5);

        jPanelSfondo.add(InterestsP, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 260, 60));

        jLabel2.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel2.setText("Category");
        jPanelSfondo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        categoryCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Famiglia", "Amici", "Colleghi", "Conoscenti", "Altro" }));
        jPanelSfondo.add(categoryCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 160, -1));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        CreateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/ok-26.png"))); // NOI18N
        CreateButton.setText("Create");
        CreateButton.setName(""); // NOI18N
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateButtonActionPerformed(evt);
            }
        });
        jPanel1.add(CreateButton);

        CancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/cancel-26 red.png"))); // NOI18N
        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });
        jPanel1.add(CancelButton);

        jPanelSfondo.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 380, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/sfondo399.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jPanelSfondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanelSfondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 380, 370));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
        
        if (CreateButton.getText().equals("Create")){
            if (createFriend()){
                JOptionPane.showMessageDialog(this, "Creating friend successfully completed", 
                        "Completed", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                circlesScreen circle = new circlesScreen();
                circle.setVisible(true);
            } 
        } else{
            if(updateFriend()){
            JOptionPane.showMessageDialog(this, "Updating friend successfully completed", "Completed", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            circlesScreen circle = new circlesScreen();
            circle.setVisible(true);
            }
        }           
        

    }//GEN-LAST:event_CreateButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
            
            this.dispose();
            circlesScreen circle = new circlesScreen();
            circle.setVisible(true);
    }//GEN-LAST:event_CancelButtonActionPerformed

    
    
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
            java.util.logging.Logger.getLogger(newPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(newPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(newPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(newPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new newPerson().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton CreateButton;
    private javax.swing.JLabel DateL;
    private javax.swing.JLabel InterestsL;
    private javax.swing.JPanel InterestsP;
    private javax.swing.JLabel NameL;
    private javax.swing.JTextField NameTF;
    private javax.swing.JLabel SurnameL;
    private javax.swing.JTextField SurnameTF;
    private javax.swing.JComboBox categoryCB;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelSfondo;
    // End of variables declaration//GEN-END:variables
}
