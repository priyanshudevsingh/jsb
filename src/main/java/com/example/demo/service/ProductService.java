// package com.example.demo.service;

// import com.example.demo.model.Employee;
// import org.apache.poi.ss.usermodel.*;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.springframework.stereotype.Service;

// import java.io.*;
// import java.util.ArrayList;
// import java.util.List;

// @Service
// public class EmployeeService {

//     private static final String FILE_PATH = "employees.xlsx";

//     // Method to read data from Excel file
//     public List<Employee> getAllEmployees() {
//         List<Employee> employees = new ArrayList<>();

//         try (FileInputStream fis = new FileInputStream(FILE_PATH);
//              Workbook workbook = new XSSFWorkbook(fis)) {
//             Sheet sheet = workbook.getSheetAt(0);

//             for (Row row : sheet) {
//                 if (row.getRowNum() == 0) continue; // Skip header row

//                 Employee emp = new Employee();
//                 emp.setId((int) row.getCell(0).getNumericCellValue());
//                 emp.setName(row.getCell(1).getStringCellValue());
//                 emp.setDepartment(row.getCell(2).getStringCellValue());
//                 emp.setSalary(row.getCell(3).getNumericCellValue());

//                 employees.add(emp);
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         return employees;
//     }

//     // Method to write data to Excel file
//     public void saveAllEmployees(List<Employee> employees) {
//         try (Workbook workbook = new XSSFWorkbook()) {
//             Sheet sheet = workbook.createSheet("Employees");

//             // Header row
//             Row header = sheet.createRow(0);
//             header.createCell(0).setCellValue("ID");
//             header.createCell(1).setCellValue("Name");
//             header.createCell(2).setCellValue("Department");
//             header.createCell(3).setCellValue("Salary");

//             // Data rows
//             int rowNum = 1;
//             for (Employee emp : employees) {
//                 Row row = sheet.createRow(rowNum++);
//                 row.createCell(0).setCellValue(emp.getId());
//                 row.createCell(1).setCellValue(emp.getName());
//                 row.createCell(2).setCellValue(emp.getDepartment());
//                 row.createCell(3).setCellValue(emp.getSalary());
//             }

//             try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
//                 workbook.write(fos);
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }
package com.example.demo.service;

import com.example.demo.model.Product;
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

    // Method to get all products from the Excel file
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                Product product = new Product(
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        row.getCell(4).getStringCellValue()
                );
                product.setId(UUID.fromString(row.getCell(0).getStringCellValue()));

                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Method to write products to Excel file
    public void saveAllProducts(List<Product> products) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Name");
            header.createCell(2).setCellValue("Color");
            header.createCell(3).setCellValue("Model");
            header.createCell(4).setCellValue("Brand");

            // Data rows
            int rowNum = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getId().toString());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getColor());
                row.createCell(3).setCellValue(product.getModel());
                row.createCell(4).setCellValue(product.getBrand());
            }

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
