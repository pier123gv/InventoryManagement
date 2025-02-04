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
 *Encargada de manejar solicitudes al servidor.
 * @author pier
 * Esta clase se encarga de formatear las solicitudes en formato JSON, 
 * enviarlas al servidor mediante un socket seguro y devolver la respuesta obtenida.
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
    
     /**
     * Formatea una solicitud en formato JSON.
     * Crea un objeto JSON con la operación y sus argumentos.
     * @param operation Nombre de la operación a ejecutar.
     * @param arguments Lista de argumentos de la operación.
     * @return La solicitud en formato JSON como una cadena de texto.
     */
    public String formatRequest(String operation, String[] arguments) {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("operation", operation);
        for (int i = 0; i< arguments.length; i++){
            jsonRequest.put("argument"+i, arguments[i]);
        }
        
        return jsonRequest.toString(4); 
    }
    
    /**
     * Envía una solicitud al servidor y devuelve la respuesta.
     * Establece una conexión con el servidor, envía el mensaje JSON 
     * y recibe la respuesta del servidor.
     * @param jsonMessage Mensaje en formato JSON a enviar.
     * @return La respuesta del servidor como una cadena de texto.
     */
    public String handleRequest(String jsonMessage){      
        String response;
      
        clientSocket = new ClientSocket(serverAddress,port);
        response = clientSocket.sendMessage(jsonMessage);
        
        return response;
    }
}
