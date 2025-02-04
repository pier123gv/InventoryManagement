/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementserver.networklayer;

import backend.inventorymanagementserver.InventoryManagementServer;
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
    public String formatRequest(String senderIp, String recieverIp, int port, String operation, String arguments) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("senderip", senderIp);
        jsonRequest.put("recieverIp", recieverIp);
        jsonRequest.put("port", port);
        jsonRequest.put("operation", operation);
        jsonRequest.put("arguments", arguments);
        return jsonRequest.toString(4); 
    }
    
    public boolean handleRequest(String jsonMessage){
        boolean result = false;
        String response, operation, arguments;
        String [] fields;
        JSONObject jsonRequest;
        String ip;
        int port;
        jsonRequest = new JSONObject(jsonMessage);
        ip = jsonRequest.getString("ip");
        port = jsonRequest.getInt("port");
        clientSocket = new ClientSocket(ip,port);
        if(clientSocket.sendMessage().equals("SUCCESS")){
            operation = jsonRequest.getString("operation");
            switch (operation) { 
                case "ADD":
                    arguments = jsonRequest.getString("arguments");
                    fields = arguments.split(",");
                    result = management.addProductToDB(
                             Integer.parseInt(fields[0]),
                             fields[1].replace("##",","),
                             Integer.parseInt(fields[2]));
                break;
                case "SELL":
                    arguments = jsonRequest.getString("arguments");
                    fields = arguments.split(",");
                    result = management.addProductToDB(Integer.parseInt(fields[0]),fields[1].replace("##",","),Integer.parseInt(fields[2]));
                break;
                case "RESTOCK":
                    arguments = jsonRequest.getString("arguments");
                    fields = arguments.split(",");
                    result = management.addProductToDB(Integer.parseInt(fields[0]),fields[1].replace("##",","),Integer.parseInt(fields[2]));
                break;
                default:
                }
            }
            
            outgoing.remove(0);   
        return result;
    }
    
}
