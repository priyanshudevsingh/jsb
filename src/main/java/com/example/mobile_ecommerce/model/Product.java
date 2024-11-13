package com.example.mobile_ecommerce.model;

import java.util.UUID;

public class Product {
    private UUID id;
    private String product_id;
    private String product_name;
    private int year_of_release;
    private double price;
    private String color;
    private String model;
    private int sales;

    // Constructor with validation (this is the existing one)
    public Product(String product_id, String product_name, int year_of_release, double price, String model,
            String color, int sales) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (sales < 0) {
            throw new IllegalArgumentException("Sales count must be non-negative");
        }
        this.id = UUID.randomUUID(); // UUID is generated here
        this.product_id = product_id;
        this.product_name = product_name;
        this.year_of_release = year_of_release;
        this.price = price;
        this.model = model;
        this.color = color;
        this.sales = sales;
    }

    // Constructor that accepts UUID (new one)
    public Product(UUID id, String product_id, String product_name, int year_of_release, double price, String model,
            String color, int sales) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (sales < 0) {
            throw new IllegalArgumentException("Sales count must be non-negative");
        }
        this.id = id; // Use the provided UUID
        this.product_id = product_id;
        this.product_name = product_name;
        this.year_of_release = year_of_release;
        this.price = price;
        this.model = model;
        this.color = color;
        this.sales = sales;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getYear_of_release() {
        return year_of_release;
    }

    public void setYear_of_release(int year_of_release) {
        this.year_of_release = year_of_release;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        if (sales < 0) {
            throw new IllegalArgumentException("Sales cannot be negative.");
        }
        this.sales = sales;
    }

    // Override toString for logging/debugging
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    // Override equals and hashCode for proper comparison in collections
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product product = (Product) obj;
        return product_id.equals(product.product_id);
    }

    @Override
    public int hashCode() {
        return product_id.hashCode();
    }
}
