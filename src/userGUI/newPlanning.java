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
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import memoassistant.*;


/**
 *
 * @author Alessandro
 */
public class newPlanning extends mainFrame {

    private int idmemo;
    private byte conclused;
    private byte warned;

    /**
     * Creates new form newPlanning
     */
    public newPlanning() {
        initComponents();
    }

     
     
      public newPlanning(Event event) {
        initComponents();
        idmemo = event.getIdmemo();
        conclused = event.getConclused();
        warned = event.getWarned();
        Action_jRadioButton.setEnabled(false);
        loadEvent(event);
    }
      
       public newPlanning(Action action) {
        initComponents();
        idmemo = action.getIdmemo();
        conclused = action.getConclused();
        warned = action.getWarned();
        Event_jRadioButton.setEnabled(false);
        loadAction(action);
    }
       
      /**
       * carica l'evento in caso di modifica
       */
      private void loadEvent(Event event){
    
        whatTA.setText(event.getWhat());
        whereTF.setText(event.getWhere());
        jDateChooser.setDate(event.getEventDate());
        timejSpinner.setValue(event.getEventHour());
        
    }
      
      /**
       * carica l'action in caso di modifica
       */
      private void loadAction(Action action){
    
        whatTA.setText(action.getWhat());
        whereTF.setText(action.getWhere());
        LatJLabel.setText(String.valueOf(action.getLatitude()));
        LongJLabel.setText(String.valueOf(action.getLongitude()));
            
    }
      
      public void setLatJLabel(String prompt){
      
          LatJLabel.setText(prompt);
      }
      
      public void setLongJLabel(String prompt){
      
          LongJLabel.setText(prompt);
      }
      
      public void setWhereTF(String prompt){
      
          whereTF.setText(prompt);
      }
      
       public void setNameButton(String name){
    
      CreateButton.setText(name);
    }

        public void setWhatTA(String message) {
        whatTA.setText(message);
    }
    
      public void setEventRadioButton(){
      
          Event_jRadioButton.setEnabled(false);
      }
       
