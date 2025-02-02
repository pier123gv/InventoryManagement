/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package backend.inventorymanagementserver;

import backend.inventorymanagementserver.model.ProductDatabase;
import backend.inventorymanagementserver.userinterface.CommandLineInterface;
import backend.inventorymanagementserver.utils.CSVUtils;

/**
 *
 * @author isabe, pier
 */
public class InventoryManagementServer {
    private ProductDatabase db;
    private CommandLineInterface ui;
    private CSVUtils exporter;
    private String fileName = "database.csv";
    public InventoryManagementServer() {
        db = new ProductDatabase();
        ui = new CommandLineInterface(this);
        exporter = new CSVUtils(this,fileName);
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
    
    public boolean sellProductInDB(Object productKey, int amount){
        return db.sellProduct(productKey, amount);
    }

    public ProductDatabase getDb() {
        return db;
    }
    public boolean restockProductInDB (Object productKey, int amount){
        return db.restockProduct(productKey, amount);
    }
    // Main
    public static void main(String[] args) {
        InventoryManagementServer management = new InventoryManagementServer();
        management.start();
    }
}
