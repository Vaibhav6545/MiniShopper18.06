package minishopper.Controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import minishopper.dtos.ChangeOrderStatusDto;
import minishopper.dtos.CreateExcelOrderRequestDto;
import minishopper.dtos.CreateOrderRequestDto;
import minishopper.dtos.CreateSingleProductOrderRequestDto;
import minishopper.dtos.ExcelOrderDto;
import minishopper.dtos.OrderDto;
import minishopper.dtos.OrderItemDto;
import minishopper.dtos.UpdateOrderItemDto;
import minishopper.dtos.UserDto;
import minishopper.exception.InvalidInputException;
import minishopper.exception.ResourceNotFoundException;
import minishopper.Entity.Order;
import minishopper.Entity.OrderItem;
import minishopper.Entity.Product;
import minishopper.Entity.User;
import minishopper.Service.OrderService;
import minishopper.Service.ProductService;
import minishopper.Service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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

    @MockBean
    private UserService userService;

    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   
    @Test
    void testCheckNormalUserId() {
        // Arrange
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user);

        // Act
        boolean actualCheckNormalUserIdResult = orderController.checkNormalUserId("42");

        // Assert
        verify(userService).checkUserId(eq("42"));
        assertFalse(actualCheckNormalUserIdResult);
    }

   
    @Test
    void testCheckShopkeeperUserId() {
        // Arrange
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user);

        // Act
        boolean actualCheckShopkeeperUserIdResult = orderController.checkShopkeeperUserId("42");

        // Assert
        verify(userService).checkUserId(eq("42"));
        assertFalse(actualCheckShopkeeperUserIdResult);
    }

    
    @Test
    void testChangeOrderStatusDto() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .reason("Just cause")
                .shippingAddress("42 Main St")
                .state("MD");
        UserDto user = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();
        when(orderService.fetchOrderByOrderId(Mockito.<String>any())).thenReturn(buildResult);
        doNothing().when(orderService).updateOrderStatus(Mockito.<ChangeOrderStatusDto>any());

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("shopkeeper");
        user2.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user2);

        ChangeOrderStatusDto changeOrderStatusDto = new ChangeOrderStatusDto();
        changeOrderStatusDto.setExpectedDeliveryDate("2020-03-01");
        changeOrderStatusDto.setOrderId("42");
        changeOrderStatusDto.setOrderStatus("Order Status");
        changeOrderStatusDto.setReason("Just cause");
        changeOrderStatusDto.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(changeOrderStatusDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/minishop/changeOrderStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"orderId\":\"42\",\"orderNumber\":\"42\",\"orderStatus\":\"Order Status\",\"paymentStatus\":\"Payment Status\","
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"shippingAddress\":\"42"
                                        + " Main St\",\"pinCode\":\"Pin Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"phoneNumber\":\"6625550144\",\"deliveredDate"
                                        + "\":[1970,1,1],\"createdAt\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"password\":\"ok\",\"firstName\""
                                        + ":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,\"role\":\"Role\"},\"reason\":\"Just"
                                        + " cause\"}"));
    }

  
    @Test
    void testCreateOrder() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .reason("Just cause")
                .shippingAddress("42 Main St")
                .state("MD");
        UserDto user = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();
        when(orderService.createOrder(Mockito.<CreateOrderRequestDto>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("user");
        user2.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user2);

        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto();
        createOrderRequestDto.setCartId("42");
        createOrderRequestDto.setCity("Oxford");
        createOrderRequestDto.setFirstName("Jane");
        createOrderRequestDto.setLastName("Doe");
        createOrderRequestDto.setOrderName("Order Name");
        createOrderRequestDto.setOrderStatus("Order Status");
        createOrderRequestDto.setPaymentStatus("Payment Status");
        createOrderRequestDto.setPhoneNumber("6625550144");
        createOrderRequestDto.setPinCode("Pin Code");
        createOrderRequestDto.setShippingAddress("42 Main St");
        createOrderRequestDto.setState("MD");
        createOrderRequestDto.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(createOrderRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/minishop/placeOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"orderId\":\"42\",\"orderNumber\":\"42\",\"orderStatus\":\"Order Status\",\"paymentStatus\":\"Payment Status\","
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"shippingAddress\":\"42"
                                        + " Main St\",\"pinCode\":\"Pin Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"phoneNumber\":\"6625550144\",\"deliveredDate"
                                        + "\":[1970,1,1],\"createdAt\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"password\":\"ok\",\"firstName\""
                                        + ":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,\"role\":\"Role\"},\"reason\":\"Just"
                                        + " cause\"}"));
    }

    
    @Test
    void testCreateOrderForSingleProduct() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .reason("Just cause")
                .shippingAddress("42 Main St")
                .state("MD");
        UserDto user = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();
        when(orderService.createOrderSingleProduct(Mockito.<CreateSingleProductOrderRequestDto>any()))
                .thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("user");
        user2.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user2);

        CreateSingleProductOrderRequestDto createSingleProductOrderRequestDto = new CreateSingleProductOrderRequestDto();
        createSingleProductOrderRequestDto.setCity("Oxford");
        createSingleProductOrderRequestDto.setFirstName("Jane");
        createSingleProductOrderRequestDto.setLastName("Doe");
        createSingleProductOrderRequestDto.setOrderName("Order Name");
        createSingleProductOrderRequestDto.setOrderStatus("Order Status");
        createSingleProductOrderRequestDto.setPaymentStatus("Payment Status");
        createSingleProductOrderRequestDto.setPhoneNumber("6625550144");
        createSingleProductOrderRequestDto.setPinCode("Pin Code");
        createSingleProductOrderRequestDto.setProductId("42");
        createSingleProductOrderRequestDto.setQuantity(1);
        createSingleProductOrderRequestDto.setShippingAddress("42 Main St");
        createSingleProductOrderRequestDto.setState("MD");
        createSingleProductOrderRequestDto.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(createSingleProductOrderRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/minishop/singleProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"orderId\":\"42\",\"orderNumber\":\"42\",\"orderStatus\":\"Order Status\",\"paymentStatus\":\"Payment Status\","
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"shippingAddress\":\"42"
                                        + " Main St\",\"pinCode\":\"Pin Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"phoneNumber\":\"6625550144\",\"deliveredDate"
                                        + "\":[1970,1,1],\"createdAt\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"password\":\"ok\",\"firstName\""
                                        + ":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,\"role\":\"Role\"},\"reason\":\"Just"
                                        + " cause\"}"));
    }

   
    @Test
    void testDeleteItem() throws Exception {
        // Arrange
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");

        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setFirstName("Jane");
        order.setLastName("Doe");
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("Order Name");
        order.setOrderNumber("42");
        order.setOrderStatus("Order Status");
        order.setPaymentStatus("Payment Status");
        order.setPhoneNumber("6625550144");
        order.setPinCode("Pin Code");
        order.setReason("Just cause");
        order.setShippingAddress("42 Main St");
        order.setState("MD");
        order.setUser(user);

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

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setOrderItemId(1);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(10.0d);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

        Order order2 = new Order();
        order2.setCity("Oxford");
        order2.setCreatedAt(mock(Date.class));
        order2.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order2.setFirstName("Jane");
        order2.setLastName("Doe");
        order2.setOrderAmount(10.0d);
        order2.setOrderId("42");
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderName("Order Name");
        order2.setOrderNumber("42");
        order2.setOrderStatus("Order Status");
        order2.setPaymentStatus("Payment Status");
        order2.setPhoneNumber("6625550144");
        order2.setPinCode("Pin Code");
        order2.setReason("Just cause");
        order2.setShippingAddress("42 Main St");
        order2.setState("MD");
        order2.setUser(user2);

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

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrder(order2);
        orderItem2.setOrderItemId(1);
        orderItem2.setProduct(product2);
        orderItem2.setQuantity(1);
        orderItem2.setTotalPrice(10.0d);
        when(orderService.getOrderItemById(anyInt())).thenReturn(orderItem);
        when(orderService.removeOrderItemByOrderItemId(anyInt())).thenReturn(orderItem2);
        doNothing().when(orderService).updateTotalPrice(Mockito.<OrderItem>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/minishop/item/{orderItemId}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Deleted Successfully"));
    }

   
    @Test
    void testFetchAllProductsForExcel() throws Exception {
        // Arrange
        when(productService.getAllAvailableProduct()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/minishop/excel");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testFetchAllProductsForExcel2() throws Exception {
        // Arrange
        Product product = new Product();
        product.setBrand("in all products excel");
        product.setCategory("in all products excel");
        product.setDiscountedPrice(10.0d);
        product.setImage("in all products excel");
        product.setProductId("42");
        product.setProductName("in all products excel");
        product.setShortDescription("in all products excel");
        product.setStock(1);
        product.setUnitPrice(10.0d);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productService.getAllAvailableProduct()).thenReturn(productList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/minishop/excel");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"productId\":\"42\",\"productName\":\"in all products excel\",\"brand\":\"in all products excel\",\"unitPrice\""
                                        + ":10.0,\"discountedPrice\":10.0,\"stock\":1,\"category\":\"in all products excel\",\"shortDescription\":\"in all"
                                        + " products excel\",\"image\":\"in all products excel\"}]"));
    }

   
    @Test
    void testGetAllOrdersForShopkeeper() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .reason("Just cause")
                .shippingAddress("42 Main St")
                .state("MD");
        UserDto user = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();

        ArrayList<OrderDto> orderDtoList = new ArrayList<>();
        orderDtoList.add(buildResult);
        when(orderService.fetchAllOrders()).thenReturn(orderDtoList);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("shopkeeper");
        user2.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/minishop/getAllOrders/{userId}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"orderId\":\"42\",\"orderNumber\":\"42\",\"orderStatus\":\"Order Status\",\"paymentStatus\":\"Payment Status\","
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"shippingAddress\":\"42"
                                        + " Main St\",\"pinCode\":\"Pin Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"phoneNumber\":\"6625550144\",\"deliveredDate"
                                        + "\":[1970,1,1],\"createdAt\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"password\":\"ok\",\"firstName\""
                                        + ":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,\"role\":\"Role\"},\"reason\":\"Just"
                                        + " cause\"}]"));
    }

   
    @Test
    void testGetOrderByOrderId() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .reason("Just cause")
                .shippingAddress("42 Main St")
                .state("MD");
        UserDto user = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();
        when(orderService.fetchOrderByOrderId(Mockito.<String>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/minishop/{orderId}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"orderId\":\"42\",\"orderNumber\":\"42\",\"orderStatus\":\"Order Status\",\"paymentStatus\":\"Payment Status\","
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"shippingAddress\":\"42"
                                        + " Main St\",\"pinCode\":\"Pin Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"phoneNumber\":\"6625550144\",\"deliveredDate"
                                        + "\":[1970,1,1],\"createdAt\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"password\":\"ok\",\"firstName\""
                                        + ":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,\"role\":\"Role\"},\"reason\":\"Just"
                                        + " cause\"}"));
    }

   
    @Test
    void testGetOrderByUserId() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .reason("Just cause")
                .shippingAddress("42 Main St")
                .state("MD");
        UserDto user = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();

        ArrayList<OrderDto> orderDtoList = new ArrayList<>();
        orderDtoList.add(buildResult);
        when(orderService.fetchOrderByUser(Mockito.<String>any())).thenReturn(orderDtoList);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("user");
        user2.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/minishop/user/{userId}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"orderId\":\"42\",\"orderNumber\":\"42\",\"orderStatus\":\"Order Status\",\"paymentStatus\":\"Payment Status\","
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"shippingAddress\":\"42"
                                        + " Main St\",\"pinCode\":\"Pin Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"phoneNumber\":\"6625550144\",\"deliveredDate"
                                        + "\":[1970,1,1],\"createdAt\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"password\":\"ok\",\"firstName\""
                                        + ":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,\"role\":\"Role\"},\"reason\":\"Just"
                                        + " cause\"}]"));
    }

   
    @Test
    void testOrderExcelData() throws Exception {
        // Arrange
        CreateExcelOrderRequestDto createExcelOrderRequestDto = new CreateExcelOrderRequestDto();
        createExcelOrderRequestDto.setCity("Oxford");
        createExcelOrderRequestDto.setFirstName("Jane");
        createExcelOrderRequestDto.setLastName("Doe");
        createExcelOrderRequestDto.setOrderName("Order Name");
        createExcelOrderRequestDto.setOrderStatus("Order Status");
        createExcelOrderRequestDto.setPaymentStatus("Payment Status");
        createExcelOrderRequestDto.setPhoneNumber("6625550144");
        createExcelOrderRequestDto.setPinCode("Pin Code");
        createExcelOrderRequestDto.setProducts(new ArrayList<>());
        createExcelOrderRequestDto.setShippingAddress("42 Main St");
        createExcelOrderRequestDto.setState("MD");
        createExcelOrderRequestDto.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(createExcelOrderRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/minishop/excel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

   
    @Test
    void testOrderExcelData2() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .reason("Just cause")
                .shippingAddress("42 Main St")
                .state("MD");
        UserDto user = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();
        when(orderService.createOrderByExcelSheet(Mockito.<CreateExcelOrderRequestDto>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("user");
        user2.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user2);

        ArrayList<ExcelOrderDto> products = new ArrayList<>();
        products.add(new ExcelOrderDto("42", "Product Name", "Brand", 10.0d, 10.0d, 1, "Category", "Short Description", 1));

        CreateExcelOrderRequestDto createExcelOrderRequestDto = new CreateExcelOrderRequestDto();
        createExcelOrderRequestDto.setCity("Oxford");
        createExcelOrderRequestDto.setFirstName("Jane");
        createExcelOrderRequestDto.setLastName("Doe");
        createExcelOrderRequestDto.setOrderName("Order Name");
        createExcelOrderRequestDto.setOrderStatus("Order Status");
        createExcelOrderRequestDto.setPaymentStatus("Payment Status");
        createExcelOrderRequestDto.setPhoneNumber("6625550144");
        createExcelOrderRequestDto.setPinCode("Pin Code");
        createExcelOrderRequestDto.setProducts(products);
        createExcelOrderRequestDto.setShippingAddress("42 Main St");
        createExcelOrderRequestDto.setState("MD");
        createExcelOrderRequestDto.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(createExcelOrderRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/minishop/excel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"orderId\":\"42\",\"orderNumber\":\"42\",\"orderStatus\":\"Order Status\",\"paymentStatus\":\"Payment Status\","
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"shippingAddress\":\"42"
                                        + " Main St\",\"pinCode\":\"Pin Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"phoneNumber\":\"6625550144\",\"deliveredDate"
                                        + "\":[1970,1,1],\"createdAt\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"password\":\"ok\",\"firstName\""
                                        + ":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,\"role\":\"Role\"},\"reason\":\"Just"
                                        + " cause\"}"));
    }

   
    @Test
    void testUpdateItemInOrdere() throws Exception {
        // Arrange
        when(orderService.updateOrderItem(Mockito.<UpdateOrderItemDto>any())).thenReturn(new OrderItemDto());

        UpdateOrderItemDto updateOrderItemDto = new UpdateOrderItemDto();
        updateOrderItemDto.setOrderItemId(1);
        updateOrderItemDto.setProductId("42");
        updateOrderItemDto.setQuantity(1);
        String content = (new ObjectMapper()).writeValueAsString(updateOrderItemDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/minishop/updateOrderItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"orderItemId\":0,\"quantity\":0,\"totalPrice\":0.0,\"product\":null}"));
    }
}
