/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package backend.inventorymanagementserver;

import backend.inventorymanagementserver.model.ProductDatabase;
import backend.inventorymanagementserver.networklayer.RequestHandlerServer;
import backend.inventorymanagementserver.utils.CSVUtils;

/**
 *
 * @author isabe, pier
 */
public class InventoryManagementServer {
    private final int port = 9090;
    private ProductDatabase db;
    private CSVUtils exporter;
    private String fileName = "database.csv";
    private RequestHandlerServer handler;
    public InventoryManagementServer() {
        db = new ProductDatabase();
        exporter = new CSVUtils(this,fileName);
        db.populate(exporter.readCSVToString());
        handler = new RequestHandlerServer(this, port);

    }
    
    public boolean exportCSV() {
        return exporter.exportCSV();
    }
    
    public void start(){
        handler.start();
    }
    
    
    public String addProductToDB(String productName, int productStock, float productPrice, String productDescription){
        if (productStock < 0 || productPrice<0) return "No se aceptan precios ni stock negativos";
        return db.addProduct(productName,productStock,productPrice,productDescription);
    }
    
    public boolean deleteProductInDB(Object productKey){
        return db.deleteProduct(productKey);
    }
    public String deleteProductNameInDB(String name){
        return db.deleteProductName(name);
    }
    public String sellProductInDBbyName(String productName, int sellAmount){
        return db.sellProductByName(productName, sellAmount);
    }
    public String sellProductInDBbyCode(int productCode, int sellAmount){
        return db.sellProductByCode(productCode,sellAmount);
    }
    public String editProductInDB (String oldName, String newProductName, int newProductStock, float newProductPrice, String newProductDescription){
        return db.editProduct(oldName, newProductName, newProductStock, newProductPrice, newProductDescription);
    }
    public String showDatabaseContent(){
        return db.toStringLineJump(); //temporarily with line jumps.
    }
    public String searchProductInDatabase(int productCode){
        return db.productInfo(productCode);
    }

    public String testConnection(){
        return "Connection successfull";
    }
    public ProductDatabase getDB(){
        return this.db;
    }
    // Main
    public static void main(String[] args) {
        InventoryManagementServer management = new InventoryManagementServer();
        management.start();
    }
}
