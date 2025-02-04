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
public class RequestHandlerClient{
    private InventoryManagementClient management;
    private ClientSocket clientSocket;
    private String serverAddress;
    private int port;
    
    public RequestHandlerClient(InventoryManagementClient management, String serverAddress, int port) {
        this.management = management;
        this.serverAddress=serverAddress;
        this.port = port;
    }
    public String formatRequest(String operation, String[] arguments) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("operation", operation);
        for (int i = 0; i< arguments.length; i++){
            jsonRequest.put("argument"+i, arguments[i]);
        }
        
        return jsonRequest.toString(4); 
    }
    
    public String handleRequest(String jsonMessage){
        
        String response;
        
        
        clientSocket = new ClientSocket(serverAddress,port);
        response = clientSocket.sendMessage(jsonMessage);
        
        return response;
    }
}
