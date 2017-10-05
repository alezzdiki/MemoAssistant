/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package maps;

import java.awt.geom.Point2D;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Classe che si occupa del geocoding 
 * @author Alessandro
 * @see MapsJava
 */
public class Geocoding extends MapsJava{

    public final String URLRoot = "http://maps.google.com/maps/api/geocode/xml";
    // public final String PATHSTATUS = "GeocodeResponse/status";
    public final String PATHPOSTALCODE = "GeocodeResponse/result/address_component";
    public final String PATHCOORDINATES = "GeocodeResponse/result/geometry/location[1]/*";
    public final String PATHADDRESS = "GeocodeResponse/result/formatted_address";
    
    private String addressFound;
    private String postalcode;
    
    /**
     * Restituisce l'indirizzo trovato a seguito di una richiesta di geocoding. Se non trova nulla ritorna "No data"
     * 
     * @return l'indirizzo
     */
    public String getAddressFound() {
        return addressFound;
    }
    
    /**
     * Restituisce il CAP a seguito di una richiesta di geocoding. Se non trova nulla ritorna "No data"
     * @return il CAP
     */
    public String getPostalcode() {
        return postalcode;
    }

    /**
     * Scorre la lista di nodi address_compontent fino al postal code, dopo di che ne formatta la stringa 
     * @param node nodelist contentente tutti gli address component
     * @return il CAP
     */

    private String getNodesPostalcode(NodeList node){
         String result = "No data";
         int i = 0;
         while (i<node.getLength()) {
            String nodeString = node.item(i).getTextContent();
            if(nodeString.contains("postal_code")){
                result = nodeString.replace(" ", "").substring(1,6);
                break;
            }
            i+=1;
        }
        return result;
    }

    /**
     * Crea l'URL per la richiesta. Il metodo static di URLEncoder codifica l'indirizzo secondo UTF - 8
     * @param address indirizzo da ricercare
     * @return un oggetto di tipo URL pronto per openStream
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException 
     */
    private URL createURL(String address) throws UnsupportedEncodingException, MalformedURLException{
        URL urlReturn=new URL(URLRoot + "?address=" + URLEncoder.encode(address, "utf-8") + super.getSelectPropertiesRequest());
        return urlReturn;
    }
    
   
    
    /**
     * Metodo che trasforma un indirizzo in coordinate geografiche. Si crea l'url, si apre lo stream e si crea un document 
     * DOM object tree, che verrà esaminato tramite xpath. Si creano i nodelist.
     * 
     * @param address indirizzo da trasformare
     * @return ritorna un punto 2D, ossia un punto con una coordniata x "latitudine" e una y "longitudine"
     * Restituisce 0.0 in caso di errore
     */
    public Point2D.Double getCoordinates(String address) throws UnsupportedEncodingException, MalformedURLException{
        this.addressFound="";
        URL url=createURL(address);
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
                DocumentBuilder builder = factory.newDocumentBuilder(); 
                Document document = builder.parse(url.openStream()); 

                XPathFactory xpathFactory = XPathFactory.newInstance(); 
                XPath xpath = xpathFactory.newXPath(); 

                NodeList nodeLatLng = (NodeList) xpath.evaluate(PATHCOORDINATES, 
                         document, XPathConstants.NODESET);
                NodeList nodeAddress = (NodeList) xpath.evaluate(PATHADDRESS, 
                         document, XPathConstants.NODESET);
                NodeList nodePostal = (NodeList) xpath.evaluate(this.PATHPOSTALCODE, 
                         document, XPathConstants.NODESET);
                Double lat=0.0;
                Double lng=0.0;
                try {
                    this.postalcode=this.getNodesPostalcode(nodePostal);
                    this.addressFound="No data";
                    this.addressFound=nodeAddress.item(0).getTextContent();
                    lat = Double.valueOf(nodeLatLng.item(0).getTextContent());
                    lng = Double.valueOf(nodeLatLng.item(1).getTextContent());
                    System.out.println("lat e long " + lat + lng);
                } catch (Exception ex) {
                     JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                Point2D.Double result = new Point2D.Double(lat, lng);
                System.out.println("coordinate: " + result.toString());
                return result;
            } catch (Exception ex) {
                     JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
     }
    
   
    
   }