       public boolean createEvent() {
        try {
            if (!validInputEvent()) {
                JOptionPane.showMessageDialog(this, "All fields are required, please", "Error invalid input", ERROR_MESSAGE);
                return false;
            }
            Event event = new Event();
            
            String dateString = ((JTextField) jDateChooser.getDateEditor().getUiComponent()).getText();
            try {
                event.setEventDate(dateManager(dateString));
            } catch (ParseException ex) {
                System.err.println("Error date format");
            }
            
            
            Date date = (Date) timejSpinner.getValue();
            java.sql.Time time = new java.sql.Time(date.getTime());
            event.setEventHour(time);
            //System.out.println(time);
            event.setWhere(whereTF.getText());
            event.setWhat(whatTA.getText());
            
            LinkedList<Event> list = new LinkedList<>();
            list.add(event);
            Client client = new Client();
            client.send(list, "New", USER_EMAIL);
            client.closeSocket();
            
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    
    public boolean createAction(){
        try {
            if (!validInputAction()){
                JOptionPane.showMessageDialog(this, "All fields are required, please", "Error invalid input", ERROR_MESSAGE);
                return false;
            }
            Action action = new Action();
            
            action.setWhere(whereTF.getText());
            action.setWhat(whatTA.getText());
            action.setLatitude(Double.parseDouble(LatJLabel.getText()));
            action.setLongitude(Double.parseDouble(LongJLabel.getText()));
            
            
            
            LinkedList<Action> list = new LinkedList<>();
            list.add(action);
            client_server.Client client = new Client();
            client.send(list, "New", USER_EMAIL);
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
    
     private boolean validInputEvent(){
        return (!whereTF.getText().equals("")) && (!whatTA.getText().equals(""))
                && (jDateChooser.getDate() != null);
    }
     
      private boolean validInputAction(){
        return (!whereTF.getText().equals("")) && (!whatTA.getText().equals("")) 
                && (!LatJLabel.getText().equals("")) && (!LongJLabel.getText().equals(""));
    }
     
    public boolean updateEvent(){
        try {    
            Event event = new Event();
            event.setIdmemo(idmemo);
            event.setConclused(conclused);
            event.setWarned(warned);
            event.setWhere(whereTF.getText());
            event.setWhat(whatTA.getText());
        
            Date date = (Date) timejSpinner.getValue();
            java.sql.Time time = new java.sql.Time (date.getTime());
            event.setEventHour(time);
            String dateString = ((JTextField)jDateChooser.getDateEditor().getUiComponent()).getText();
            System.out.println(dateString);
            try {
                event.setEventDate(dateManager(dateString));
            } catch (ParseException ex) {
                System.err.println("Error date format");
            }

            if (!validInputEvent()){
                JOptionPane.showMessageDialog(this, "All fields are required, please", "Error invalid input", ERROR_MESSAGE);
                return false;
            }
            
            LinkedList<Event> list = new LinkedList<>();
            list.add(event);

            Client client = new Client();
            client.send(list, "Update", null);
            client.closeSocket();
            
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }       
    
    public boolean updateAction(){
        try {    
            Action action = new Action();
            action.setIdmemo(idmemo);
            action.setConclused(conclused);
            action.setWarned(warned);
            action.setWhere(whereTF.getText());
            action.setWhat(whatTA.getText());
            action.setLatitude(Double.parseDouble(LatJLabel.getText()));
            action.setLongitude(Double.parseDouble(LongJLabel.getText()));

            if (!validInputAction()){
                JOptionPane.showMessageDialog(this, "All fields are required, please", "Error invalid input", ERROR_MESSAGE);
                return false;
            }
            
            LinkedList<Action> list = new LinkedList<>();
            list.add(action);

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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanelSfondo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        CreateButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Event_jRadioButton = new javax.swing.JRadioButton();
        Action_jRadioButton = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser = new com.toedter.calendar.JDateChooser();
        java.util.Date date = new Date();
        javax.swing.SpinnerDateModel sdm =
        new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        timejSpinner = new javax.swing.JSpinner(sdm);
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        whereTF = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        LatJLabel = new javax.swing.JLabel();
        LongJLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        whatTA = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Memo");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelSfondo.setOpaque(false);
        jPanelSfondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);

        CreateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/ok-26.png"))); // NOI18N
        CreateButton.setText("Create");
        CreateButton.setEnabled(false);
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

        jPanelSfondo.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 400, 50));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel1.setText("     Planning:          ");
        jPanel2.add(jLabel1);

        buttonGroup1.add(Event_jRadioButton);
        Event_jRadioButton.setText("Event               ");
        Event_jRadioButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Event_jRadioButton.setOpaque(false);
        Event_jRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Event_jRadioButtonActionPerformed(evt);
            }
        });
        jPanel2.add(Event_jRadioButton);

        buttonGroup1.add(Action_jRadioButton);
        Action_jRadioButton.setText("Action");
        Action_jRadioButton.setOpaque(false);
        Action_jRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Action_jRadioButtonActionPerformed(evt);
            }
        });
        jPanel2.add(Action_jRadioButton);

        jPanelSfondo.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 380, 40));

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel2.setText("Date/Hour:  ");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jDateChooser.setDateFormatString("dd-MM-yyyy");
        jDateChooser.setMinimumSize(new java.awt.Dimension(129, 30));
        jDateChooser.setPreferredSize(new java.awt.Dimension(129, 30));
        jPanel3.add(jDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 10, 140, -1));

        JSpinner.DateEditor de = new JSpinner.DateEditor(timejSpinner, "HH:mm");
        timejSpinner.setEditor(de);
        jPanel3.add(timejSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(261, 10, 70, 30));

        jPanelSfondo.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 380, 50));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel4.setText("Where:       ");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        whereTF.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel4.add(whereTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 5, 230, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/plus-26.png"))); // NOI18N
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 5, 40, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel6.setText("Latitude:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 40, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel7.setText("Longitude:");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 50, 30));

        LatJLabel.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        jPanel4.add(LatJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 70, 30));

        LongJLabel.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        jPanel4.add(LongJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 90, 30));

        jPanelSfondo.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 380, 70));

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Bodoni MT", 0, 14)); // NOI18N
        jLabel5.setText("What:      ");
        jLabel5.setToolTipText("");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        whatTA.setColumns(20);
        whatTA.setRows(5);
        jScrollPane1.setViewportView(whatTA);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 5, 280, 120));

        jPanelSfondo.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 400, 130));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/sfondo399.png"))); // NOI18N
        jPanelSfondo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanelSfondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 400, 380));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
        // TODO add your handling code here:
        if (!jDateChooser.isEnabled()){
            
            if(CreateButton.getText().equals("Create")){
                if (createAction()){
                    JOptionPane.showMessageDialog(this, "Creating action successfully completed", 
                            "Completed", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    Planner plan = new Planner();
                    plan.setVisible(true);
                }
            } else{
                if (updateAction()){
                    JOptionPane.showMessageDialog(this, "Updating action successfully completed", "Completed",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    Planner plan = new Planner();
                    plan.setVisible(true);
                } 
            }                    
        }
        else{
            if(CreateButton.getText().equals("Create")){
                if (createEvent()){
                    JOptionPane.showMessageDialog(this, "Creating event successfully completed", 
                            "Completed", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    Planner plan = new Planner();
                    plan.setVisible(true);
                }
            } 
            else{
                if (updateEvent()){
                    JOptionPane.showMessageDialog(this, "Updating event successfully completed", "Completed",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    Planner plan = new Planner();
                    plan.setVisible(true);
                } 
            }
        }
    }//GEN-LAST:event_CreateButtonActionPerformed

    private void Action_jRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Action_jRadioButtonActionPerformed
        CreateButton.setEnabled(true);
        jDateChooser.setEnabled(false);
        timejSpinner.setEnabled(false);
        whereTF.setEnabled(false);
    }//GEN-LAST:event_Action_jRadioButtonActionPerformed

    private void Event_jRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Event_jRadioButtonActionPerformed
        CreateButton.setEnabled(true);
        jDateChooser.setEnabled(true);
        timejSpinner.setEnabled(true);
        whereTF.setEnabled(true);
    }//GEN-LAST:event_Event_jRadioButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        
        this.dispose();
        Planner plan = new Planner();
        plan.setVisible(true);
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        memoWhere memowhere = new memoWhere(this);
        memowhere.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed


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
            java.util.logging.Logger.getLogger(newPlanning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(newPlanning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(newPlanning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(newPlanning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new newPlanning().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Action_jRadioButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton CreateButton;
    private javax.swing.JRadioButton Event_jRadioButton;
    private javax.swing.JLabel LatJLabel;
    private javax.swing.JLabel LongJLabel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelSfondo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner timejSpinner;
    private javax.swing.JTextArea whatTA;
    private javax.swing.JTextField whereTF;
    // End of variables declaration//GEN-END:variables
}
