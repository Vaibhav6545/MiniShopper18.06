
package minishopper.Service.impl;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import minishopper.Entity.Product;
import minishopper.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductServiceImpl productServiceImpl;
    
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
    
    @Test
    void testGetByProductId() {
        // Arrange
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setImage("Image");
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
   
    @Test
    void testSaveImage() throws IOException {
        // Arrange
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setImage("Image");
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setShortDescription("Short Description");
        product.setStock(1);
        product.setUnitPrice(10.0d);
        Product product2 = new Product();
        product2.setBrand("Brand");
        product2.setCategory("Category");
        product2.setDiscountedPrice(10.0d);
        product2.setImage("Image");
        product2.setProductId("42");
        product2.setProductName("Product Name");
        product2.setShortDescription("Short Description");
        product2.setStock(1);
        product2.setUnitPrice(10.0d);
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product2);
        when(productRepository.findByProductId(Mockito.<String>any())).thenReturn(product);
        // Act
        Product actualSaveImageResult = productServiceImpl
                .saveImage(new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))));
        // Assert
        verify(productRepository).findByProductId(eq("PID4013"));
        verify(productRepository).save(isA(Product.class));
        assertSame(product2, actualSaveImageResult);
    }
}
 
 