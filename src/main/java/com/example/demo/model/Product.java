// package com.example.demo.model;

// public class Employee {
//     private int id;
//     private String name;
//     private String department;
//     private double salary;

//     // Getters and Setters
//     public int getId() { return id; }
//     public void setId(int id) { this.id = id; }

//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }

//     public String getDepartment() { return department; }
//     public void setDepartment(String department) { this.department = department; }

//     public double getSalary() { return salary; }
//     public void setSalary(double salary) { this.salary = salary; }
// }
package com.example.demo.model;

import java.util.UUID;

public class Product {
    private UUID id;         // Unique ID (UUID)
    private String name;      // Product name
    private String color;     // Product color
    private String model;     // Product model
    private String brand;     // Product brand

    // Constructor for creating a new Product with auto-generated UUID
    public Product(String name, String color, String model, String brand) {
        this.id = UUID.randomUUID(); // Auto-generate unique ID
        this.name = name;
        this.color = color;
        this.model = model;
        this.brand = brand;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
}
