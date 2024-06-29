package minishopper.Controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import minishopper.Service.ProductService;

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
      
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/listAllProducts",
                uriVariables);
        Object[] controllers = new Object[]{productController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

   
    }

   
    @Test
    void testFetchProductsByCategory() throws Exception {
      
        Object[] uriVariables = new Object[]{"Category"};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products/productCategory/{category}",
                uriVariables);
        Object[] controllers = new Object[]{productController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

       ResultActions actualPerformResult = buildResult.perform(requestBuilder);

      
    }

   
    @Test
    void testFetchSingleProduct() throws Exception {
       
        Object[] uriVariables = new Object[]{"42"};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products/productId/{id}",
                uriVariables);
        Object[] controllers = new Object[]{productController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        // Act
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

    
    }

   
    @Test
    void testSaveImage() throws Exception {
       
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/addImage", uriVariables);
        Object[] controllers = new Object[]{productController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

  
    }
}
