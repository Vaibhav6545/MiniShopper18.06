
package minishopper.Controller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import minishopper.Entity.Product;
import minishopper.Service.ProductService;
import minishopper.exception.ResourceNotFoundException;
@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductControllerTest {
    @Autowired
    private ProductController productController;
    @MockBean
    private ProductService productService;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
   
  
  
 
    @Test
    public void testFetchSingleProduct_ProductFound() throws ResourceNotFoundException {
        
        String productId = "123";
        Product product = new Product("1L", "Product 1", "xy", 10.1,1,"yz" ,"yt", 35.4);
        when(productService.getByProductId(productId)).thenReturn(product);
        ResponseEntity<Product> response = productController.fetchSingleProduct(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }
    @Test
    public void testFetchSingleProduct_ProductNotFound() {
        String productId = "456";
        when(productService.getByProductId(productId)).thenReturn(null);
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productController.fetchSingleProduct(productId);
        });
        assertEquals("Product Not Found", exception.getMessage());
    }
 

@Test
public void testFetchAllProducts_NoProductsFound() {
    List<Product> emptyList = new ArrayList<>();
    when(productService.getAllProducts()).thenReturn(emptyList);
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
        productController.fetchAllProducts();
    });
    assertEquals("Products Not Found", exception.getMessage());
}
@Test
public void testFetchAllProducts_ProductsFound() {
    List<Product> productList = new ArrayList<>();
    productList.add(new Product("1L", "Product 1", "xy", 10.1,1,"yz" ,"yt", 35.4));
    productList.add(new Product("2L", "Product 2", "xp", 10.2,2,"yl" ,"yt", 36.4));
    when(productService.getAllProducts()).thenReturn(productList);
    ResponseEntity<List<Product>> response = productController.fetchAllProducts();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(productList, response.getBody());
}
}
 
 