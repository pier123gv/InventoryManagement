/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementserver.networklayer;

import backend.inventorymanagementserver.InventoryManagementServer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
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
public class RequestHandlerServer {
    private InventoryManagementServer management;
    private int port;
    private static final BlockingQueue<ClientRequest> messageQueue = new LinkedBlockingQueue<>();

    public RequestHandlerServer(InventoryManagementServer management, int port) {
        this.management = management;
        this.port = port;
    }

    public String handleRequest(String jsonMessage){
        String response = "ERROR", operation, productName, productDescription;
        int productCode,productStock, productAmount;
        float productPrice;
        JSONObject jsonRequest;
        jsonRequest = new JSONObject(jsonMessage);
            operation = jsonRequest.getString("operation");
            switch (operation) { 
                case "ADD":
                    productName = jsonRequest.getString("argument0");
                    productStock = Integer.parseInt(jsonRequest.getString("argument1"));
                    productPrice = Float.parseFloat(jsonRequest.getString("argument2"));
                    productDescription = jsonRequest.getString("argument3");
                    response = management.addProductToDB(productName,productStock,productPrice,productDescription);
                break;
                case "SELL":
                    productCode = Integer.parseInt(jsonRequest.getString("argument0"));
                    productAmount = Integer.parseInt(jsonRequest.getString("argument1"));
                    response = "NOT IMPLEMENTED YET";
                break;
                case "RESTOCK":
                    //result = management.addProductToDB(Integer.parseInt(fields[0]),fields[1].replace("##",","),Integer.parseInt(fields[2]));
                break;
                case "TEST":
                    response = management.testConnection();
                    break;
                default:
                }
            
        return response;
    }

    public void start() {
        ExecutorService clientHandlerPool = Executors.newFixedThreadPool(10); // Pool para manejar clientes

        try {
            Properties p = new Properties();
            p.load(new FileInputStream(new File("configuration.properties")));     
            String response;
        
            String sslRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
            String sslPassword = p.getProperty("SSL_PASSWORD");
            System.setProperty("javax.net.ssl.keyStore",sslRoute);
            System.setProperty("javax.net.ssl.keyStorePassword",sslPassword);
            System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
            System.setProperty("javax.net.ssl.trustStore", sslRoute);
            System.setProperty("javax.net.ssl.trustStorePassword", sslPassword);
            System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
        
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
            Logger.getLogger(RequestHandlerServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RequestHandlerServer.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(RequestHandlerServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}