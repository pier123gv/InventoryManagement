/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementclient.networklayer;

import backend.inventorymanagementclient.InventoryManagementClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
/**
 *
 * @author pier
 */
public class RequestHandler{
    private InventoryManagementClient management;
    private ClientSocket clientSocket;
    
    public RequestHandler(InventoryManagementClient management) {
        this.management = management;
    }
    public String formatRequest(String operation, String[] arguments) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("operation", operation);
        jsonRequest.put("arguments", arguments);
        return jsonRequest.toString(4); 
    }
    
    public String handleRequest(String jsonMessage){
        
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File("configuration.properties")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String response;
        JSONObject jsonRequest = new JSONObject(jsonMessage);
        String ip= jsonRequest.getString("ip");
        int port = jsonRequest.getInt("port");
        
        String sslRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
        String sslPassword = p.getProperty("SSL_PASSWORD");
        System.setProperty("javax.net.ssl.keyStore",sslRoute);
        System.setProperty("javax.net.ssl.keyStorePassword",sslPassword);
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.trustStore", sslRoute);
        System.setProperty("javax.net.ssl.trustStorePassword", sslPassword);
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
        
        
        clientSocket = new ClientSocket(ip,port);
        response = clientSocket.sendMessage(jsonMessage);
        
        return response;
    }
}
