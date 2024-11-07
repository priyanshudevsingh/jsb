// package com.example.demo.controller;

// import com.example.demo.model.Employee;
// import com.example.demo.service.EmployeeService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/employees")
// public class EmployeeController {

//     @Autowired
//     private EmployeeService employeeService;

//     @GetMapping
//     public List<Employee> getAllEmployees() {
//         return employeeService.getAllEmployees();
//     }

//     @PostMapping
//     public Employee addEmployee(@RequestBody Employee employee) {
//         List<Employee> employees = employeeService.getAllEmployees();
//         employees.add(employee);
//         employeeService.saveAllEmployees(employees);
//         return employee;
//     }

//     @PutMapping("/{id}")
//     public Employee updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
//         List<Employee> employees = employeeService.getAllEmployees();

//         for (Employee emp : employees) {
//             if (emp.getId() == id) {
//                 emp.setName(updatedEmployee.getName());
//                 emp.setDepartment(updatedEmployee.getDepartment());
//                 emp.setSalary(updatedEmployee.getSalary());
//                 employeeService.saveAllEmployees(employees);
//                 return emp;
//             }
//         }
//         return null;
//     }

//     @DeleteMapping("/{id}")
//     public void deleteEmployee(@PathVariable int id) {
//         List<Employee> employees = employeeService.getAllEmployees();
//         employees.removeIf(emp -> emp.getId() == id);
//         employeeService.saveAllEmployees(employees);
//     }
// }
package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        List<Product> products = productService.getAllProducts();
        product.setId(UUID.randomUUID()); // Generate a new unique ID
        products.add(product);
        productService.saveAllProducts(products);
        return product;
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody Product updatedProduct) {
        List<Product> products = productService.getAllProducts();

        for (Product product : products) {
            if (product.getId().equals(id)) {
                product.setName(updatedProduct.getName());
                product.setColor(updatedProduct.getColor());
                product.setModel(updatedProduct.getModel());
                product.setBrand(updatedProduct.getBrand());
                productService.saveAllProducts(products);
                return product;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        List<Product> products = productService.getAllProducts();
        products.removeIf(product -> product.getId().equals(id));
        productService.saveAllProducts(products);
    }
}
