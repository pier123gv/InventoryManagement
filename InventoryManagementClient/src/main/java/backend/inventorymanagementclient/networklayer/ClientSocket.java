/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementclient.networklayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *Clase para conectarse con el servidor
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
    
    /**
    * Establece una conexión segura con el servidor usando SSL.
    * Carga la configuración SSL desde `configuration.properties`, configura los 
    * certificados y crea un `SSLSocket` para conectarse al servidor.
    * @throws IOException Si ocurre un error al cargar la configuración o al conectar.
    */
    public void connect() throws IOException {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File("configuration.properties")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sslRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
        String sslPassword = p.getProperty("SSL_PASSWORD");
        System.setProperty("javax.net.ssl.keyStore",sslRoute);
        System.setProperty("javax.net.ssl.keyStorePassword",sslPassword);
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.trustStore", sslRoute);
        System.setProperty("javax.net.ssl.trustStorePassword", sslPassword);
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
        
        SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        clientSocket = (SSLSocket) socketFactory.createSocket(serverAddress, port);
        clientSocket.setSoTimeout(30000);
        System.out.println("Connection established");
        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = new DataOutputStream(clientSocket.getOutputStream());
    }
    
    /**
    * Envía un mensaje JSON al servidor a través de una conexión SSL.
    * Establece la conexión, envía el mensaje y espera una respuesta del servidor.
    * Maneja excepciones de tiempo de espera, cierre inesperado y errores de E/S.
    * @param jsonMessage El mensaje en formato JSON a enviar.
    * @return La respuesta del servidor como una cadena de texto.
    */
    public String sendMessage(String jsonMessage) {
        String response = "Error";
        try {
            connect();
            System.out.println("Sending: " + jsonMessage);
            outputStream.writeUTF(jsonMessage);
            outputStream.flush();
            response = inputStream.readUTF();
            System.out.println("Response: " + response);
        } catch (java.net.SocketTimeoutException ex) {
            System.out.println("Timeout waiting for server response: " + ex.getMessage());      
        } catch (java.io.EOFException ex) {
            System.out.println("Server closed connection unexpectedly: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
        } finally {
            closeConnection();
        }
        return response;
    }
    
    /**
     * Cierra la conexión con el servidor.
     * Libera los recursos asociados al socket y los flujos de entrada/salida.
     * Maneja posibles errores al cerrar la conexión.
     */
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
