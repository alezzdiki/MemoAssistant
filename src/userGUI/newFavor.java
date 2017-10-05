/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userGUI;

import client_server.Client;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import memoassistant.Favorites;
import static userGUI.mainFrame.USER_EMAIL;
import maps.*;

/**
 *
 * @author Alessandro
 */
public class newFavor extends mainFrame {

    
    private int idfavorites;
    private Geocoding ObjGeocoding;
    private Places ObjPlaces;
    DefaultTableModel newModel;
    
    /**
     * Creates new form newFavor
     */
    public newFavor() {
        initComponents();
        gpTable.requestFocus();
        ObjGeocoding = new Geocoding();
        ObjPlaces = new Places();
    }

    
    /**
     * Crea un preferito da un indirizzo inserito. Richiede successivamente un nome/description
     * @return true se l'operazione è andata a buon fine, false viceversa
     */
    public boolean createFavoriteStreet(){

        String description = JOptionPane.showInputDialog(this, "Please, enter name or description of favorite",
                "Set name", JOptionPane.INFORMATION_MESSAGE);

        if (description != null && description.length() > 0) {
            try {
                if(description.length() > 45){
                    
                    JOptionPane.showMessageDialog(this, "Name too long. Insert max 45 char", "Error", ERROR_MESSAGE);
                    return false;
                }
                Favorites favorites = null;
                
                favorites = new Favorites(Double.parseDouble(latTF.getText()), Double.parseDouble(longTF.getText()),
                        addressFoundTF.getText(), description);
                
                LinkedList<Favorites> list = new LinkedList<>();
                list.add(favorites);
                
                Client client = new Client();
                client.send(list, "New", USER_EMAIL);
                client.closeSocket();
                return true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        JOptionPane.showMessageDialog(this, "No name inserted", "Error", ERROR_MESSAGE);
        return false;
        
    }
    
    /**
     * Crea un preferito da un Google Place
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public boolean createFavoriteGP() {


        if (!((String) newModel.getValueAt(0, 0)).equals("")) {
            try {
                String description = (String) newModel.getValueAt(gpTable.getSelectedRow(), 0);
                String address = (String) newModel.getValueAt(gpTable.getSelectedRow(), 1);
                Double latitude =  Double.parseDouble((String) newModel.getValueAt(gpTable.getSelectedRow(), 2));
                Double longitude = Double.parseDouble((String) newModel.getValueAt(gpTable.getSelectedRow(), 3));
                Favorites favorites = new Favorites(latitude, longitude, address, description);
                LinkedList<Favorites> list = new LinkedList<>();
                list.add(favorites);
                
                Client client = new Client();
                client.send(list, "New", USER_EMAIL);
                client.closeSocket();
                return true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }
      
    /**
     * Calcola le due coordinate identificative dell'indirizzo inserito
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException 
     */
    
   private void getFromStreet() throws UnsupportedEncodingException, MalformedURLException{
      
       try {
           if (!this.addressTF.getText().isEmpty()) {
               addressFoundTF.setText("");
               System.out.println("address inserito: " + addressTF.getText());
               Point2D.Double result = ObjGeocoding.getCoordinates(this.addressTF.getText());
               if (result == null) {
                   System.out.println("result null");
               }
               System.out.println("sono qui: " + result.toString());
               latTF.setText(String.valueOf(result.x));
               longTF.setText(String.valueOf(result.y));
               addressFoundTF.setText(String.valueOf(ObjGeocoding.getAddressFound()));
           }
       }catch (NullPointerException e){
           JOptionPane.showMessageDialog(this, "Check your internet connection", "Error", ERROR_MESSAGE);
       }
   }
   
   
   /**
     * Calcola la lista dei Places che soddisfano i criteri inseriti e la passa al metodo loadPlaceTable()
     * 
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException
     * @throws IOException 
     */
   private void places() throws UnsupportedEncodingException, MalformedURLException, IOException{
        
       try{
           if(!approx_addrTF.getText().isEmpty()){
            cleanTable(gpTable);
            Point2D.Double latLong=ObjGeocoding.getCoordinates(approx_addrTF.getText());
            if(latLong.x!=0.0 && latLong.y!=0.0){
                String keyword=null;
                if(!keywordTF.getText().isEmpty()){
                    keyword=keywordTF.getText();
                }
                String place=null;
                
                ArrayList<String> types=new ArrayList<>();
                if(!"No type".equals(typeCB.getSelectedItem().toString())){
                    types.add(typeCB.getSelectedItem().toString());
                }
                Places.Rankby rankby= Places.Rankby.prominence;
               
                int radius = (int)radiusSpinner.getValue();
                
                String[][] result = ObjPlaces.getPlaces(latLong.x, latLong.y, radius,
                        keyword, place, rankby, types);
                loadPlacesTable(result);
            }
        }
       }catch (NullPointerException ex){
           JOptionPane.showMessageDialog(this, "Check your internet connection", "Error", ERROR_MESSAGE);
       }
    }
   
   
   /**
    * pulisce la tabella
    * @param jtable la table da pulire
    */
   private void cleanTable(JTable jtable){
        jtable.setModel(new DefaultTableModel());
    }
   
   
    /**
    * Carica la matrice nella tabella e ne setta il model
    * 
    * @param data matrice ottenuta tramite places()
    * @see places
    */
   
    public void loadPlacesTable(String[][] data){
    
        
        String[] columnNames = new String [] {
        "Place", "Vicinity", "Latitude", "Longitude" };
       
        
       
        setFavoritesTable(data, columnNames);
    }

    public  void setFavoritesTable(Object[][] dataObj, String[] col) {

          newModel = new DefaultTableModel(dataObj, col) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        
        gpTable.setModel(newModel);
        gpTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
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
        jPanel6 = new javax.swing.JPanel();
        createButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanelSfondo = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        approx_addrTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        keywordTF = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        typeCB = new javax.swing.JComboBox();
        find_gp_Button = new javax.swing.JButton();
        googleplacesRB = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        radiusSpinner = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        addressTF = new javax.swing.JTextField();
        find_str_Button = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        latTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        longTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        addressFoundTF = new javax.swing.JTextField();
        streetRB = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        gpTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Favorites");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        createButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/ok-26.png"))); // NOI18N
        createButton.setText("Create");
        createButton.setEnabled(false);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });
        jPanel6.add(createButton);

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/cancel-26 red.png"))); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        jPanel6.add(cancelButton);

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 550, 50));

        jPanelSfondo.setOpaque(false);
        jPanelSfondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Google Places", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Estrangelo Edessa", 0, 18))); // NOI18N
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Insert approximate address: ");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 37, -1, -1));

        approx_addrTF.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel5.add(approx_addrTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 29, -1, -1));

        jLabel6.setText("Keyword: ");
        jLabel6.setToolTipText("");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        keywordTF.setPreferredSize(new java.awt.Dimension(130, 30));
        jPanel5.add(keywordTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 100, -1));

        jLabel7.setText("Type");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

        typeCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "No type", "accounting", "airport", "amusement_park", "aquarium", "art_gallery", "atm", "bakery", "bank", "bar", "beauty_salon", "bicycle_store", "book_store", "bowling_alley", "bus_station", "cafe", "campground", "car_dealer", "car_rental", "car_repair", "car_wash", "casino", "cemetery", "church", "city_hall", "clothing_store", "convenience_store", "courthouse", "dentist", "department_store", "doctor", "electrician", "electronics_store", "embassy", "establishment", "finance", "fire_station", "florist", "food", "funeral_home", "furniture_store", "gas_station", "general_contractor", "grocery_or_supermarket", "gym", "hair_care", "hardware_store", "health", "hindu_temple", "home_goods_store", "hospital", "insurance_agency", "jewelry_store", "laundry", "lawyer", "library", "liquor_store", "local_government_office", "locksmith", "lodging", "meal_delivery", "meal_takeaway", "mosque", "movie_rental", "movie_theater", "moving_company", "museum", "night_club", "painter", "park", "parking", "pet_store", "pharmacy", "physiotherapist", "place_of_worship", "plumber", "police", "post_office", "real_estate_agency", "restaurant", "roofing_contractor", "rv_park", "school", "shoe_store", "shopping_mall", "spa", "stadium", "storage", "store", "subway_station", "synagogue", "taxi_stand", "train_station", "travel_agency", "university", "veterinary_care", "zoo" }));
        jPanel5.add(typeCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(334, 70, 140, 30));

        find_gp_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/lenteblu 16x16.png"))); // NOI18N
        find_gp_Button.setToolTipText("Find Places");
        find_gp_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                find_gp_ButtonActionPerformed(evt);
            }
        });
        jPanel5.add(find_gp_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, 50, 30));

        buttonGroup1.add(googleplacesRB);
        googleplacesRB.setOpaque(false);
        googleplacesRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                googleplacesRBActionPerformed(evt);
            }
        });
        jPanel5.add(googleplacesRB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, -10, 30, 40));

        jLabel9.setText("Radius");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 40, -1));

        radiusSpinner.setModel(new javax.swing.SpinnerNumberModel(500, 1, 50000, 10));
        jPanel5.add(radiusSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 70, 30));

        jPanelSfondo.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 550, 120));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Street", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Estrangelo Edessa", 0, 18))); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Insert address: ");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 44, -1, 20));

        addressTF.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel4.add(addressTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 290, -1));

        find_str_Button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/lenteblu 16x16.png"))); // NOI18N
        find_str_Button.setToolTipText("Find Places");
        find_str_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                find_str_ButtonActionPerformed(evt);
            }
        });
        jPanel4.add(find_str_Button, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, -1, 30));

        jLabel1.setText("Latitude:");
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 14));
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, 30));

        latTF.setEditable(false);
        latTF.setPreferredSize(new java.awt.Dimension(70, 30));
        jPanel4.add(latTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 80, -1));

        jLabel2.setText("Longitude:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 84, -1, 20));

        longTF.setEditable(false);
        longTF.setPreferredSize(new java.awt.Dimension(70, 30));
        jPanel4.add(longTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, 80, -1));

        jLabel3.setText("Address found:");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 124, -1, 20));

        addressFoundTF.setEditable(false);
        addressFoundTF.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel4.add(addressFoundTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 330, -1));

        buttonGroup1.add(streetRB);
        streetRB.setToolTipText("");
        streetRB.setOpaque(false);
        streetRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                streetRBActionPerformed(evt);
            }
        });
        jPanel4.add(streetRB, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, -1, 20));

        jPanelSfondo.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 550, 170));

        gpTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Place", "Vicinity", "Latitude", "Longitude"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(gpTable);

        jPanelSfondo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 550, 130));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/userGUI/resources/sfondo 550x550.png"))); // NOI18N
        jPanelSfondo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));

        getContentPane().add(jPanelSfondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 0, 550, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void streetRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_streetRBActionPerformed
        addressTF.setEnabled(true);
        find_str_Button.setEnabled(true);
        approx_addrTF.setEnabled(false);
        keywordTF.setEnabled(false);
        typeCB.setEnabled(false);
        find_gp_Button.setEnabled(false);
        createButton.setEnabled(true);
        approx_addrTF.setText("");
        keywordTF.setText("");
    }//GEN-LAST:event_streetRBActionPerformed

    private void googleplacesRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_googleplacesRBActionPerformed
        addressTF.setEnabled(false);
        find_str_Button.setEnabled(false);
        approx_addrTF.setEnabled(true);
        keywordTF.setEnabled(true);
        typeCB.setEnabled(true);
        find_gp_Button.setEnabled(true);
        createButton.setEnabled(true);
        addressFoundTF.setText("");
        addressTF.setText("");
        latTF.setText("");
        longTF.setText("");
    }//GEN-LAST:event_googleplacesRBActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed

        
            try{
                
                if (addressFoundTF.getText().equals("No data") || 
                        (addressFoundTF.getText().equals("") && ((String)newModel.getValueAt(0, 0)).equals(""))) 
                        throw new Exception();
                
                if (streetRB.isSelected()){
                      if (createFavoriteStreet()){
                            JOptionPane.showMessageDialog(this, "Creating favorite successfully completed", "Completed", 
                                    JOptionPane.INFORMATION_MESSAGE);
                            this.dispose();
                            favoritesScreen fav = new favoritesScreen();
                            fav.setVisible(true);
                      }
                }
                else if (googleplacesRB.isSelected()){
                    if (createFavoriteGP()){
                            JOptionPane.showMessageDialog(this, "Creating favorite successfully completed", "Completed", 
                                    JOptionPane.INFORMATION_MESSAGE);
                            this.dispose();      
                            favoritesScreen fav = new favoritesScreen();
                            fav.setVisible(true);
                      }
                }     

          }catch (Exception ex) {
              JOptionPane.showMessageDialog(this, "Address not found" , "Error ", ERROR_MESSAGE);
          }  
        
   
       
        
    }//GEN-LAST:event_createButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
        favoritesScreen fav = new favoritesScreen();
        fav.setVisible(true);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void find_str_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_find_str_ButtonActionPerformed
        try {
            getFromStreet();
        } catch (UnsupportedEncodingException | MalformedURLException ex) {
            JOptionPane.showMessageDialog(this, "Error geocoding", ex.getMessage(), ERROR_MESSAGE);
        }
    }//GEN-LAST:event_find_str_ButtonActionPerformed

    private void find_gp_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_find_gp_ButtonActionPerformed
        
        if(!MapsJava.getKey().isEmpty()){
            try {
                places();
                
            } catch (MalformedURLException ex) {
                Logger.getLogger(newFavor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(newFavor.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        else{
            JOptionPane.showMessageDialog(this,"Insert a valid APIkey", 
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_find_gp_ButtonActionPerformed

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
            java.util.logging.Logger.getLogger(newFavor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(newFavor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(newFavor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(newFavor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new newFavor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressFoundTF;
    private javax.swing.JTextField addressTF;
    private javax.swing.JTextField approx_addrTF;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton createButton;
    private javax.swing.JButton find_gp_Button;
    private javax.swing.JButton find_str_Button;
    private javax.swing.JRadioButton googleplacesRB;
    private javax.swing.JTable gpTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelSfondo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField keywordTF;
    private javax.swing.JTextField latTF;
    private javax.swing.JTextField longTF;
    private javax.swing.JSpinner radiusSpinner;
    private javax.swing.JRadioButton streetRB;
    private javax.swing.JComboBox typeCB;
    // End of variables declaration//GEN-END:variables
}
