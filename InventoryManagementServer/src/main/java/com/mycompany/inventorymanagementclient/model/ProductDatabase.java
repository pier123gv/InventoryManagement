/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementclient.model;

import java.util.ArrayList;

/**
 *
 * @author isabe, pier
 */
public class ProductDatabase {
    private final ArrayList<Product> Inventory = new ArrayList();
    /**
     * Look's up a product in the Inventory by name.
     * @param name The name of the product to lookup.
     * @return The productCode if it exists or 0 if it doesn't.
     */
    public int searchProduct(String name){
        for(Product product : Inventory){
            if(product.getProductName().equals(name)) return product.getProductCode();
        }
        return 0;
    }
    public boolean addProduct(String name, int amount){
        if(searchProduct(name)==0) return false;
        int lastIndex = Inventory.isEmpty() ? 0 : Inventory.get(Inventory.size() - 1).getProductCode();
        Product newProduct = new Product(lastIndex+1,name,amount);
        return Inventory.add(newProduct);
    }

    public ArrayList<Product> getInventory() {
        return Inventory;
    }
    
}
