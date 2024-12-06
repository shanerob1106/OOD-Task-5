/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ood.bbbsystem;

/**
 *
 * @author shane
 */
public class Garment {

    private String id, make, name, colour, description, material;
    private int price, stock;

    public Garment(String id, int price, String make, String name, String colour, String description, String material, int stock) {
        this.id = id;
        this.price = price;
        this.make = make;
        this.name = name;
        this.colour = colour;
        this.description = description;
        this.material = material;
        this.stock = stock;
    }

    public String getGender() {
        String genderCode = id.split("_")[0];
        switch (genderCode) {
            case "B":
                return "Boys";
            case "G":
                return "Girls";
            case "U":
                return "Unisex";
            default:
                return "unknown";
        }
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }

    public String getImageFilename() {
        return id + ".jpg";
    }

    public String getDescription() {
        return description;
    }

    public String getMaterial() {
        return material;
    }

    public int getPrice() {
        return price;
    }

    public String getSize() {
        return id.split("_")[2] + (" months");
    }

    public String getFormattedPrice() {
        return String.format("£%.2f", price / 100.0);
    }

    public int getStock() {
        return stock;
    }

    public void incrementStock(int amount) {
        stock += amount;
    }

    public void decreaseStock(int amount) {
        stock -= amount;
    }

}
