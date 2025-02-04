/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.inventorymanagementserver.model;

/**
 *
 * @author isabe, pier
 */
public class Product {
    private int productCode;
    private String productName;
    private int productStock;
    private String productDescription;
    private float productPrice;
    
    public Product(int productCode, String productName, int productAmount, float productPrice, String productDescription) {
        this.productCode = productCode;
        this.productName = productName;
        this.productStock = productAmount;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }

    public int getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }
    
    public String getProductDescription() {
        return productDescription;
    }

    public int getProductStock() {
        return productStock;
    }
    
    public int getProductPrice() {
        return productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }
    /**
    * String representation of the product using the format code,name,amount 
    * and changing comas in the name for ## to bypass CSV confusion.
    * @return String in format [productCode],[productName],[productStock]
    */
    @Override
    public String toString() {
        return (productCode+","+productName.replace(",","##")+","+productStock+",");
    }
    
    public boolean restock(int restockAmount){
        productStock += restockAmount;
        return true;
    }
    
    public String sellProduct(int sellAmount){
        if(productStock>=sellAmount){
            productStock-=sellAmount;
            return "SUCCESS";
        }
        return "INSUFICIENT STOCK ERROR";
    }
}
