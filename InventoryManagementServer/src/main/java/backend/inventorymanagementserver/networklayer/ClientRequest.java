/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementserver.networklayer;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author pier
 */
class ClientRequest {
    private final String message;
    private final Socket clientSocket;
    private final DataOutputStream outputStream;

    public ClientRequest(String message, Socket clientSocket, DataOutputStream outputStream) {
        this.message = message;
        this.clientSocket = clientSocket;
        this.outputStream = outputStream;
    }

    public String getMessage() {
        return message;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }
}

