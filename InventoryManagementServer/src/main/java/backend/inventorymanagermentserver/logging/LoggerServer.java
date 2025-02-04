/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagermentserver.logging;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author pier
 */
public class LoggerServer {
    private String filePath;
    public LoggerServer(String fileName) {
        this.filePath = fileName;
    }
    
    public boolean createLogEntry(String entry) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(entry).append("\n");
            System.out.println("Log entry added successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to the log file: " + e.getMessage());
        }
        return true;
    }
}
