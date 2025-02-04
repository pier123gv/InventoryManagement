/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package backend.inventorymanagementclient;

import backend.inventorymanagementclient.networklayer.RequestHandlerClient;
import backend.inventorymanagementclient.utils.CSVExporter;

/**
 *
 * @author isabe, pier
 */
public class InventoryManagementClient {
    
    private final int port = 9090;
    private final String serverAddress = "127.0.0.1";
    private final String fileName = "database.csv";
    
    private CSVExporter exporter;
    private RequestHandlerClient handler;
    
    public InventoryManagementClient() {
        exporter = new CSVExporter(this,fileName);
        handler = new RequestHandlerClient(this,serverAddress,port);
        
    }
    
    public boolean exportCSV() {
        String[] args = {};
        String request = handler.formatRequest("REQUEST", args);
        String productos = handler.handleRequest(request);
        return exporter.exportCSV(productos);
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
    
    public String modifyProductNameInDB ( String OldName, String productName, int productStock, float productPrice, String productDescription){
        String[] args = {OldName, productName,String.valueOf(productStock),String.valueOf(productPrice), productDescription};
        String request = handler.formatRequest("MODIFY-PRODUCT", args);
        return handler.handleRequest(request);
    }
    
    public String deleteProductFromDB (int productCode){
        String[] args = {String.valueOf(productCode)};
        String request = handler.formatRequest("DELETE", args);
        return handler.handleRequest(request);
    }
    
    public String requestProductfromDB (int productCode){
        String[] args = {""+productCode};
        String request = handler.formatRequest("REQUESTPRODUCT", args);
        return handler.handleRequest(request);
    }
        public String deleteNameProductfromDB (String productName){
        String[] args = {productName};
        String request = handler.formatRequest("DELETEPRODUCTNAME", args);
        return handler.handleRequest(request);
    }
        
    public String requestDB (){
        String[] args = {};
        String request = handler.formatRequest("REQUEST", args);
        return handler.handleRequest(request);
    }
    
    public String testConnection(){
        String[] args = {};
        String request = handler.formatRequest("TEST", args);
        return handler.handleRequest(request);
    }
}
