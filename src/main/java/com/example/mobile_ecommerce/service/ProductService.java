package com.example.mobile_ecommerce.service;

import com.example.mobile_ecommerce.model.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private static final String FILE_PATH = "products.xlsx";

    /**
     * Method to get all products from the Excel file
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                // Extract the UUID from the first column (it should already be in the file)
                String uuidString = row.getCell(0).getStringCellValue();
                UUID productUUID = UUID.fromString(uuidString);

                // Create the product without changing UUID
                Product product = new Product(
                        productUUID, // Use the existing UUID
                        row.getCell(1).getStringCellValue(), // product_id
                        row.getCell(2).getStringCellValue(), // product_name
                        (int) row.getCell(3).getNumericCellValue(), // year_of_release
                        row.getCell(4).getNumericCellValue(), // price
                        row.getCell(5).getStringCellValue(), // model
                        row.getCell(6).getStringCellValue(), // color
                        (int) row.getCell(7).getNumericCellValue() // sales
                );

                products.add(product);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Excel file not found: " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     * Method to save all products to an Excel file
     */
    public void saveAllProducts(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");

            // Create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("UUID");
            header.createCell(1).setCellValue("Product ID");
            header.createCell(2).setCellValue("Product Name");
            header.createCell(3).setCellValue("Year Of Release");
            header.createCell(4).setCellValue("Price");
            header.createCell(5).setCellValue("Model");
            header.createCell(6).setCellValue("Color");
            header.createCell(7).setCellValue("Sales");

            // Create data rows
            int rowNum = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getId().toString()); // Use existing UUID
                row.createCell(1).setCellValue(product.getProduct_id());
                row.createCell(2).setCellValue(product.getProduct_name());
                row.createCell(3).setCellValue(product.getYear_of_release());
                row.createCell(4).setCellValue(product.getPrice());
                row.createCell(5).setCellValue(product.getModel());
                row.createCell(6).setCellValue(product.getColor());
                row.createCell(7).setCellValue(product.getSales());
            }

            // Write the workbook to the file
            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get a product by ID
    public Product getProductById(UUID id) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    // Method to add a new product
    public void addProduct(Product product) {
        // Generate UUID only when adding a new product
        if (product.getId() == null) {
            product.setId(UUID.randomUUID()); // Generate a new UUID if it's not already set
        }
        List<Product> products = getAllProducts();
        products.add(product);
        saveAllProducts(products);
    }

    // Method to update an existing product
    public Product updateProduct(UUID id, Product updatedProduct) {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setProduct_id(updatedProduct.getProduct_id());
                product.setProduct_name(updatedProduct.getProduct_name());
                product.setYear_of_release(updatedProduct.getYear_of_release());
                product.setPrice(updatedProduct.getPrice());
                product.setModel(updatedProduct.getModel());
                product.setColor(updatedProduct.getColor());
                product.setSales(updatedProduct.getSales());
                saveAllProducts(products);
                return product;
            }
        }
        return null;
    }

    // Method to delete a product by ID
    public boolean deleteProduct(UUID id) {
        List<Product> products = getAllProducts();
        boolean isRemoved = products.removeIf(product -> product.getId().equals(id));
        if (isRemoved) {
            saveAllProducts(products);
        }
        return isRemoved;
    }
}
