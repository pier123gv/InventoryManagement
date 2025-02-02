/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementclient.utils;

import com.mycompany.inventorymanagementclient.InventoryManagementClient;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isabe, pier
 */
public class CSVExporter {
    private String fileName;
    private final InventoryManagementClient management;

    public CSVExporter(InventoryManagementClient management, String fileName) {
        this.management = management;
        this.fileName = fileName;
    }
    
    public boolean exportCSV() {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append(management.getDb().toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CSVExporter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
