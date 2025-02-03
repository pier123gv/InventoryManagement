/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementclient.networklayer;

import backend.inventorymanagementclient.InventoryManagementClient;
import java.util.ArrayList;
import org.json.JSONObject;
/**
 *
 * @author pier
 */
public class RequestHandler implements Runnable{
    private InventoryManagementClient management;
    
    private ArrayList<String> outgoing;
    private ArrayList<String> incomming;
    private ClientSocket clientSocket;
    
    public RequestHandler(InventoryManagementClient management) {
        this.management = management;
        outgoing = new ArrayList();
        incomming = new ArrayList();
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

    public void createRequest(String type, String senderIp, String recieverIp, int port, String operation, String arguments){
        outgoing.add(formatRequest(senderIp,recieverIp,port,operation,arguments));
    }
    
    public boolean handleRequest(){
        boolean result = false;
        String response, operation, arguments;
        String [] fields;
        JSONObject jsonRequest;
        String ip;
        int port;
        if(!incomming.isEmpty()){
            jsonRequest = new JSONObject(outgoing.get(0));
            ip = jsonRequest.getString("ip");
            port = jsonRequest.getInt("port");
            clientSocket = new ClientSocket(ip,port);
            if(clientSocket.sendMessage(outgoing.get(0)).equals("SUCCESS")){
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
                    default:
            }
            }
            
            outgoing.remove(0);   
        }
        else{
            jsonRequest = new JSONObject(incomming.get(0));
            operation = jsonRequest.getString("operation");
            switch (operation) { 
                case "RELOAD":
                    result = management.populateDB(jsonRequest.getString("arguments"));
                break;
                default:
            }
            incomming.remove(0); 
        }
        return result;
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
