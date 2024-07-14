
package minishopper.Controller;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import minishopper.Entity.Product;
import minishopper.Service.ProductService;
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
@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductControllerTest {
    @Autowired
    private ProductController productController;
    @MockBean
    private ProductService productService;
   
    @Test
    void testFetchAllProducts() throws Exception {
        // Arrange
        Product product = new Product();
        product.setBrand("Products Not Found");
        product.setCategory("Products Not Found");
        product.setDiscountedPrice(10.0d);
        product.setImage("Products Not Found");
        product.setProductId("42");
        product.setProductName("Products Not Found");
        product.setShortDescription("Products Not Found");
        product.setStock(1);
        product.setUnitPrice(10.0d);
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productService.getAllProducts()).thenReturn(productList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/listAllProducts");
        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"productId\":\"42\",\"productName\":\"Products Not Found\",\"brand\":\"Products Not Found\",\"unitPrice\":10.0,"
                                        + "\"discountedPrice\":10.0,\"stock\":1,\"category\":\"Products Not Found\",\"shortDescription\":\"Products Not"
                                        + " Found\",\"image\":\"Products Not Found\"}]"));
    }
   
    @Test
    void testFetchSingleProduct() throws Exception {
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
        when(productService.getByProductId(Mockito.<String>any())).thenReturn(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products/productId/{productId}", "42");
        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productId\":\"42\",\"productName\":\"Product Name\",\"brand\":\"Brand\",\"unitPrice\":10.0,\"discountedPrice\":10"
                                        + ".0,\"stock\":1,\"category\":\"Category\",\"shortDescription\":\"Short Description\",\"image\":\"Image\"}"));
    }
  
    @Test
    void testSaveImage() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/addImage");
        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("image"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("image"));
    }
  
    @Test
    void testSaveImage2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/addImage");
        requestBuilder.contentType("https://example.org/example");
        // Act and Assert
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("image"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("image"));
    }
}
 
 