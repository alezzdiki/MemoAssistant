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
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import memoassistant.Friend;

/**
 *
 * @author Alessandro
 */
public class circlesScreen extends mainFrame {

    private static LinkedList<Friend> friendsList;
    private  DefaultTableModel newModel;
   
   

    public circlesScreen() {
        initComponents();
        loadFriendsTable();
    }
/**
 * carica la lista dei friends
 */
     public static void loadFriends(){
        try {
            Client client = new Client();
            client.send("Friends", USER_EMAIL);
            friendsList =  (LinkedList<Friend>) client.receive();
            client.closeSocket();
            Collections.sort(friendsList);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public static LinkedList<Friend> getFriendsList() {
        return friendsList;
    }

   
 /**
  * carica la lista nella tabella
  */
    public void loadFriendsTable(){
    
        loadFriends();
        //firstScreen.stampaList(firstScreen.getFriendsList());
        String[] columnNames = new String [] {
        "Surname", "Name", "Date of Birth", "Category" };
        Object[][] data = new Object[friendsList.size()][4];
        
       
        int i = 0;
        for (Friend f : friendsList){
        
             String name = f.getName();
            String surname = f.getSurname();
            Date date = f.getDate();
            String category = f.getCategory();
            
            data[i][0] = surname;
            data[i][1] = name;
            data[i][2] = date;
            data[i][3] = category;
            i++;
        }
        setFriendTable(data, columnNames);
    }

    public  void setFriendTable(Object[][] dataObj, String[] col) {

         newModel = new DefaultTableModel(dataObj, col) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        
        circlesTable.setModel(newModel);
        circlesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addDoubleclickedListener();
    }
    
    /**
     * Metodo che aggiunge un listener per un doppio click su una riga della tabella
     */
    private  void addDoubleclickedListener() {
        circlesTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    // do some action
                    JOptionPane.showMessageDialog(null, "idfriend " + friendsList.get(row).getIdfriend());
                    newPerson newperson = new newPerson(friendsList.get(row));
                    newperson.setNameButton("Apply");
                    dispose();
                    newperson.setVisible(true);
                }
            }
        });
    }
    
      private void deleteFriend(int i){
        try {
            LinkedList<Friend> list = new LinkedList<>();
            list.add(friendsList.get(i));
            Client client = new Client();
            client.send(list, "Delete", USER_EMAIL);
            client.closeSocket();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        circlesTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Circles");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/sfondo 500x30.png"))); // NOI18N
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 30));

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/plus-26.png"))); // NOI18N
        addButton.setText("Add");
        addButton.setToolTipText("Add new person");
        addButton.setPreferredSize(new java.awt.Dimension(101, 35));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jPanel2.add(addButton);

        removeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/minus-26.png"))); // NOI18N
        removeButton.setText("Remove");
        removeButton.setToolTipText("Remove  person");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        jPanel2.add(removeButton);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 500, 50));

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        circlesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Surname", "Date of Birth", "Category"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(circlesTable);

        jPanel3.add(jScrollPane2);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 500, 210));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        
        //utlizzo il numero della riga, infatti esso coincide con la posizione della linkdelist in cui si trova l'oggetto
        try{
            int i = circlesTable.getSelectedRow();
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Friend: " + friendsList.get(i).getName() + " " + 
                    friendsList.get(i).getSurname() + " ?", "Delete Friend", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.YES_OPTION){
                deleteFriend(i);
                newModel.removeRow(i);
            }
        }catch (IndexOutOfBoundsException ex){
            JOptionPane.showMessageDialog(this, "No record to delete", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_removeButtonActionPerformed

    
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        newPerson newperson = new newPerson();
        this.dispose();
        newperson.setVisible(true);
        
    }//GEN-LAST:event_addButtonActionPerformed

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
            java.util.logging.Logger.getLogger(circlesScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(circlesScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(circlesScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(circlesScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new circlesScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private static javax.swing.JTable circlesTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables
}
