/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementserver.utils;

import backend.inventorymanagementserver.InventoryManagementServer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isabe, pier
 */
public class CSVUtils {
    private String fileName;
    private final InventoryManagementServer management;

    public CSVUtils(InventoryManagementServer management, String fileName) {
        this.management = management;
        this.fileName = fileName;
    }
    
    public boolean exportCSV() {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append(management.getDb().toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CSVUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
