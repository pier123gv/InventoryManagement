package backend.inventorymanagementserver.networklayer;

import backend.inventorymanagementserver.InventoryManagementServer;
import org.json.JSONObject;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RequestHandlerServer {
    private InventoryManagementServer management;
    private int port;

    public RequestHandlerServer(InventoryManagementServer management, int port) {
        this.management = management;
        this.port = port;
    }

    public String handleRequest(String jsonMessage) {
        String response = "ERROR", operation, productName, productDescription;
        int productCode, productStock, productAmount;
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
                response = management.addProductToDB(productName, productStock, productPrice, productDescription);
                break;
            case "SELLBYCODE":
                productCode = Integer.parseInt(jsonRequest.getString("argument0"));
                productAmount = Integer.parseInt(jsonRequest.getString("argument1"));
                response = management.sellProductInDBbyCode(productCode, productAmount);
                break;
            case "SELLBYNAME":
                productName = jsonRequest.getString("argument0");
                productAmount = Integer.parseInt(jsonRequest.getString("argument1"));
                response = management.sellProductInDBbyName(productName, productAmount);
                break;
            case "EDIT":
                productCode = Integer.parseInt(jsonRequest.getString("argument0"));
                productName = jsonRequest.getString("argument1");
                productStock = Integer.parseInt(jsonRequest.getString("argument2"));
                productPrice = Float.parseFloat(jsonRequest.getString("argument3"));
                productDescription = jsonRequest.getString("argument4");
                response = management.editProductInDB(productCode, productName, productStock, productPrice, productDescription);
                break;
            case "REQUEST":
                response = management.showDatabaseContent();
                break;
            case "REQUESTPRODUCT":
                productCode = Integer.parseInt(jsonRequest.getString("argument0"));
                response = management.searchProductInDatabase(productCode);
                break;
                
            case "DELETEPRODUCTNAME":
                String pName = jsonRequest.getString("argument0");
                response = management.deleteProductNameInDB(pName);
                break;
            case "TEST":
                response = management.testConnection();
                break;
            default:
        }
        return response;
    }

    public void start() {
        try {
            // Configuración SSL
            Properties p = new Properties();
            p.load(new FileInputStream("configuration.properties"));
            String sslRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
            String sslPassword = p.getProperty("SSL_PASSWORD");

            System.setProperty("javax.net.ssl.keyStore", sslRoute);
            System.setProperty("javax.net.ssl.keyStorePassword", sslPassword);
            System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
            System.setProperty("javax.net.ssl.trustStore", sslRoute);
            System.setProperty("javax.net.ssl.trustStorePassword", sslPassword);
            System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");

            // Crear el socket del servidor SSL
            SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) socketFactory.createServerSocket(port);
            System.out.println("Server listening on port: " + port);

            while (true) {
                // Aceptar una nueva conexión
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                System.out.println("Nueva conexión: " + clientSocket.getInetAddress());

                // Manejar la conexión en un hilo separado
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void handleClient(SSLSocket clientSocket) {
        try (DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream())) {

            // Leer el mensaje del cliente
            String clientMessage = inputStream.readUTF();
            System.out.println("Mensaje recibido: " + clientMessage);

            // Procesar el mensaje
            String response = handleRequest(clientMessage);
            System.out.println("Procesando mensaje: " + clientMessage + " -> Respuesta: " + response);

            // Enviar la respuesta al cliente
            outputStream.writeUTF(response);
            outputStream.flush();

            // Cerrar la conexión
            clientSocket.close();
            System.out.println("Conexión cerrada: " + clientSocket.getInetAddress());
        } catch (IOException ex) {
            System.out.println("Error manejando cliente: " + ex.getMessage());
        }
    }
}