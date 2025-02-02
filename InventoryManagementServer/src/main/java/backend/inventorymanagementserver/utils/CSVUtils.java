/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementserver.utils;

import backend.inventorymanagementserver.InventoryManagementServer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isabe, pier
 */
public class CSVUtils {
    private String filePath;
    private final InventoryManagementServer management;

    public CSVUtils(InventoryManagementServer management, String fileName) {
        this.management = management;
        this.filePath = fileName;
    }
    
    public boolean exportCSV() {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append(management.getDb().toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CSVUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
        public String readCSVToString() {
        StringBuilder content = new StringBuilder();

        try (InputStream inputStream = CSVUtils.class.getClassLoader().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");  // Append each line with a newline character
            }

        } catch (IOException | NullPointerException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return content.toString();  // Return the entire content as a String

    }
}
