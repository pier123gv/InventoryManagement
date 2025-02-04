/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementserver.networklayer;

import backend.inventorymanagementserver.InventoryManagementServer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import org.json.JSONObject;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author pier
 */
public class RequestHandler {
    private InventoryManagementServer management;
    private int port;
    private static final BlockingQueue<ClientRequest> messageQueue = new LinkedBlockingQueue<>();

    public RequestHandler(InventoryManagementServer management) {
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
    
    public String handleRequest(String jsonMessage){
        String response = "Error", operation, arguments;
        String [] fields;
        JSONObject jsonRequest;
        String ip;
        int port;
        jsonRequest = new JSONObject(jsonMessage);
            operation = jsonRequest.getString("operation");
            switch (operation) { 
                case "ADD":
                    arguments = jsonRequest.getString("arguments");
                    fields = arguments.split(",");
                    //response = management.addProductToDB(Integer.parseInt(fields[0]),fields[1].replace("##",","),Integer.parseInt(fields[2]));
                break;
                case "SELL":
                    arguments = jsonRequest.getString("arguments");
                    fields = arguments.split(",");
                    //result = management.addProductToDB(Integer.parseInt(fields[0]),fields[1].replace("##",","),Integer.parseInt(fields[2]));
                break;
                case "RESTOCK":
                    arguments = jsonRequest.getString("arguments");
                    fields = arguments.split(",");
                    //result = management.addProductToDB(Integer.parseInt(fields[0]),fields[1].replace("##",","),Integer.parseInt(fields[2]));
                break;
                default:
                }
            
        return response;
    }

    public void start() {
        ExecutorService clientHandlerPool = Executors.newFixedThreadPool(10); // Pool para manejar clientes

        try {
            // Configuración del socket SSL
            SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) socketFactory.createServerSocket(port);
            System.out.println("Server listening on port: " + port);

            // Iniciar el hilo para procesar los mensajes
            new Thread(this::processMessages).start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected from: " + clientSocket.getInetAddress());

                clientHandlerPool.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try (DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream())) {

            String clientMessage = inputStream.readUTF();
            System.out.println("Client message: " + clientMessage);

            messageQueue.put(new ClientRequest(clientMessage, clientSocket, outputStream));

        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processMessages() {
        while (true) {
            try {
                // Extraer y procesar la petición en orden de llegada
                ClientRequest request = messageQueue.take();
                String clientMessage = request.getMessage();
                Socket clientSocket = request.getClientSocket();
                DataOutputStream outputStream = request.getOutputStream();

                // Simulación del procesamiento del mensaje
                String response = handleRequest(clientMessage);
                System.out.println("Processing message: " + clientMessage + " -> Response: " + response);

                // Enviar respuesta al cliente
                outputStream.writeUTF(response);
                outputStream.flush();

                // Cerrar la conexión después de enviar la respuesta
                clientSocket.close();
                System.out.println("Connection closed");

            } catch (IOException ex) {
                System.out.println("Error processing message: " + ex.getMessage());
            } catch (InterruptedException ex) {
                Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}