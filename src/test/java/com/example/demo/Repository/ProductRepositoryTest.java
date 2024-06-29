
package minishopper.Repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import minishopper.Entity.Product;
import minishopper.Repository.ProductRepository;
@ContextConfiguration(classes = {ProductRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"minishopper.Entity"})
@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    void testFindByProductId() {
        
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setShortDescription("Short Description");
        product.setStock(1);
        product.setUnitPrice(10.0d);
        Product product2 = new Product();
        product2.setBrand("42");
        product2.setCategory("42");
        product2.setDiscountedPrice(0.5d);
        product2.setProductId("Product Id");
        product2.setProductName("42");
        product2.setShortDescription("42");
        product2.setStock(-1);
        product2.setUnitPrice(0.5d);
        productRepository.save(product);
        productRepository.save(product2);
        // Act
        productRepository.findByProductId("42");
    }
   
  /*  @Test
    void testFindByProductName() {
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
        Product product2 = new Product();
        product2.setBrand("42");
        product2.setCategory("42");
        product2.setDiscountedPrice(0.5d);
        product2.setProductId("Product Id");
        product2.setProductName("42");
        product2.setShortDescription("42");
        product2.setStock(-1);
        product2.setUnitPrice(0.5d);
        productRepository.save(product);
        productRepository.save(product2);
        // Act and Assert
        assertEquals(1, productRepository.findByProductName("Product Name").size());
    }
    */
    @Test
    void testFindByBrand() {
       
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setShortDescription("Short Description");
        product.setStock(1);
        product.setUnitPrice(10.0d);
        Product product2 = new Product();
        product2.setBrand("42");
        product2.setCategory("42");
        product2.setDiscountedPrice(0.5d);
        product2.setProductId("Product Id");
        product2.setProductName("42");
        product2.setShortDescription("42");
        product2.setStock(-1);
        product2.setUnitPrice(0.5d);
        productRepository.save(product);
        productRepository.save(product2);
        
        assertEquals(1, productRepository.findByBrand("Brand").size());
    }
    
    @Test
    void testFindByCategory() {
        
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setShortDescription("Short Description");
        product.setStock(1);
        product.setUnitPrice(10.0d);
        Product product2 = new Product();
        product2.setBrand("42");
        product2.setCategory("42");
        product2.setDiscountedPrice(0.5d);
        product2.setProductId("Product Id");
        product2.setProductName("42");
        product2.setShortDescription("42");
        product2.setStock(-1);
        product2.setUnitPrice(0.5d);
        productRepository.save(product);
        productRepository.save(product2);
       
        assertEquals(1, productRepository.findByCategory("Category").size());
    }
   
  /*  @Test
    void testFindAvailableProducts() {
       
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setShortDescription("Short Description");
        product.setStock(1);
        product.setUnitPrice(10.0d);
        Product product2 = new Product();
        product2.setBrand("42");
        product2.setCategory("42");
        product2.setDiscountedPrice(0.5d);
        product2.setProductId("Product Id");
        product2.setProductName("42");
        product2.setShortDescription("42");
        product2.setStock(-1);
        product2.setUnitPrice(0.5d);
        productRepository.save(product);
        productRepository.save(product2);
       
        assertTrue(productRepository.findAvailableProducts().isEmpty());
        */
    }
