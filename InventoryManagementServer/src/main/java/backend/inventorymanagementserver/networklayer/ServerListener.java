/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementserver.networklayer;

/**
 *
 * @author pier
 */
public class ServerListener implements Runnable{
    private RequestHandler handler;
    public int port;

    public ServerListener(RequestHandler handler, int port) {
        this.handler = handler;
        this.port = port;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
