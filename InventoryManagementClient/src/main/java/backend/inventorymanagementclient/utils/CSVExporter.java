/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementclient.utils;

import backend.inventorymanagementclient.InventoryManagementClient;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 ** Clase responsable de exportar productos a un archivo CSV.
 * Permite guardar la información de productos en un archivo de texto con formato CSV.
 * @author isabe, pier
 */
public class CSVExporter {
    private String fileName;
    private final InventoryManagementClient management;

    public CSVExporter(InventoryManagementClient management, String fileName) {
        this.management = management;
        this.fileName = fileName;
    }
    
     /**
     * Exporta los productos a un archivo CSV.
     * Escribe el contenido de la cadena proporcionada en un archivo CSV.
     * @param products Cadena con los datos de los productos en formato CSV.
     * @return {@code true} si la exportación fue exitosa, {@code false} en caso de error.
     */
    public boolean exportCSV(String products) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append(products);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CSVExporter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
