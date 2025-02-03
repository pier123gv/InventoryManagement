/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementclient.networklayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author pier
 */
public class ClientSocket {

    private String serverAddress;
    private int port;
    private SSLSocket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public ClientSocket(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void connect() throws IOException {
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        clientSocket = (SSLSocket) socketFactory.createSocket(serverAddress, port);
        System.out.println("Connection established");
        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = new DataOutputStream(clientSocket.getOutputStream());
    }

    public String sendMessage(String jsonMessage) {
        String response = "Error";
        try {
            connect();
            System.out.println("Sending: " + jsonMessage);
            outputStream.writeUTF(jsonMessage);
            response = inputStream.readUTF();
            System.out.println("Response: " + response);
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
        } finally {
            closeConnection();
        }
        return response;
    }

    public void closeConnection() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException ex) {
            System.out.println("Error closing connection: " + ex.getMessage());
        }
    }
    
    
}
