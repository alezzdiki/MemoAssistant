/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package maps;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Classe che si occupa di trovare Google Places a partire da una posizione. Richiede una APIkey
 * @author Alessandro
 */
public class Places extends MapsJava {

    public final String URLRoot="https://maps.googleapis.com/maps/api/place/search/xml";
    
    //public final String pathStatus="PlaceSearchResponse/status";
   
    
    /**
     * Tipo di ordinamento dei risultati, verrà utilizzato solo prominence, ossia per importanza
     */
    public enum Rankby{prominence,distance}
    
       /**
        * trasforma un arraylist di nodelist in un matrice di stringhe
        * @param nodes l'arraylist creato con i criteri che interessano: name, vicinity, lat e long
        * @return la matrice dei risultati
        */
    
    private String[][] getNodesPlaces(ArrayList<NodeList> nodes){
        String[][] result=new String[1000][4];
        for(int i = 0; i < nodes.size();i++){
             for (int j = 0, n = nodes.get(i).getLength(); j < n; j++) {
                String nodeString = nodes.get(i).item(j).getTextContent();
                result[j][i]=nodeString;
             }
        }
        result=(String[][])super.resizeArray(result, nodes.get(0).getLength());
                        System.err.println("CIAOOOOOO2222");

        return result;
    }
    
    /**
     * Crea l'URL per la richiesta 
     * @param latitude dell'indirizzo trovato tramite geocoding. Obbligatorio
     * @param longitude dell'indirizzo trovato tramite geocoding. Obbligatorio
     * @param radius raggio di ricerca. Obbligatorio
     * @param keyword   parola chiave da inserire per una ricerca più accurata
     * @param namePlace nome esatto del place; verrà settato null
     * @param rankby criterio di ricerca
     * @param types tipo di place
     * @return l'URL pronto per openStream
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException 
     */
    
    private URL createURL(double latitude, double longitude,int radius,String keyword,String namePlace,
            Rankby rankby,ArrayList<String> types) throws UnsupportedEncodingException, MalformedURLException{
        
        String _location= URLRoot + "?location=" + latitude + "," + longitude;
        String _radius= "";
        if(!rankby.equals(Rankby.distance)){
            _radius= "&radius=" + radius;
        }
      
        String _keyword="";
        if(keyword!=null && !keyword.isEmpty()){
            _keyword="&keyword=" +  URLEncoder.encode(keyword, "utf-8");
        }
        String _namePlace="";
        if(namePlace!=null && !namePlace.isEmpty()){
            _namePlace="&name=" + URLEncoder.encode(namePlace, "utf-8");
        }
        String _rankby="&rankby=" + rankby.toString();
        String _types="";
        if(types!=null && types.size()>0){
            _types="&types=";
            for(String item:types){
                _types+=item;
            }
        }
                                    System.err.println("CIAOOOOOO5555");

        URL urlReturn=new URL(_location + _radius + _keyword + _namePlace + _rankby +
                _types + super.getSelectPropertiesRequest() + "&key=" + MapsJava.getKey());
                                    System.err.println(MapsJava.getKey());

        return urlReturn;
    }
    
  /**
     * Avvia la richiesta vera e propria di ricreca dei places. Si crea l'url, si apre lo stream e si crea un document 
     * DOM object tree, che verrà esaminato tramite xpath. Si creano i nodelist.
     * @param latitude dell'indirizzo trovato tramite geocoding
     * @param longitude dell'indirizzo trovato tramite geocoding
     * @param radius raggio di ricerca
     * @param keyword   parola chiave da inserire per una ricerca più accurata
     * @param namePlace nome esatto del place; verrà settato null
     * @param rankby criterio di ricerca
     * @param types tipo di place
     * @return l'URL pronto per openStream
     * @throws UnsupportedEncodingException
     * @throws MalformedURLException 
     */
    
    public String[][] getPlaces(double latitude, double longitude,int radius,String keyword,String namePlace,
            Rankby rankby,ArrayList<String> types) throws UnsupportedEncodingException, MalformedURLException{
                                   

        URL url=createURL(latitude,longitude,radius,keyword,namePlace,rankby,types);
         System.err.println("Error url: " + url.toString());
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
            DocumentBuilder builder = factory.newDocumentBuilder(); 
            Document document = builder.parse(url.openStream()); 

            XPathFactory xpathFactory = XPathFactory.newInstance(); 
            XPath xpath = xpathFactory.newXPath();
            
            NodeList nodeName = (NodeList) xpath.evaluate("PlaceSearchResponse/result/name", 
                         document, XPathConstants.NODESET);
            NodeList nodeVicinity = (NodeList) xpath.evaluate("PlaceSearchResponse/result/vicinity", 
                         document, XPathConstants.NODESET);
            NodeList nodeLatitude = (NodeList) xpath.evaluate("PlaceSearchResponse/result/geometry/location/lat", 
                         document, XPathConstants.NODESET);
            NodeList nodeLongitude = (NodeList) xpath.evaluate("PlaceSearchResponse/result/geometry/location/lng", 
                         document, XPathConstants.NODESET);
                            
            ArrayList<NodeList> allNodes=new ArrayList<>();
            allNodes.add(nodeName);allNodes.add(nodeVicinity);allNodes.add(nodeLatitude);
            allNodes.add(nodeLongitude);
                            System.err.println("Error");

            String[][] result=this.getNodesPlaces(allNodes);
                            System.err.println(result[0][1]);

            return result;
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Place not found. Try to change radius or address", 
                    "Error", JOptionPane.ERROR_MESSAGE);
              return null;
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    
}
