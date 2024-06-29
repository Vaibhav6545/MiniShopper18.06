package minishopper.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import minishopper.Service.OrderService;
import minishopper.Service.ProductService;
import minishopper.dtos.CreateOrderRequest;
import minishopper.dtos.ExcelOrder;
import minishopper.dtos.UpdateOrderItem;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OrderControllerTest {
    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    
    @Test
    void testCreateOrder() throws Exception {
        
       
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/orders", uriVariables)
                .contentType(MediaType.APPLICATION_JSON);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCartId("42");
        createOrderRequest.setCity("Oxford");
        createOrderRequest.setOrderName("Order Name");
        createOrderRequest.setOrderStatus("Order Status");
        createOrderRequest.setPaymentStatus("Payment Status");
        createOrderRequest.setPostalCode("Postal Code");
        createOrderRequest.setProductId("42");
        ArrayList<ExcelOrder> products = new ArrayList<>();
        createOrderRequest.setProducts(products);
        createOrderRequest.setQuantity(1);
        createOrderRequest.setShippingAddress("42 Main St");
        createOrderRequest.setShippingPhone("6625550144");
        createOrderRequest.setState("MD");
        createOrderRequest.setUserId("42");

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(createOrderRequest));
        Object[] controllers = new Object[]{orderController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

      
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

        
    }

   
    @Test
    void testCreateOrderForSingleProduct() throws Exception {
       
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/orders/singleProduct", uriVariables)
                .contentType(MediaType.APPLICATION_JSON);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCartId("42");
        createOrderRequest.setCity("Oxford");
        createOrderRequest.setOrderName("Order Name");
        createOrderRequest.setOrderStatus("Order Status");
        createOrderRequest.setPaymentStatus("Payment Status");
        createOrderRequest.setPostalCode("Postal Code");
        createOrderRequest.setProductId("42");
        ArrayList<ExcelOrder> products = new ArrayList<>();
        createOrderRequest.setProducts(products);
        createOrderRequest.setQuantity(1);
        createOrderRequest.setShippingAddress("42 Main St");
        createOrderRequest.setShippingPhone("6625550144");
        createOrderRequest.setState("MD");
        createOrderRequest.setUserId("42");

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(createOrderRequest));
        Object[] controllers = new Object[]{orderController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

      
    }

  
    @Test
    void testDeleteItem() throws Exception {
     
        Object[] uriVariables = new Object[]{1};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/orders/item/{orderItemId}",
                uriVariables);
        Object[] controllers = new Object[]{orderController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

     
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

    
    }

   
    @Test
    void testFetchAllProductsForExcel() throws Exception {
    
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/excel", uriVariables);
        Object[] controllers = new Object[]{orderController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

      
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

   
    }

    
    @Test
    void testGetOrderByOrderId() throws Exception {
      
        Object[] uriVariables = new Object[]{"42"};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/{orderId}", uriVariables);
        Object[] controllers = new Object[]{orderController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

       
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

      
    }

   
    @Test
    void testGetOrderByUserId() throws Exception {
     
        Object[] uriVariables = new Object[]{"42"};
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/user/{userId}", uriVariables);
        Object[] controllers = new Object[]{orderController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

     
    }

   
    @Test
    void testReadExcelData() throws Exception {
     
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/orders/excel", uriVariables)
                .contentType(MediaType.APPLICATION_JSON);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCartId("42");
        createOrderRequest.setCity("Oxford");
        createOrderRequest.setOrderName("Order Name");
        createOrderRequest.setOrderStatus("Order Status");
        createOrderRequest.setPaymentStatus("Payment Status");
        createOrderRequest.setPostalCode("Postal Code");
        createOrderRequest.setProductId("42");
        ArrayList<ExcelOrder> products = new ArrayList<>();
        createOrderRequest.setProducts(products);
        createOrderRequest.setQuantity(1);
        createOrderRequest.setShippingAddress("42 Main St");
        createOrderRequest.setShippingPhone("6625550144");
        createOrderRequest.setState("MD");
        createOrderRequest.setUserId("42");

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(createOrderRequest));
        Object[] controllers = new Object[]{orderController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

    
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

      
    }

  
    @Test
    void testUpdateItemInOrdere() throws Exception {
        
        Object[] uriVariables = new Object[]{};
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .post("/orders/updateOrderItem", uriVariables)
                .contentType(MediaType.APPLICATION_JSON);

        UpdateOrderItem updateOrderItem = new UpdateOrderItem();
        updateOrderItem.setOrderItemId(1);
        updateOrderItem.setProductId("42");
        updateOrderItem.setQuantity(1);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(updateOrderItem));
        Object[] controllers = new Object[]{orderController};
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

        
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);

    }
}
