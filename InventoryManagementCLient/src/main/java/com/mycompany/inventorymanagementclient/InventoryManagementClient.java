/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.inventorymanagementclient;
import com.mycompany.inventorymanagementclient.model.Product;
import com.mycompany.inventorymanagementclient.model.ProductDatabase;
import com.mycompany.inventorymanagementclient.userinterface.ProvisionaryCommandLineInterface;
import com.mycompany.inventorymanagementclient.utils.CSVExporter;
import java.util.ArrayList;

/**
 *
 * @author isabe, pier
 */
public class InventoryManagementClient {
    private ProductDatabase db;
    private ProvisionaryCommandLineInterface ui;
    private CSVExporter exporter;
    private String fileName = "database.csv";
    public InventoryManagementClient() {
        db = new ProductDatabase();
        ui = new ProvisionaryCommandLineInterface(this);
        exporter = new CSVExporter(this,fileName);
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
        InventoryManagementClient management = new InventoryManagementClient();
        management.start();
    }
}
