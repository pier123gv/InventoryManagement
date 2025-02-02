/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementserver.model;

import java.util.ArrayList;

/**
 *
 * @author isabe, pier
 */
public class ProductDatabase {
    private ArrayList<Product> Inventory = new ArrayList();
    
    /**
     * Look's up a product in the Inventory by name.
     * @param name The name of the product to lookup.
     * @return The productCode if it exists or -1 if it doesn't.
     */
    public int searchProductCode(String productName){
        for(Product product : Inventory){
            if(product.getProductName().equals(productName)) return product.getProductCode();
        }
        return -1;
    }
    
    /**
     * Look's up a product in the Inventory by name.
     * @param key The productName or productCode of the product to lookup.
     * @return The index if it exists or -1 if it doesn't.
     */
    private int lookupProduct(Object key) {
        if (key instanceof Integer) {
            for (int index = 0; index < Inventory.size(); index++) {
                if (Inventory.get(index).getProductCode()==(int)key) return index;
            }
        }
        else if (key instanceof String){
            for (int index = 0; index < Inventory.size(); index++) {
                if (Inventory.get(index).getProductName().equals(key)) return index;
            }
        }
        return -1;
    }

    private int lastProductCode(){
        int largest = 0;
        for(int index = 0; index < Inventory.size(); index++){
            int productCode = Inventory.get(index).getProductCode();
            if(productCode>largest) largest = productCode;
        }
        return largest;
    }
    
    /**
     * Adds a product to the inventory using the lowest free productCode and 
     * validating there's no entry for that product.
     * @param name The new product's name.
     * @param amount The new product's amount.
     * @return True if the entry was successful, False otherwise. 
     */
    public boolean addProduct(String productName, int productStock){
        if(searchProductCode(productName) !=-1) return false;
        return Inventory.add(new Product(lastProductCode()+1,productName,productStock));
    }

    public ArrayList<Product> getInventory() {
        return Inventory;
    }
    
    public boolean sellProduct(Object key, int productAmount){ // Recieves either code or name
        int productIndex = lookupProduct(key);
        return (productIndex == -1) ? false : Inventory.get(productIndex).sellProduct(productAmount);
    }
    
    public boolean restockProduct(Object key, int productAmount){ // Recieves either code or name
        int productIndex = lookupProduct(key);
        return (productIndex == -1) ? false : Inventory.get(productIndex).restock(productAmount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Product product : Inventory) sb.append(product.toString()+"\n");
        return sb.toString();
    }
    
}
