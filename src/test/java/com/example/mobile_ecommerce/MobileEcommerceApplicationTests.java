package com.example.mobile_ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.mobile_ecommerce.middlewares.JwtAuthFilter;
import com.example.mobile_ecommerce.service.ProductService;
import com.example.mobile_ecommerce.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MobileEcommerceApplication.class)
@ActiveProfiles("test")
public class MobileEcommerceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void contextContainsUserServiceBean() {
        assertThat(applicationContext.containsBean("userService")).isTrue();
    }

    @Test
    void contextContainsProductServiceBean() {
        assertThat(applicationContext.containsBean("productService")).isTrue();
    }

    @Test
    void contextContainsJwtAuthFilterBean() {
        assertThat(applicationContext.containsBean("jwtAuthFilter")).isTrue();
    }
}
