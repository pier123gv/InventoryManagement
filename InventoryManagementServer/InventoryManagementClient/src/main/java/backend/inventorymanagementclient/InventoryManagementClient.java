/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package backend.inventorymanagementclient;

import backend.inventorymanagementclient.model.ProductDatabase;
import backend.inventorymanagementclient.networklayer.ClientListener;
import backend.inventorymanagementclient.networklayer.ClientSocket;
import backend.inventorymanagementclient.networklayer.RequestHandler;
import backend.inventorymanagementclient.userinterface.ProvisionaryComandLineInterface;
import backend.inventorymanagementclient.utils.CSVExporter;

/**
 *
 * @author isabe, pier
 */
public class InventoryManagementClient {
    
    private final int port = 9090;
    private final String serverAddress = "";
    private ProductDatabase db;
    private ProvisionaryComandLineInterface ui;
    private CSVExporter exporter;
    private String fileName = "database.csv";
    private ClientListener listener;
    private RequestHandler handler;
    public InventoryManagementClient() {
        db = new ProductDatabase();
        ui = new ProvisionaryComandLineInterface(this);
        exporter = new CSVExporter(this,fileName);
        handler = new RequestHandler(this);
        listener = new ClientListener(handler,port);
        
    }
    
    public boolean exportCSV() {
        return exporter.exportCSV();
    }
    
    public void start(){
        Thread uiThread = new Thread(ui);
        uiThread.start();
    }
    
    public boolean addProductToDB(String productName, int productStock){
        return db.addProduct(productName, productStock);
    }
    public boolean addProductToDB(int productCode, String productName, int productStock){
        return db.addProduct(productCode, productName, productStock);
    }
    public boolean sellProductInDB(Object productKey, int amount){

        return db.sellProduct(productKey, amount);
    }

    public ProductDatabase getDb() {
        return db;
    }
    public boolean restockProductInDB (Object productKey, int amount){
        return db.restockProduct(productKey, amount);
    }
    public boolean populateDB(String content){
        return db.populate(content);
    }
    
    
    
    
    
    
    
    // Main
    public static void main(String[] args) {
        InventoryManagementClient management = new InventoryManagementClient();
        management.start();
    }
}
