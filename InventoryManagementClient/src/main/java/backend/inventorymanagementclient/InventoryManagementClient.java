/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package backend.inventorymanagementclient;

import backend.inventorymanagementclient.networklayer.RequestHandlerClient;
import backend.inventorymanagementclient.userinterface.ImprovedCommandLineInterface;
import backend.inventorymanagementclient.utils.CSVExporter;

/**
 *
 * @author isabe, pier
 */
public class InventoryManagementClient {
    
    private final int port = 9090;
    private final String serverAddress = "172.24.157.33";
    private final String fileName = "database.csv";
    
    private ImprovedCommandLineInterface ui;
    private CSVExporter exporter;
    private RequestHandlerClient handler;
    
    public InventoryManagementClient() {
        ui = new ImprovedCommandLineInterface(this);
        exporter = new CSVExporter(this,fileName);
        handler = new RequestHandlerClient(this,serverAddress,port);
        
    }
    
    public boolean exportCSV() {
        return exporter.exportCSV();
    }
    
    public void start(){
        Thread uiThread = new Thread(ui);
        uiThread.start();
    }
    
    public String addProductToDB(String productName, int productStock, float productPrice, String productDescription){
        String[] args = {productName,String.valueOf(productStock),String.valueOf(productPrice), productDescription} ;
        String request = handler.formatRequest("ADD", args);
        return handler.handleRequest(request);
    }
    
    public String deleteProductInDB(int productCode, int amount){
        String[] args = {String.valueOf(productCode),String.valueOf(amount)};
        String request = handler.formatRequest("SELL", args);
        return handler.handleRequest(request);
    }
    
    public String modifyProductStockInDB (int productCode, int newAmount){
        String[] args = {String.valueOf(productCode),String.valueOf(newAmount)};
        String request = handler.formatRequest("MODIFY-STOCK", args);
        return handler.handleRequest(request);
    }
    
    public String modifyProductNameInDB (int productCode, String newName){
        String[] args = {String.valueOf(productCode),newName};
        String request = handler.formatRequest("MODIFY-NAME", args);
        return handler.handleRequest(request);
    }
    
    public String deleteProductFromDB (int productCode){
        String[] args = {String.valueOf(productCode)};
        String request = handler.formatRequest("DELETE", args);
        return handler.handleRequest(request);
    }
    
    public String requestDB (int productCode){
        String[] args = {};
        String request = handler.formatRequest("REQUEST", args);
        return handler.handleRequest(request);
    }
    
    public String testConnection(){
        String[] args = {};
        String request = handler.formatRequest("TEST", args);
        return handler.handleRequest(request);
    }
    
    
    
    
    
    
    // Main
    public static void main(String[] args) {
        InventoryManagementClient management = new InventoryManagementClient();
        management.start();
    }
}
