/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementclient.utils;

import com.mycompany.inventorymanagementclient.model.Product;
import com.mycompany.inventorymanagementclient.model.ProductDatabase;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author isabe, pier
 */
public class CSVExporter {
    private String fileName = "database.csv";
    private final ArrayList<Product> Inventory;

    public CSVExporter(ProductDatabase DB) {
        this.Inventory = DB.getInventory();
    }
    
    public void exportarCSV() throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("productCode,productName,productAmount\n");
            for (Product product : Inventory) {
                writer.append(product.toString()).append("\n");
            }
        }
    }
}
