/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.borobabyboutique;

/**
 *
 * @author Posh Tees Toddlers
 * 
 * NOTE: Students not to directly change any code in this class
 */
public class PoshTees {
    
    //attributes
    private String label;
    private String String;
    private String size;
    private double price;
    private String notes;
    private int stock;
    private String stockCode;
    
    public PoshTees(String label, String String, String size, double price, String notes, int stock, String stockCode) {
        this.label = label;
        this.String = String;
        this.size = size;
        this.price = price;
        this.notes = notes;
        this.stock = stock;
        this.stockCode = stockCode;
    }

    //getter read only methods
    public String getLabel() {
        return label;
    }

    public String getString() {
        return String;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getNotes() {
        return notes;
    }

    public int getStock() {
        return stock;
    }

    public String getStockCode() {
        return stockCode;
    }

    //service methods
    public void increaseStock(int quantity){
        stock = stock + quantity;
    }
    
    public void decreaseStock(int quantity){
        stock = stock - quantity;
    }    
}
