package com.example.mobile_ecommerce.service;

import com.example.mobile_ecommerce.model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final String FILE_PATH = "users.xlsx";

    /**
     * Method to get all users from the Excel file
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Skip header row
                }

                // Parse User data
                User user = new User();
                user.setUser_id(row.getCell(0).getStringCellValue());
                user.setUsername(row.getCell(1).getStringCellValue());
                user.setPassword(row.getCell(2).getStringCellValue()); // Store raw password

                users.add(user);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Excel file not found: " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Method to find a user by username
     */
    public User findByUsername(String username) {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Method to save all users to Excel file
     */
    public void saveAllUsers(List<User> users) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");

            // Create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("User ID");
            header.createCell(1).setCellValue("Username");
            header.createCell(2).setCellValue("Password");

            // Create data rows
            int rowNum = 1;
            for (User user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getUser_id());
                row.createCell(1).setCellValue(user.getUsername());
                row.createCell(2).setCellValue(user.getPassword()); // Store raw password
            }

            // Write to the Excel file
            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to add a new user
     */
    public void addUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        saveAllUsers(users);
    }
}
