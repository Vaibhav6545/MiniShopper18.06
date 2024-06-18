package com.example.demo.Controller;

import static org.mockito.Mockito.when;

import com.example.demo.Entity.Category;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ProductService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CategoryController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CategoryControllerDiffblueTest {
    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;

    /**
     * Method under test: {@link CategoryController#getAllCategories()}
     */
    @Test
    void testGetAllCategories() throws Exception {
        // Arrange
        when(categoryService.fetchAllCategories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CategoryController#getCategoryById(String)}
     */
    @Test
    void testGetCategoryById() throws Exception {
        // Arrange
        Category category = new Category();
        category.setCategoryId("42");
        category.setCategoryTitle("Dr");
        category.setDescription("The characteristics of someone or something");
        when(categoryService.fetchCategoryById(Mockito.<String>any())).thenReturn(category);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories/{categoryId}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"categoryId\":\"42\",\"categoryTitle\":\"Dr\",\"description\":\"The characteristics of someone or something\"}"));
    }

    /**
     * Method under test: {@link CategoryController#getProductsByCategoryId(String)}
     */
    @Test
    void testGetProductsByCategoryId() throws Exception {
        // Arrange
        Category category = new Category();
        category.setCategoryId("42");
        category.setCategoryTitle("Dr");
        category.setDescription("The characteristics of someone or something");
        when(categoryService.fetchCategoryById(Mockito.<String>any())).thenReturn(category);
        when(productService.getAllProductsByCategory(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories/{categoryId}/products",
                "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
