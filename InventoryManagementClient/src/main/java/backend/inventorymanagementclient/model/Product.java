/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementclient.model;

/**
 *
 * @author isabe, pier
 */
public class Product {
    private int productCode;
    private String productName;
    private int productStock;

    public Product(int productCode, String productName, int productAmount) {
        this.productCode = productCode;
        this.productName = productName;
        this.productStock = productAmount;
    }

    public int getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductStock() {
        return productStock;
    }
    
    /**
     * Sells the amount given if there's enough stock;
     * @param sellAmount The amount to sell.
     * @return True if it could be done, False otherwise
     */
    public boolean sellProduct(int sellAmount){
        return sellAmount <= productStock && (productStock -= sellAmount) >= 0;
    }
    
    /**
    * String representation of the product using the format code,name,amount 
    * and changing comas in the name for ## to bypass CSV confusion.
    * @return String in format [productCode],[productName],[productStock]
    */
    @Override
    public String toString() {
        return (productCode+","+productName.replace(",","##")+","+productStock);
    }
    
    public boolean restock(int restockAmount){
        productStock += restockAmount;
        return true;
    }
}
