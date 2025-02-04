/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package backend.inventorymanagementserver;

import backend.inventorymanagementserver.model.ProductDatabase;
import backend.inventorymanagementserver.networklayer.RequestHandlerServer;
import backend.inventorymanagementserver.userinterface.CommandLineInterface;
import backend.inventorymanagementserver.utils.CSVUtils;

/**
 *
 * @author isabe, pier
 */
public class InventoryManagementServer {
    private final int port = 9090;
    private ProductDatabase db;
    private CommandLineInterface ui;
    private CSVUtils exporter;
    private String fileName = "database.csv";
    private RequestHandlerServer handler;
    public InventoryManagementServer() {
        db = new ProductDatabase();
        ui = new CommandLineInterface(this);
        exporter = new CSVUtils(this,fileName);
        db.populate(exporter.readCSVToString());
        handler = new RequestHandlerServer(this, port);
        handler.start();
    }
    
    public boolean exportCSV() {
        return exporter.exportCSV();
    }
    
    public void start(){
        Thread uiThread = new Thread(ui);
        uiThread.start();
    }
    
    
    public boolean addProductToDB(String productName, int productStock, float productPrice, String productDescription){
        return db.addProduct(productName,productStock,productPrice,productDescription);
    }
    
    public boolean deleteProductInDB(Object productKey){
        return db.deleteProduct(productKey);
    }

    public ProductDatabase getDb() {
        return db;
    }
    public boolean editProductInDB (Object key,String productName, int productStock, float productPrice, String productDescription){
        return db.editProduct(key,productName,productStock,productPrice,productDescription);
    }
    public String showDatabaseContent(){
        return db.toStringLineJump(); //temporarily with line jumps.
    }

    public String testConnection(){
        return "Connection successfull";
    }
    // Main
    public static void main(String[] args) {
        InventoryManagementServer management = new InventoryManagementServer();
        management.start();
    }
}
