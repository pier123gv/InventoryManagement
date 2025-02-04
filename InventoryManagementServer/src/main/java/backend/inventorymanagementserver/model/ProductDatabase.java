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
    public String addProduct(String productName, int productStock, float productPrice, String productDescription){
        if(searchProductCode(productName) !=-1) return "PRODUCTO EXISTENTE";
        Inventory.add(new Product(lastProductCode()+1,productName,productStock, productPrice, productDescription));
        return "SUCCESS";
    }
    
    public String sellProductByName(String productName, int sellAmount){
        int index = lookupProduct(productName);
        if(index ==-1) return "PRODUCTO INEXISTENTE";
        return Inventory.get(index).sellProduct(sellAmount);
    }
    
    public String sellProductByCode(int productCode, int sellAmount){
        int index = lookupProduct(productCode);
        if(index ==-1) return "PRODUCTO INEXISTENTE";
        return Inventory.get(index).sellProduct(sellAmount);
    }
    public ArrayList<Product> getInventory() {
        return Inventory;
    }
    
    public boolean deleteProduct(Object key){ // Recieves either code or name
int productIndex = lookupProduct(key);
    
    if (productIndex == -1) return false; // Producto no encontrado

    Inventory.remove(productIndex);

    return true;
    }
    
    public String deleteProductName(String key){ // Recieves either code or name
    int productIndex = lookupProduct(key);
    
    if (productIndex == -1) return "No encontrado"; // Producto no encontrado

    Inventory.remove(productIndex);

    return "SUCCESS";
    }
    
public String editProduct(String oldName, String newName, Integer newStock, Float newPrice, String newDescription) {
    int productIndex = lookupProduct(oldName);
    
    if (productIndex == -1) return "PRODUCTO INEXISTENTE"; // Producto no encontrado

    Product product = Inventory.get(productIndex);
    
    if (newName != null && !newName.trim().isEmpty()) {
        product.setProductName(newName);
    }
    if (newStock != null && newStock >= 0) {
        product. setProductStock(newStock);
    }
    if (newPrice != null && newPrice >= 0) {
        product.setProductPrice(newPrice);
    }
    if (newDescription != null && !newDescription.trim().isEmpty()) {
        product.setProductDescription(newDescription);
    }

    return "SUCCESS";
}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Product product : Inventory) sb.append(product.toString());
        return sb.toString();
    }
    public String toStringLineJump() {
        StringBuilder sb = new StringBuilder();
        for(Product product : Inventory) sb.append(product.toString()+"\n");
        return sb.toString();
    }
    /**
     * Se encarga de popular la base de datos recibiendo el contenido separado por comas similar al formato csv
     * Cambia las , que hay dentro de los argumentos que necesita un producto,ya que se convierten en ## cuando los vamos a guardar
     * @param content recibe el contenido en String con formato csv
     */
    public void populate(String content){
        System.out.println("Populating db");
        String data[] = content.split(",");
        for(int i = 0; i<data.length & data.length>=5; i=i+5){
            try {
                int productCode = Integer.parseInt(data[i].trim()); 
                String productName = data[i + 1].trim().replace("##", ","); 
                int productStock = Integer.parseInt(data[i + 2].trim());
                float productPrice = Float.parseFloat(data[i + 3].trim()); 
                String productDescription = data[i + 4].trim().replace("##", ","); 
                Product newProduct = new Product(productCode, productName, productStock, productPrice, productDescription);
                Inventory.add(newProduct);
            }catch (NumberFormatException e) {
                System.out.println("Error al procesar el producto en la línea: " + (i / 5 + 1));        
            }
        System.out.println("File lines: "+data.length/3 + " Entries: "+Inventory.size());
        }
    }
    public String productInfo(int productCode){
        int index = lookupProduct(productCode);
        if(index ==-1) return "PRODUCTO INEXISTENTE";
        return Inventory.get(index).toString();
    }
}