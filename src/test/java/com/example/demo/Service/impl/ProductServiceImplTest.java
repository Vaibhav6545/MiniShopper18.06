
package com.example.demo.Service.impl;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductServiceImpl productServiceImpl;
    /**
     * Method under test: {@link ProductServiceImpl#getAllProducts()}
     */
    @Test
    void testGetAllProducts() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);
        // Act
        List<Product> actualAllProducts = productServiceImpl.getAllProducts();
        // Assert
        verify(productRepository).findAll();
        assertTrue(actualAllProducts.isEmpty());
        assertSame(productList, actualAllProducts);
    }
    /**
     * Method under test: {@link ProductServiceImpl#getByProductId(String)}
     */
    @Test
    void testGetByProductId() {
        // Arrange
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setShortDescription("Short Description");
        product.setStock(1);
        product.setUnitPrice(10.0d);
        when(productRepository.findByProductId(Mockito.<String>any())).thenReturn(product);
        // Act
        Product actualByProductId = productServiceImpl.getByProductId("42");
        // Assert
        verify(productRepository).findByProductId(eq("42"));
        assertSame(product, actualByProductId);
    }
    /**
     * Method under test:
     * {@link ProductServiceImpl#getAllProductsByCategory(String)}
     */
    @Test
    void testGetAllProductsByCategory() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findByCategory(Mockito.<String>any())).thenReturn(productList);
        // Act
        List<Product> actualAllProductsByCategory = productServiceImpl.getAllProductsByCategory("Category");
        // Assert
        verify(productRepository).findByCategory(eq("Category"));
        assertTrue(actualAllProductsByCategory.isEmpty());
        assertSame(productList, actualAllProductsByCategory);
    }
    /**
     * Method under test: {@link ProductServiceImpl#getAllAvailableProduct()}
     */
    @Test
    void testGetAllAvailableProduct() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findAvailableProducts()).thenReturn(productList);
        // Act
        List<Product> actualAllAvailableProduct = productServiceImpl.getAllAvailableProduct();
        // Assert
        verify(productRepository).findAvailableProducts();
        assertTrue(actualAllAvailableProduct.isEmpty());
        assertSame(productList, actualAllAvailableProduct);
    }
    /**
     * Method under test: {@link ProductServiceImpl#updateStock(String, int)}
     */
    @Test
    void testUpdateStock() {
        // Arrange
        doNothing().when(productRepository).updateStock(Mockito.<String>any(), anyInt());
        // Act
        productServiceImpl.updateStock("42", 1);
        // Assert that nothing has changed
        verify(productRepository).updateStock(eq("42"), eq(1));
        assertTrue(productServiceImpl.getAllAvailableProduct().isEmpty());
    }
}
 