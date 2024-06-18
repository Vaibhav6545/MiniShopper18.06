package com.example.demo.Controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.Service.OrderService;
import com.example.demo.dtos.CreateOrderRequest;
import com.example.demo.dtos.OrderDto;
import com.example.demo.dtos.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

    /**
     * Method under test: {@link OrderController#createOrder(CreateOrderRequest)}
     */
    @Test
    void testCreateOrder() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .postalCode("Postal Code")
                .shippingAddress("42 Main St")
                .shippingPhone("6625550144")
                .state("MD");
        UserDto user = UserDto.builder()
                .city("Oxford")
                .email("jane.doe@example.org")
                .firstName("Jane")
                .image("Image")
                .lastName("Doe")
                .pinCode("Pin Code")
                .state("MD")
                .street("Street")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();
        when(orderService.createOrder(Mockito.<CreateOrderRequest>any())).thenReturn(buildResult);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setCartId("42");
        createOrderRequest.setCity("Oxford");
        createOrderRequest.setOrderName("Order Name");
        createOrderRequest.setOrderStatus("Order Status");
        createOrderRequest.setPaymentStatus("Payment Status");
        createOrderRequest.setPostalCode("Postal Code");
        createOrderRequest.setShippingAddress("42 Main St");
        createOrderRequest.setShippingPhone("6625550144");
        createOrderRequest.setState("MD");
        createOrderRequest.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(createOrderRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
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
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"shippingAddress\":\"42 Main St\",\"postalCode\":\"Postal"
                                        + " Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"shippingPhone\":\"6625550144\",\"deliveredDate\":[1970,1,1],\"createdAt"
                                        + "\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe"
                                        + "@example.org\",\"address\":null,\"street\":\"Street\",\"city\":\"Oxford\",\"state\":\"MD\",\"pinCode\":\"Pin"
                                        + " Code\",\"image\":\"Image\"}}"));
    }

    /**
     * Method under test: {@link OrderController#getOrderByOrderId(String)}
     */
    @Test
    void testGetOrderByOrderId() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .postalCode("Postal Code")
                .shippingAddress("42 Main St")
                .shippingPhone("6625550144")
                .state("MD");
        UserDto user = UserDto.builder()
                .city("Oxford")
                .email("jane.doe@example.org")
                .firstName("Jane")
                .image("Image")
                .lastName("Doe")
                .pinCode("Pin Code")
                .state("MD")
                .street("Street")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();
        when(orderService.fetchOrderByOrderId(Mockito.<String>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/{orderId}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"orderId\":\"42\",\"orderNumber\":\"42\",\"orderStatus\":\"Order Status\",\"paymentStatus\":\"Payment Status\","
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"shippingAddress\":\"42 Main St\",\"postalCode\":\"Postal"
                                        + " Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"shippingPhone\":\"6625550144\",\"deliveredDate\":[1970,1,1],\"createdAt"
                                        + "\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe"
                                        + "@example.org\",\"address\":null,\"street\":\"Street\",\"city\":\"Oxford\",\"state\":\"MD\",\"pinCode\":\"Pin"
                                        + " Code\",\"image\":\"Image\"}}"));
    }

    /**
     * Method under test: {@link OrderController#getOrderByUserId(String)}
     */
    @Test
    void testGetOrderByUserId() throws Exception {
        // Arrange
        when(orderService.fetchOrderByUser(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/user/{userId}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link OrderController#getOrderByUserId(String)}
     */
    @Test
    void testGetOrderByUserId2() throws Exception {
        // Arrange
        Date createdAt = mock(Date.class);
        when(createdAt.getTime()).thenReturn(10L);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(createdAt);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(LocalDate.of(1970, 1, 1))
                .orderAmount(10.0d)
                .orderId("42");
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(new ArrayList<>())
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .postalCode("Postal Code")
                .shippingAddress("42 Main St")
                .shippingPhone("6625550144")
                .state("MD");
        UserDto user = UserDto.builder()
                .city("Oxford")
                .email("jane.doe@example.org")
                .firstName("Jane")
                .image("Image")
                .lastName("Doe")
                .pinCode("Pin Code")
                .state("MD")
                .street("Street")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user).build();
        when(orderService.fetchOrderByOrderId(Mockito.<String>any())).thenReturn(buildResult);
        when(orderService.fetchOrderByUser(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/user/{userId}", "",
                "Uri Variables");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"orderId\":\"42\",\"orderNumber\":\"42\",\"orderStatus\":\"Order Status\",\"paymentStatus\":\"Payment Status\","
                                        + "\"orderAmount\":10.0,\"orderName\":\"Order Name\",\"shippingAddress\":\"42 Main St\",\"postalCode\":\"Postal"
                                        + " Code\",\"city\":\"Oxford\",\"state\":\"MD\",\"shippingPhone\":\"6625550144\",\"deliveredDate\":[1970,1,1],\"createdAt"
                                        + "\":10,\"orderItems\":[],\"user\":{\"userId\":\"42\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe"
                                        + "@example.org\",\"address\":null,\"street\":\"Street\",\"city\":\"Oxford\",\"state\":\"MD\",\"pinCode\":\"Pin"
                                        + " Code\",\"image\":\"Image\"}}"));
    }
}
