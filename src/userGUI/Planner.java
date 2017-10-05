/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userGUI;

import client_server.Client;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Time;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import memoassistant.Action;
import memoassistant.Event;


/**
 *
 * @author Alessandro
 */
public class Planner extends mainFrame {

    private static LinkedList<Event> eventList;
    private static LinkedList<Action> actionList;
    private DefaultTableModel newModel1, newModel2;
    

    /**
     * Creates new form Planner
     */
    public Planner() {
        initComponents();
        loadEventTable();
        loadActionTable();
    }

     /**
      * carica la lista degli event
      */
     public static void loadEvent(){
        try {
            Client client = new Client();
            client.send("Event", USER_EMAIL);
            eventList = (LinkedList<Event>) client.receive();
            Collections.sort(eventList);
            client.closeSocket();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }    
    /**
     * carica la lista delle action
     */
    public static void loadAction(){
        try {
            Client client = new Client();
            client.send("Action", USER_EMAIL);
            actionList = (LinkedList<Action>) client.receive();
            client.closeSocket();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * carica gli event nella tabella
     */
    public void loadEventTable(){
    
        loadEvent();
        //firstScreen.stampaList(firstScreen.getFriendsList());
        String[] columnNames = new String [] {
        "Date", "Hour", "Where", "What" };
        Object[][] data = new Object[eventList.size()][4];
        
        
        int i = 0;
        for (Event e : eventList){
        
            Date date = e.getEventDate();
            Time time = e.getEventHour();
            String where = e.getWhere();
            String temp_what = e.getWhat();        
            String what = temp_what.replaceAll("\n"," ");
            
            data[i][0] = date;
            data[i][1] = time;
            data[i][2] = where;
            data[i][3] = what;
            i++;
        }
        setEventTable(data, columnNames);
    }

    public  void setEventTable(Object[][] dataObj, String[] col) {

        newModel2 = new DefaultTableModel(dataObj, col) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        eventTable.setModel(newModel2);
        eventTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addDoubleclickedListenerEvent();
    }
    
    /**
     * aggiunge un listener per il doppio click in una riga della tabella
     */
    private  void addDoubleclickedListenerEvent() {
        eventTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    // do some action
                    JOptionPane.showMessageDialog(null, "idmemo: " + eventList.get(row).getIdmemo());
                    newPlanning plan = new newPlanning(eventList.get(row));
                    plan.setNameButton("Apply");
                    dispose();
                    plan.setVisible(true);
                    
                }
            }
        });
    }
    
     private void deleteEvent(int i){
        try {
            LinkedList<Event> list = new LinkedList<>();
            list.add(eventList.get(i));
            Client client = new Client();
            client.send(list, "Delete", USER_EMAIL);
            client.closeSocket();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
     /**
      * carica la lista delle action in tabella
      */
     public void loadActionTable(){
    
        loadAction();
        //firstScreen.stampaList(firstScreen.getFriendsList());
        String[] columnNames = new String [] {
        "What", "Where", "Made"};
        Object[][] data = new Object[actionList.size()][3];
        
     
        int i = 0;
        for(Action a : actionList){
        
            String where = a.getWhere();
            String temp_what = a.getWhat();        
            String what = temp_what.replaceAll("\n"," ");        
            byte made = a.getConclused();
            
            data[i][0] = what;
            data[i][1] = where;
            if (made == 0) data[i][2] = "No";
            else data[i][2] = "Yes";
            i++;
        }
        
        setActionTable(data, columnNames);
    }
     

    public  void setActionTable(Object[][] dataObj, String[] col) {

        newModel1 = new DefaultTableModel(dataObj, col) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        
        actionTable.setModel(newModel1);
        actionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addDoubleclickedListenerAction();
    }
    
    /**
    * Metodo che aggiunge un listener per un doppio click su una riga della tabella
     */
    private  void addDoubleclickedListenerAction() {
        actionTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    // do some action
                    JOptionPane.showMessageDialog(null, "idmemo: " + actionList.get(row).getIdmemo());
                    newPlanning plan = new newPlanning(actionList.get(row));
                    plan.setNameButton("Apply");
                    dispose();
                    plan.setVisible(true);
                    
                }
            }
        });
    }
   
   
     private void deleteAction(int i){
        try {
            LinkedList<Action> list = new LinkedList<>();
            list.add(actionList.get(i));
            Client client = new Client();
            client.send(list, "Delete", USER_EMAIL);
            client.closeSocket();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static LinkedList<Event> getEventList() {
        return eventList;
    }

    public static LinkedList<Action> getActionList() {
        return actionList;
    }
     
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        addeventButton = new javax.swing.JButton();
        removeventButton = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        eventTable = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        actionTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        addactionButton = new javax.swing.JButton();
        removeactionButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Planner");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/sfondo 1000x30.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel1.add(jLabel4);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 30));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Estrangelo Edessa", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 255));
        jLabel3.setText("Event");
        jPanel4.add(jLabel3);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 620, 30));

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addeventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/plus-26.png"))); // NOI18N
        addeventButton.setToolTipText("Add Event");
        addeventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addeventButtonActionPerformed(evt);
            }
        });
        jPanel6.add(addeventButton);

        removeventButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/minus-26.png"))); // NOI18N
        removeventButton.setToolTipText("Remove Event");
        removeventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeventButtonActionPerformed(evt);
            }
        });
        jPanel6.add(removeventButton);

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 620, 50));

        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        eventTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Date", "Hour", "Where", "What"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(eventTable);

        jPanel7.add(jScrollPane1);

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 620, 390));

        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 464, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 20, 470));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Estrangelo Edessa", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Action");
        jPanel2.add(jLabel1);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, 360, 30));

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        actionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", ""}
            },
            new String [] {
                "Where", "What"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(actionTable);

        jPanel3.add(jScrollPane2);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 360, 390));

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addactionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/plus-26.png"))); // NOI18N
        addactionButton.setToolTipText("Add Aciton");
        addactionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addactionButtonActionPerformed(evt);
            }
        });
        jPanel8.add(addactionButton);

        removeactionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/minus-26.png"))); // NOI18N
        removeactionButton.setToolTipText("Remove Action");
        removeactionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeactionButtonActionPerformed(evt);
            }
        });
        jPanel8.add(removeactionButton);

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 450, 360, 50));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addeventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addeventButtonActionPerformed
        
        newPlanning newplannig = new newPlanning();
        this.dispose();
        newplannig.setVisible(true);
        
        
    }//GEN-LAST:event_addeventButtonActionPerformed

    private void addactionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addactionButtonActionPerformed
        
        newPlanning newplannig = new newPlanning();
        this.dispose();
        newplannig.setVisible(true);
        
    }//GEN-LAST:event_addactionButtonActionPerformed

    private void removeventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeventButtonActionPerformed
        
        try{
            int i = eventTable.getSelectedRow();
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Event: " + eventList.get(i).getWhat()+ "  At: " + 
                    eventList.get(i).getWhere()+ " ?", "Delete Event", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.YES_OPTION){
                deleteEvent(i);
                newModel2.removeRow(i);
            }
        }catch (IndexOutOfBoundsException ex){
            JOptionPane.showMessageDialog(this, "No record to delete", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_removeventButtonActionPerformed

    private void removeactionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeactionButtonActionPerformed
        
         try{
            int i = actionTable.getSelectedRow();
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Action: " + actionList.get(i).getWhat()+ "  At: " + 
                    actionList.get(i).getWhere()+ " ?", "Delete Action", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.YES_OPTION){
                deleteAction(i);
                newModel1.removeRow(i);
            }
        }catch (IndexOutOfBoundsException ex){
            JOptionPane.showMessageDialog(this, "No record to delete", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_removeactionButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Planner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Planner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Planner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Planner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Planner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable actionTable;
    private javax.swing.JButton addactionButton;
    private javax.swing.JButton addeventButton;
    private javax.swing.JTable eventTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton removeactionButton;
    private javax.swing.JButton removeventButton;
    // End of variables declaration//GEN-END:variables
}
