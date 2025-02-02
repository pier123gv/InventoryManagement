/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.inventorymanagementclient.model;

/**
 *
 * @author isabe, pier
 */
public class Product {
    private int productCode;
    private String productName;
    private int productAmount;

    public Product(int productCode, String productName, int productAmount) {
        this.productCode = productCode;
        this.productName = productName;
        this.productAmount = productAmount;
    }

    public int getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductAmount() {
        return productAmount;
    }
    
    public boolean sellProduct(int sellAmount){
        return sellAmount <= productAmount && (productAmount -= sellAmount) >= 0;
    }
    /**
    * String representation of the product using the format code,name,amount 
    * and changing comas in the name for ## to bypass CSV confusion.
    * @return String in format [productCode],[productName],[productAmount]
    */
    @Override
    public String toString() {
        return (productCode+","+productName.replace(",","##")+","+productAmount);
    }
    
}
