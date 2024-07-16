package minishopper.Service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
import minishopper.Entity.Cart;
import minishopper.Entity.CartItem;
import minishopper.Entity.Order;
import minishopper.Entity.OrderItem;
import minishopper.Entity.Product;
import minishopper.Entity.User;
import minishopper.exception.ResourceNotFoundException;
import minishopper.Repository.CartRepository;
import minishopper.Repository.OrderItemRepository;
import minishopper.Repository.OrderRepository;
import minishopper.Repository.ProductRepository;
import minishopper.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OrderServiceImplTest {
    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

   
    @Test
    void testCreateOrder() throws ResourceNotFoundException {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
        LocalDate deliveredDate = LocalDate.of(1970, 1, 1);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(deliveredDate)
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        ArrayList<OrderItemDto> orderItems = new ArrayList<>();
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(orderItems)
                .orderName("Order Name")
                .orderNumber("42")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .reason("Just cause")
                .shippingAddress("42 Main St")
                .state("MD");
        UserDto user3 = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        OrderDto buildResult = stateResult.user(user3).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

        User user4 = new User();
        user4.setAddresses(new ArrayList<>());
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setLastName("Doe");
        user4.setOrders(new ArrayList<>());
        user4.setPassword("ok");
        user4.setRole("Role");
        user4.setUserId("42");

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
        order.setUser(user4);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);

        User user5 = new User();
        user5.setAddresses(new ArrayList<>());
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setLastName("Doe");
        user5.setOrders(new ArrayList<>());
        user5.setPassword("ok");
        user5.setRole("Role");
        user5.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user5);
        CreateOrderRequestDto orderRequest = new CreateOrderRequestDto("42", "42", "Order Status", "Payment Status",
                "Order Name", "42 Main St", "Pin Code", "Oxford", "MD", "6625550144");

        OrderDto actualCreateOrderResult = orderServiceImpl.createOrder(orderRequest);
        verify(cartRepository).findByCartId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(cartRepository).save(isA(Cart.class));
        verify(orderRepository).save(isA(Order.class));
        LocalDate deliveredDate2 = actualCreateOrderResult.getDeliveredDate();
        assertEquals("1970-01-01", deliveredDate2.toString());
        assertEquals("42 Main St", actualCreateOrderResult.getShippingAddress());
        assertEquals("42", actualCreateOrderResult.getOrderId());
        assertEquals("42", actualCreateOrderResult.getOrderNumber());
        UserDto user6 = actualCreateOrderResult.getUser();
        assertEquals("42", user6.getUserId());
        assertEquals("6625550144", actualCreateOrderResult.getPhoneNumber());
        assertEquals("Doe", actualCreateOrderResult.getLastName());
        assertEquals("Doe", user6.getLastName());
        assertEquals("Jane", actualCreateOrderResult.getFirstName());
        assertEquals("Jane", user6.getFirstName());
        assertEquals("Just cause", actualCreateOrderResult.getReason());
        assertEquals("MD", actualCreateOrderResult.getState());
        assertEquals("Order Name", actualCreateOrderResult.getOrderName());
        assertEquals("Order Status", actualCreateOrderResult.getOrderStatus());
        assertEquals("Oxford", actualCreateOrderResult.getCity());
        assertEquals("Payment Status", actualCreateOrderResult.getPaymentStatus());
        assertEquals("Pin Code", actualCreateOrderResult.getPinCode());
        assertEquals("Role", user6.getRole());
        assertEquals("ok", user6.getPassword());
        assertEquals("jane.doe@example.org", user6.getEmail());
        assertNull(user6.getAddress());
        assertEquals(10.0d, actualCreateOrderResult.getOrderAmount());
        assertSame(orderItems, actualCreateOrderResult.getOrderItems());
        assertSame(deliveredDate, deliveredDate2);
    }

  
   @Test
    void testCreateOrder2() throws ResourceNotFoundException {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
      
    }

   
    @Test
    void testCreateOrder3() throws ResourceNotFoundException {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setUser(user);
        when(cartRepository.save(Mockito.<Cart>any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

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
        order.setUser(user2);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("ok");
        user3.setRole("Role");
        user3.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user3);
        CreateOrderRequestDto orderRequest = CreateOrderRequestDto.builder()
                .cartId("42")
                .city("Oxford")
                .firstName("Jane")
                .lastName("Doe")
                .orderName("Order Name")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .shippingAddress("42 Main St")
                .state("MD")
                .userId("42")
                .build();
        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.createOrder(orderRequest));
        verify(cartRepository).findByCartId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(cartRepository).save(isA(Cart.class));
        verify(orderRepository).save(isA(Order.class));
    }

   
    @Test
    void testCreateOrder4() throws ResourceNotFoundException {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setImage("Image");
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setShortDescription("Short Description");
        product.setStock(1000);
        product.setUnitPrice(10.0d);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setCartItemId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1000);
        cartItem.setTotalPrice(10.0d);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(items);
        cart2.setUser(user2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart2);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("ok");
        user3.setRole("Role");
        user3.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user3);
        CreateOrderRequestDto orderRequest = CreateOrderRequestDto.builder()
                .cartId("42")
                .city("Oxford")
                .firstName("Jane")
                .lastName("Doe")
                .orderName("Order Name")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .shippingAddress("42 Main St")
                .state("MD")
                .userId("42")
                .build();
        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.createOrder(orderRequest));
        verify(cartRepository).findByCartId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
    }

    
    @Test
    void testCreateOrder5() throws ResourceNotFoundException {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setUser(user);

        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setImage("Image");
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setShortDescription("Short Description");
        product.setStock(1000);
        product.setUnitPrice(10.0d);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setCartItemId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1000);
        cartItem.setTotalPrice(10.0d);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("john.smith@example.org");
        user2.setFirstName("John");
        user2.setLastName("Smith");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("you cannot order more than 50 items in one order");
        user2.setRole("Role");
        user2.setUserId("you cannot order more than 50 items in one order");

        Cart cart2 = new Cart();
        cart2.setCartId("you cannot order more than 50 items in one order");
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);

        Product product2 = new Product();
        product2.setBrand("Brand");
        product2.setCategory("Category");
        product2.setDiscountedPrice(0.0d);
        product2.setImage("Image");
        product2.setProductId("you cannot order more than 50 items in one order");
        product2.setProductName("Product Name");
        product2.setShortDescription("Short Description");
        product2.setStock(1);
        product2.setUnitPrice(0.0d);

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setCartItemId(2);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);
        cartItem2.setTotalPrice(0.0d);

        ArrayList<CartItem> items = new ArrayList<>();
        items.add(cartItem2);
        items.add(cartItem);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("ok");
        user3.setRole("Role");
        user3.setUserId("42");

        Cart cart3 = new Cart();
        cart3.setCartId("42");
        cart3.setItems(items);
        cart3.setUser(user3);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart3);

        User user4 = new User();
        user4.setAddresses(new ArrayList<>());
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setLastName("Doe");
        user4.setOrders(new ArrayList<>());
        user4.setPassword("ok");
        user4.setRole("Role");
        user4.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user4);
        CreateOrderRequestDto orderRequest = CreateOrderRequestDto.builder()
                .cartId("42")
                .city("Oxford")
                .firstName("Jane")
                .lastName("Doe")
                .orderName("Order Name")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .shippingAddress("42 Main St")
                .state("MD")
                .userId("42")
                .build();
        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.createOrder(orderRequest));
        verify(cartRepository).findByCartId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
    }

   
    @Test
    void testCreateOrderSingleProduct() {
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
        LocalDate deliveredDate = LocalDate.of(1970, 1, 1);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(deliveredDate)
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        ArrayList<OrderItemDto> orderItems = new ArrayList<>();
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(orderItems)
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

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
        order.setUser(user2);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);

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

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("ok");
        user3.setRole("Role");
        user3.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user3);
        CreateSingleProductOrderRequestDto orderRequest = new CreateSingleProductOrderRequestDto("42", "Order Status",
                "Payment Status", "Order Name", "Jane", "Doe", "42 Main St", "Pin Code", "Oxford", "MD", "6625550144", "42", 1);

        OrderDto actualCreateOrderSingleProductResult = orderServiceImpl.createOrderSingleProduct(orderRequest);
        verify(productRepository).findByProductId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(orderRepository).save(isA(Order.class));
        LocalDate deliveredDate2 = actualCreateOrderSingleProductResult.getDeliveredDate();
        assertEquals("1970-01-01", deliveredDate2.toString());
        assertEquals("42 Main St", actualCreateOrderSingleProductResult.getShippingAddress());
        assertEquals("42", actualCreateOrderSingleProductResult.getOrderId());
        assertEquals("42", actualCreateOrderSingleProductResult.getOrderNumber());
        UserDto user4 = actualCreateOrderSingleProductResult.getUser();
        assertEquals("42", user4.getUserId());
        assertEquals("6625550144", actualCreateOrderSingleProductResult.getPhoneNumber());
        assertEquals("Doe", actualCreateOrderSingleProductResult.getLastName());
        assertEquals("Doe", user4.getLastName());
        assertEquals("Jane", actualCreateOrderSingleProductResult.getFirstName());
        assertEquals("Jane", user4.getFirstName());
        assertEquals("Just cause", actualCreateOrderSingleProductResult.getReason());
        assertEquals("MD", actualCreateOrderSingleProductResult.getState());
        assertEquals("Order Name", actualCreateOrderSingleProductResult.getOrderName());
        assertEquals("Order Status", actualCreateOrderSingleProductResult.getOrderStatus());
        assertEquals("Oxford", actualCreateOrderSingleProductResult.getCity());
        assertEquals("Payment Status", actualCreateOrderSingleProductResult.getPaymentStatus());
        assertEquals("Pin Code", actualCreateOrderSingleProductResult.getPinCode());
        assertEquals("Role", user4.getRole());
        assertEquals("ok", user4.getPassword());
        assertEquals("jane.doe@example.org", user4.getEmail());
        assertNull(user4.getAddress());
        assertEquals(10.0d, actualCreateOrderSingleProductResult.getOrderAmount());
        List<OrderItemDto> orderItems2 = actualCreateOrderSingleProductResult.getOrderItems();
        assertTrue(orderItems2.isEmpty());
        assertSame(orderItems, orderItems2);
        assertSame(deliveredDate, deliveredDate2);
    }

    
    @Test
    void testCreateOrderSingleProduct2() {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
    }

   
    @Test
    void testCreateOrderSingleProduct3() {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

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
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);

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

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user2);
        CreateSingleProductOrderRequestDto orderRequest = CreateSingleProductOrderRequestDto.builder()
                .city("Oxford")
                .firstName("Jane")
                .lastName("Doe")
                .orderName("Order Name")
                .orderStatus("Order Status")
                .paymentStatus("Payment Status")
                .phoneNumber("6625550144")
                .pinCode("Pin Code")
                .productId("42")
                .quantity(1)
                .shippingAddress("42 Main St")
                .state("MD")
                .userId("42")
                .build();
        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.createOrderSingleProduct(orderRequest));
        verify(productRepository).findByProductId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(orderRepository).save(isA(Order.class));
    }

   
    @Test
    void testCreateOrderByExcelSheet() throws ResourceNotFoundException {
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
        LocalDate deliveredDate = LocalDate.of(1970, 1, 1);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(deliveredDate)
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        ArrayList<OrderItemDto> orderItems = new ArrayList<>();
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(orderItems)
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

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
        order.setUser(user2);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("ok");
        user3.setRole("Role");
        user3.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user3);

        CreateExcelOrderRequestDto orderRequest = new CreateExcelOrderRequestDto();
        orderRequest.setProducts(new ArrayList<>());
        OrderDto actualCreateOrderByExcelSheetResult = orderServiceImpl.createOrderByExcelSheet(orderRequest);
        verify(userRepository).findByUserId(isNull());
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(orderRepository).save(isA(Order.class));
        LocalDate deliveredDate2 = actualCreateOrderByExcelSheetResult.getDeliveredDate();
        assertEquals("1970-01-01", deliveredDate2.toString());
        assertEquals("42 Main St", actualCreateOrderByExcelSheetResult.getShippingAddress());
        assertEquals("42", actualCreateOrderByExcelSheetResult.getOrderId());
        assertEquals("42", actualCreateOrderByExcelSheetResult.getOrderNumber());
        UserDto user4 = actualCreateOrderByExcelSheetResult.getUser();
        assertEquals("42", user4.getUserId());
        assertEquals("6625550144", actualCreateOrderByExcelSheetResult.getPhoneNumber());
        assertEquals("Doe", actualCreateOrderByExcelSheetResult.getLastName());
        assertEquals("Doe", user4.getLastName());
        assertEquals("Jane", actualCreateOrderByExcelSheetResult.getFirstName());
        assertEquals("Jane", user4.getFirstName());
        assertEquals("Just cause", actualCreateOrderByExcelSheetResult.getReason());
        assertEquals("MD", actualCreateOrderByExcelSheetResult.getState());
        assertEquals("Order Name", actualCreateOrderByExcelSheetResult.getOrderName());
        assertEquals("Order Status", actualCreateOrderByExcelSheetResult.getOrderStatus());
        assertEquals("Oxford", actualCreateOrderByExcelSheetResult.getCity());
        assertEquals("Payment Status", actualCreateOrderByExcelSheetResult.getPaymentStatus());
        assertEquals("Pin Code", actualCreateOrderByExcelSheetResult.getPinCode());
        assertEquals("Role", user4.getRole());
        assertEquals("ok", user4.getPassword());
        assertEquals("jane.doe@example.org", user4.getEmail());
        assertNull(user4.getAddress());
        assertEquals(10.0d, actualCreateOrderByExcelSheetResult.getOrderAmount());
        List<OrderItemDto> orderItems2 = actualCreateOrderByExcelSheetResult.getOrderItems();
        assertTrue(orderItems2.isEmpty());
        assertSame(orderItems, orderItems2);
        assertSame(deliveredDate, deliveredDate2);
    }

    
    @Test
    void testCreateOrderByExcelSheet2() throws ResourceNotFoundException {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
       
    }

   
    @Test
    void testCreateOrderByExcelSheet3() throws ResourceNotFoundException {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
    }    
    @Test
    void testFetchOrderByUser() {
        when(orderRepository.findByUser(Mockito.<User>any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user);
        String userId = "42";
        List<OrderDto> actualFetchOrderByUserResult = orderServiceImpl.fetchOrderByUser(userId);
        verify(orderRepository).findByUser(isA(User.class));
        verify(userRepository).findByUserId(eq("42"));
        assertTrue(actualFetchOrderByUserResult.isEmpty());
    }

    @Test
    void testFetchOrderByUser2() {
        when(userRepository.findByUserId(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        String userId = "42";
        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.fetchOrderByUser(userId));
        verify(userRepository).findByUserId(eq("42"));
    }

    
    @Test
    void testFetchOrderByUser3() {
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
        LocalDate deliveredDate = LocalDate.of(1970, 1, 1);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(deliveredDate)
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        ArrayList<OrderItemDto> orderItems = new ArrayList<>();
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(orderItems)
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

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
        order.setUser(user2);

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findByUser(Mockito.<User>any())).thenReturn(orderList);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("ok");
        user3.setRole("Role");
        user3.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user3);
        String userId = "42";
        List<OrderDto> actualFetchOrderByUserResult = orderServiceImpl.fetchOrderByUser(userId);
        verify(orderRepository).findByUser(isA(User.class));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        assertEquals(1, actualFetchOrderByUserResult.size());
        OrderDto getResult = actualFetchOrderByUserResult.get(0);
        LocalDate deliveredDate2 = getResult.getDeliveredDate();
        assertEquals("1970-01-01", deliveredDate2.toString());
        assertEquals("42 Main St", getResult.getShippingAddress());
        assertEquals("42", getResult.getOrderId());
        assertEquals("42", getResult.getOrderNumber());
        UserDto user4 = getResult.getUser();
        assertEquals("42", user4.getUserId());
        assertEquals("6625550144", getResult.getPhoneNumber());
        assertEquals("Doe", getResult.getLastName());
        assertEquals("Doe", user4.getLastName());
        assertEquals("Jane", getResult.getFirstName());
        assertEquals("Jane", user4.getFirstName());
        assertEquals("Just cause", getResult.getReason());
        assertEquals("MD", getResult.getState());
        assertEquals("Order Name", getResult.getOrderName());
        assertEquals("Order Status", getResult.getOrderStatus());
        assertEquals("Oxford", getResult.getCity());
        assertEquals("Payment Status", getResult.getPaymentStatus());
        assertEquals("Pin Code", getResult.getPinCode());
        assertEquals("Role", user4.getRole());
        assertEquals("ok", user4.getPassword());
        assertEquals("jane.doe@example.org", user4.getEmail());
        assertNull(user4.getAddress());
        assertEquals(10.0d, getResult.getOrderAmount());
        List<OrderItemDto> orderItems2 = getResult.getOrderItems();
        assertTrue(orderItems2.isEmpty());
        assertSame(orderItems, orderItems2);
        assertSame(deliveredDate, deliveredDate2);
    }

  
    @Test
    void testFetchOrderByUser4() {
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
        LocalDate deliveredDate = LocalDate.of(1970, 1, 1);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(deliveredDate)
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        ArrayList<OrderItemDto> orderItems = new ArrayList<>();
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(orderItems)
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

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
        order.setUser(user2);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("john.smith@example.org");
        user3.setFirstName("John");
        user3.setLastName("Smith");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("Password");
        user3.setRole("42");
        user3.setUserId("User Id");

        Order order2 = new Order();
        order2.setCity("London");
        order2.setCreatedAt(mock(Date.class));
        order2.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order2.setFirstName("John");
        order2.setLastName("Smith");
        order2.setOrderAmount(0.5d);
        order2.setOrderId("Order Id");
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderName("42");
        order2.setOrderNumber("Order Number");
        order2.setOrderStatus("42");
        order2.setPaymentStatus("42");
        order2.setPhoneNumber("8605550118");
        order2.setPinCode("42");
        order2.setReason("Reason");
        order2.setShippingAddress("17 High St");
        order2.setState("State");
        order2.setUser(user3);

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order2);
        orderList.add(order);
        when(orderRepository.findByUser(Mockito.<User>any())).thenReturn(orderList);

        User user4 = new User();
        user4.setAddresses(new ArrayList<>());
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setLastName("Doe");
        user4.setOrders(new ArrayList<>());
        user4.setPassword("ok");
        user4.setRole("Role");
        user4.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user4);
        String userId = "42";
        List<OrderDto> actualFetchOrderByUserResult = orderServiceImpl.fetchOrderByUser(userId);
        verify(orderRepository).findByUser(isA(User.class));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), isA(Class.class));
        assertEquals(2, actualFetchOrderByUserResult.size());
        OrderDto getResult = actualFetchOrderByUserResult.get(0);
        LocalDate deliveredDate2 = getResult.getDeliveredDate();
        assertEquals("1970-01-01", deliveredDate2.toString());
        assertEquals("42 Main St", getResult.getShippingAddress());
        assertEquals("42", getResult.getOrderId());
        assertEquals("42", getResult.getOrderNumber());
        UserDto user5 = getResult.getUser();
        assertEquals("42", user5.getUserId());
        assertEquals("6625550144", getResult.getPhoneNumber());
        assertEquals("Doe", getResult.getLastName());
        assertEquals("Doe", user5.getLastName());
        assertEquals("Jane", getResult.getFirstName());
        assertEquals("Jane", user5.getFirstName());
        assertEquals("Just cause", getResult.getReason());
        assertEquals("MD", getResult.getState());
        assertEquals("Order Name", getResult.getOrderName());
        assertEquals("Order Status", getResult.getOrderStatus());
        assertEquals("Oxford", getResult.getCity());
        assertEquals("Payment Status", getResult.getPaymentStatus());
        assertEquals("Pin Code", getResult.getPinCode());
        assertEquals("Role", user5.getRole());
        assertEquals("ok", user5.getPassword());
        assertEquals("jane.doe@example.org", user5.getEmail());
        assertNull(user5.getAddress());
        assertEquals(10.0d, getResult.getOrderAmount());
        List<OrderItemDto> orderItems2 = getResult.getOrderItems();
        assertTrue(orderItems2.isEmpty());
        assertSame(orderItems, orderItems2);
        assertSame(getResult, actualFetchOrderByUserResult.get(1));
        assertSame(deliveredDate, deliveredDate2);
    }

   
    @Test
    void testFetchOrderByOrderId() throws ResourceNotFoundException {
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
        LocalDate deliveredDate = LocalDate.of(1970, 1, 1);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(deliveredDate)
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        ArrayList<OrderItemDto> orderItems = new ArrayList<>();
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(orderItems)
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

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
        order.setUser(user2);
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);
        String orderId = "42";
        OrderDto actualFetchOrderByOrderIdResult = orderServiceImpl.fetchOrderByOrderId(orderId);
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        LocalDate deliveredDate2 = actualFetchOrderByOrderIdResult.getDeliveredDate();
        assertEquals("1970-01-01", deliveredDate2.toString());
        assertEquals("42 Main St", actualFetchOrderByOrderIdResult.getShippingAddress());
        assertEquals("42", actualFetchOrderByOrderIdResult.getOrderId());
        assertEquals("42", actualFetchOrderByOrderIdResult.getOrderNumber());
        UserDto user3 = actualFetchOrderByOrderIdResult.getUser();
        assertEquals("42", user3.getUserId());
        assertEquals("6625550144", actualFetchOrderByOrderIdResult.getPhoneNumber());
        assertEquals("Doe", actualFetchOrderByOrderIdResult.getLastName());
        assertEquals("Doe", user3.getLastName());
        assertEquals("Jane", actualFetchOrderByOrderIdResult.getFirstName());
        assertEquals("Jane", user3.getFirstName());
        assertEquals("Just cause", actualFetchOrderByOrderIdResult.getReason());
        assertEquals("MD", actualFetchOrderByOrderIdResult.getState());
        assertEquals("Order Name", actualFetchOrderByOrderIdResult.getOrderName());
        assertEquals("Order Status", actualFetchOrderByOrderIdResult.getOrderStatus());
        assertEquals("Oxford", actualFetchOrderByOrderIdResult.getCity());
        assertEquals("Payment Status", actualFetchOrderByOrderIdResult.getPaymentStatus());
        assertEquals("Pin Code", actualFetchOrderByOrderIdResult.getPinCode());
        assertEquals("Role", user3.getRole());
        assertEquals("ok", user3.getPassword());
        assertEquals("jane.doe@example.org", user3.getEmail());
        assertNull(user3.getAddress());
        assertEquals(10.0d, actualFetchOrderByOrderIdResult.getOrderAmount());
        List<OrderItemDto> orderItems2 = actualFetchOrderByOrderIdResult.getOrderItems();
        assertTrue(orderItems2.isEmpty());
        assertSame(orderItems, orderItems2);
        assertSame(deliveredDate, deliveredDate2);
    }

    
    @Test
    void testFetchOrderByOrderId2() throws ResourceNotFoundException {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

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
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);
        String orderId = "42";
        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.fetchOrderByOrderId(orderId));
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }

    @Test
    void testRemoveOrderItemByOrderItemId() {
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
        doNothing().when(orderItemRepository).deleteById(Mockito.<Integer>any());
        when(orderItemRepository.findById(anyInt())).thenReturn(orderItem);
        int orderItemId = 1;
        OrderItem actualRemoveOrderItemByOrderItemIdResult = orderServiceImpl.removeOrderItemByOrderItemId(orderItemId);
        verify(orderItemRepository).findById(eq(1));
        verify(orderItemRepository).deleteById(eq(1));
        assertSame(orderItem, actualRemoveOrderItemByOrderItemIdResult);
    }

  
    @Test
    void testRemoveOrderItemByOrderItemId2() {
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
        doThrow(new ResourceNotFoundException("An error occurred")).when(orderItemRepository)
                .deleteById(Mockito.<Integer>any());
        when(orderItemRepository.findById(anyInt())).thenReturn(orderItem);
        int orderItemId = 1;
        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.removeOrderItemByOrderItemId(orderItemId));
        verify(orderItemRepository).findById(eq(1));
        verify(orderItemRepository).deleteById(eq(1));
    }

   
    @Test
    void testUpdateTotalPrice() {
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
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order2);
        doNothing().when(orderRepository).deleteById(Mockito.<String>any());
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("ok");
        user3.setRole("Role");
        user3.setUserId("42");

        Order order3 = new Order();
        order3.setCity("Oxford");
        order3.setCreatedAt(mock(Date.class));
        order3.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order3.setFirstName("Jane");
        order3.setLastName("Doe");
        order3.setOrderAmount(10.0d);
        order3.setOrderId("42");
        order3.setOrderItems(new ArrayList<>());
        order3.setOrderName("Order Name");
        order3.setOrderNumber("42");
        order3.setOrderStatus("Order Status");
        order3.setPaymentStatus("Payment Status");
        order3.setPhoneNumber("6625550144");
        order3.setPinCode("Pin Code");
        order3.setReason("Just cause");
        order3.setShippingAddress("42 Main St");
        order3.setState("MD");
        order3.setUser(user3);

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
        orderItem.setOrder(order3);
        orderItem.setOrderItemId(1);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(10.0d);
        orderServiceImpl.updateTotalPrice(orderItem);
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(orderRepository).deleteById(eq("42"));
        verify(orderRepository).save(isA(Order.class));
    }

    
    @Test
    void testUpdateTotalPrice2() {
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
        doThrow(new ResourceNotFoundException("An error occurred")).when(orderRepository).deleteById(Mockito.<String>any());
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);

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
        orderItem.setOrder(order2);
        orderItem.setOrderItemId(1);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(10.0d);
        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.updateTotalPrice(orderItem));
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(orderRepository).deleteById(eq("42"));
    }

    
    @Test
    void testUpdateTotalPrice3() {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");
        Order order = mock(Order.class);
        when(order.getOrderAmount()).thenReturn(10.0d);
        doNothing().when(order).setCity(Mockito.<String>any());
        doNothing().when(order).setCreatedAt(Mockito.<Date>any());
        doNothing().when(order).setDeliveredDate(Mockito.<LocalDate>any());
        doNothing().when(order).setFirstName(Mockito.<String>any());
        doNothing().when(order).setLastName(Mockito.<String>any());
        doNothing().when(order).setOrderAmount(anyDouble());
        doNothing().when(order).setOrderId(Mockito.<String>any());
        doNothing().when(order).setOrderItems(Mockito.<List<OrderItem>>any());
        doNothing().when(order).setOrderName(Mockito.<String>any());
        doNothing().when(order).setOrderNumber(Mockito.<String>any());
        doNothing().when(order).setOrderStatus(Mockito.<String>any());
        doNothing().when(order).setPaymentStatus(Mockito.<String>any());
        doNothing().when(order).setPhoneNumber(Mockito.<String>any());
        doNothing().when(order).setPinCode(Mockito.<String>any());
        doNothing().when(order).setReason(Mockito.<String>any());
        doNothing().when(order).setShippingAddress(Mockito.<String>any());
        doNothing().when(order).setState(Mockito.<String>any());
        doNothing().when(order).setUser(Mockito.<User>any());
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
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order2);
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("ok");
        user3.setRole("Role");
        user3.setUserId("42");

        Order order3 = new Order();
        order3.setCity("Oxford");
        order3.setCreatedAt(mock(Date.class));
        order3.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order3.setFirstName("Jane");
        order3.setLastName("Doe");
        order3.setOrderAmount(10.0d);
        order3.setOrderId("42");
        order3.setOrderItems(new ArrayList<>());
        order3.setOrderName("Order Name");
        order3.setOrderNumber("42");
        order3.setOrderStatus("Order Status");
        order3.setPaymentStatus("Payment Status");
        order3.setPhoneNumber("6625550144");
        order3.setPinCode("Pin Code");
        order3.setReason("Just cause");
        order3.setShippingAddress("42 Main St");
        order3.setState("MD");
        order3.setUser(user3);

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
        orderItem.setOrder(order3);
        orderItem.setOrderItemId(1);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(10.0d);
        orderServiceImpl.updateTotalPrice(orderItem);
        verify(order, atLeast(1)).getOrderAmount();
        verify(order).setCity(eq("Oxford"));
        verify(order).setCreatedAt(isA(Date.class));
        verify(order).setDeliveredDate(isA(LocalDate.class));
        verify(order).setFirstName(eq("Jane"));
        verify(order).setLastName(eq("Doe"));
        verify(order, atLeast(1)).setOrderAmount(anyDouble());
        verify(order).setOrderId(eq("42"));
        verify(order).setOrderItems(isA(List.class));
        verify(order).setOrderName(eq("Order Name"));
        verify(order).setOrderNumber(eq("42"));
        verify(order).setOrderStatus(eq("Order Status"));
        verify(order).setPaymentStatus(eq("Payment Status"));
        verify(order).setPhoneNumber(eq("6625550144"));
        verify(order).setPinCode(eq("Pin Code"));
        verify(order).setReason(eq("Just cause"));
        verify(order).setShippingAddress(eq("42 Main St"));
        verify(order).setState(eq("MD"));
        verify(order).setUser(isA(User.class));
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(orderRepository).save(isA(Order.class));
    }

   
    @Test
    void testUpdateOrderItem() throws ResourceNotFoundException {
        OrderItemDto orderItemDto = new OrderItemDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderItemDto>>any())).thenReturn(orderItemDto);

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
        when(orderItemRepository.save(Mockito.<OrderItem>any())).thenReturn(orderItem2);
        when(orderItemRepository.findById(anyInt())).thenReturn(orderItem);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("ok");
        user3.setRole("Role");
        user3.setUserId("42");

        Order order3 = new Order();
        order3.setCity("Oxford");
        order3.setCreatedAt(mock(Date.class));
        order3.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order3.setFirstName("Jane");
        order3.setLastName("Doe");
        order3.setOrderAmount(10.0d);
        order3.setOrderId("42");
        order3.setOrderItems(new ArrayList<>());
        order3.setOrderName("Order Name");
        order3.setOrderNumber("42");
        order3.setOrderStatus("Order Status");
        order3.setPaymentStatus("Payment Status");
        order3.setPhoneNumber("6625550144");
        order3.setPinCode("Pin Code");
        order3.setReason("Just cause");
        order3.setShippingAddress("42 Main St");
        order3.setState("MD");
        order3.setUser(user3);

        User user4 = new User();
        user4.setAddresses(new ArrayList<>());
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setLastName("Doe");
        user4.setOrders(new ArrayList<>());
        user4.setPassword("ok");
        user4.setRole("Role");
        user4.setUserId("42");

        Order order4 = new Order();
        order4.setCity("Oxford");
        order4.setCreatedAt(mock(Date.class));
        order4.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order4.setFirstName("Jane");
        order4.setLastName("Doe");
        order4.setOrderAmount(10.0d);
        order4.setOrderId("42");
        order4.setOrderItems(new ArrayList<>());
        order4.setOrderName("Order Name");
        order4.setOrderNumber("42");
        order4.setOrderStatus("Order Status");
        order4.setPaymentStatus("Payment Status");
        order4.setPhoneNumber("6625550144");
        order4.setPinCode("Pin Code");
        order4.setReason("Just cause");
        order4.setShippingAddress("42 Main St");
        order4.setState("MD");
        order4.setUser(user4);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order4);
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order3);

        Product product3 = new Product();
        product3.setBrand("Brand");
        product3.setCategory("Category");
        product3.setDiscountedPrice(10.0d);
        product3.setImage("Image");
        product3.setProductId("42");
        product3.setProductName("Product Name");
        product3.setShortDescription("Short Description");
        product3.setStock(1);
        product3.setUnitPrice(10.0d);
        when(productRepository.findByProductId(Mockito.<String>any())).thenReturn(product3);
        UpdateOrderItemDto updateOrderItem = new UpdateOrderItemDto(1, 1, "42");

        OrderItemDto actualUpdateOrderItemResult = orderServiceImpl.updateOrderItem(updateOrderItem);
        verify(orderItemRepository).findById(eq(1));
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(productRepository).findByProductId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(orderRepository).save(isA(Order.class));
        verify(orderItemRepository).save(isA(OrderItem.class));
        assertSame(orderItemDto, actualUpdateOrderItemResult);
    }

   
    @Test
    void testFetchAllOrders() {
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());
        List<OrderDto> actualFetchAllOrdersResult = orderServiceImpl.fetchAllOrders();
        verify(orderRepository).findAll();
        assertTrue(actualFetchAllOrdersResult.isEmpty());
    }

    
    @Test
    void testFetchAllOrders2() {
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
        LocalDate deliveredDate = LocalDate.of(1970, 1, 1);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(deliveredDate)
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        ArrayList<OrderItemDto> orderItems = new ArrayList<>();
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(orderItems)
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

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
        order.setUser(user2);

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findAll()).thenReturn(orderList);
        List<OrderDto> actualFetchAllOrdersResult = orderServiceImpl.fetchAllOrders();
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(orderRepository).findAll();
        assertEquals(1, actualFetchAllOrdersResult.size());
        OrderDto getResult = actualFetchAllOrdersResult.get(0);
        LocalDate deliveredDate2 = getResult.getDeliveredDate();
        assertEquals("1970-01-01", deliveredDate2.toString());
        assertEquals("42 Main St", getResult.getShippingAddress());
        assertEquals("42", getResult.getOrderId());
        assertEquals("42", getResult.getOrderNumber());
        UserDto user3 = getResult.getUser();
        assertEquals("42", user3.getUserId());
        assertEquals("6625550144", getResult.getPhoneNumber());
        assertEquals("Doe", getResult.getLastName());
        assertEquals("Doe", user3.getLastName());
        assertEquals("Jane", getResult.getFirstName());
        assertEquals("Jane", user3.getFirstName());
        assertEquals("Just cause", getResult.getReason());
        assertEquals("MD", getResult.getState());
        assertEquals("Order Name", getResult.getOrderName());
        assertEquals("Order Status", getResult.getOrderStatus());
        assertEquals("Oxford", getResult.getCity());
        assertEquals("Payment Status", getResult.getPaymentStatus());
        assertEquals("Pin Code", getResult.getPinCode());
        assertEquals("Role", user3.getRole());
        assertEquals("ok", user3.getPassword());
        assertEquals("jane.doe@example.org", user3.getEmail());
        assertNull(user3.getAddress());
        assertEquals(10.0d, getResult.getOrderAmount());
        List<OrderItemDto> orderItems2 = getResult.getOrderItems();
        assertTrue(orderItems2.isEmpty());
        assertSame(orderItems, orderItems2);
        assertSame(deliveredDate, deliveredDate2);
    }

   
    @Test
    void testFetchAllOrders3() {
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
        LocalDate deliveredDate = LocalDate.of(1970, 1, 1);
        OrderDto.OrderDtoBuilder orderIdResult = createdAtResult.deliveredDate(deliveredDate)
                .firstName("Jane")
                .lastName("Doe")
                .orderAmount(10.0d)
                .orderId("42");
        ArrayList<OrderItemDto> orderItems = new ArrayList<>();
        OrderDto.OrderDtoBuilder stateResult = orderIdResult.orderItems(orderItems)
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");

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
        order.setUser(user2);

        User user3 = new User();
        user3.setAddresses(new ArrayList<>());
        user3.setEmail("john.smith@example.org");
        user3.setFirstName("John");
        user3.setLastName("Smith");
        user3.setOrders(new ArrayList<>());
        user3.setPassword("Password");
        user3.setRole("42");
        user3.setUserId("User Id");

        Order order2 = new Order();
        order2.setCity("London");
        order2.setCreatedAt(mock(Date.class));
        order2.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order2.setFirstName("John");
        order2.setLastName("Smith");
        order2.setOrderAmount(0.5d);
        order2.setOrderId("Order Id");
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderName("42");
        order2.setOrderNumber("Order Number");
        order2.setOrderStatus("42");
        order2.setPaymentStatus("42");
        order2.setPhoneNumber("8605550118");
        order2.setPinCode("42");
        order2.setReason("Reason");
        order2.setShippingAddress("17 High St");
        order2.setState("State");
        order2.setUser(user3);

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order2);
        orderList.add(order);
        when(orderRepository.findAll()).thenReturn(orderList);
        List<OrderDto> actualFetchAllOrdersResult = orderServiceImpl.fetchAllOrders();
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), isA(Class.class));
        verify(orderRepository).findAll();
        assertEquals(2, actualFetchAllOrdersResult.size());
        OrderDto getResult = actualFetchAllOrdersResult.get(0);
        LocalDate deliveredDate2 = getResult.getDeliveredDate();
        assertEquals("1970-01-01", deliveredDate2.toString());
        assertEquals("42 Main St", getResult.getShippingAddress());
        assertEquals("42", getResult.getOrderId());
        assertEquals("42", getResult.getOrderNumber());
        UserDto user4 = getResult.getUser();
        assertEquals("42", user4.getUserId());
        assertEquals("6625550144", getResult.getPhoneNumber());
        assertEquals("Doe", getResult.getLastName());
        assertEquals("Doe", user4.getLastName());
        assertEquals("Jane", getResult.getFirstName());
        assertEquals("Jane", user4.getFirstName());
        assertEquals("Just cause", getResult.getReason());
        assertEquals("MD", getResult.getState());
        assertEquals("Order Name", getResult.getOrderName());
        assertEquals("Order Status", getResult.getOrderStatus());
        assertEquals("Oxford", getResult.getCity());
        assertEquals("Payment Status", getResult.getPaymentStatus());
        assertEquals("Pin Code", getResult.getPinCode());
        assertEquals("Role", user4.getRole());
        assertEquals("ok", user4.getPassword());
        assertEquals("jane.doe@example.org", user4.getEmail());
        assertNull(user4.getAddress());
        assertEquals(10.0d, getResult.getOrderAmount());
        List<OrderItemDto> orderItems2 = getResult.getOrderItems();
        assertTrue(orderItems2.isEmpty());
        assertSame(orderItems, orderItems2);
        assertSame(getResult, actualFetchAllOrdersResult.get(1));
        assertSame(deliveredDate, deliveredDate2);
    }

   
    @Test
    void testUpdateOrderStatus() {
        doNothing().when(orderRepository)
                .updateOrderStatusByOrderId(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                        Mockito.<LocalDate>any(), Mockito.<String>any());
        ChangeOrderStatusDto changeOrderStatusDto = new ChangeOrderStatusDto("42", "Order Status", "Just cause",
                "2020-03-01", "42");

        orderServiceImpl.updateOrderStatus(changeOrderStatusDto);
        verify(orderRepository).updateOrderStatusByOrderId(eq("Order Status"), eq("42"), eq("Just cause"),
                isA(LocalDate.class), eq("NOT PAID"));
    }

    
    @Test
    void testUpdateOrderStatus2() {
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
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);
        doNothing().when(orderRepository)
                .updateOrderStatusByOrderId(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                        Mockito.<LocalDate>any(), Mockito.<String>any());
        ChangeOrderStatusDto changeOrderStatusDto = new ChangeOrderStatusDto("42", "fulfill", "Just cause", "2020-03-01",
                "42");

        orderServiceImpl.updateOrderStatus(changeOrderStatusDto);
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(orderRepository).updateOrderStatusByOrderId(eq("fulfill"), eq("42"), eq("Just cause"), isA(LocalDate.class),
                eq("COD"));
    }

    
    @Test
    void testUpdateOrderStatus3() {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");
        Order order = mock(Order.class);
        when(order.getOrderItems()).thenReturn(new ArrayList<>());
        doNothing().when(order).setCity(Mockito.<String>any());
        doNothing().when(order).setCreatedAt(Mockito.<Date>any());
        doNothing().when(order).setDeliveredDate(Mockito.<LocalDate>any());
        doNothing().when(order).setFirstName(Mockito.<String>any());
        doNothing().when(order).setLastName(Mockito.<String>any());
        doNothing().when(order).setOrderAmount(anyDouble());
        doNothing().when(order).setOrderId(Mockito.<String>any());
        doNothing().when(order).setOrderItems(Mockito.<List<OrderItem>>any());
        doNothing().when(order).setOrderName(Mockito.<String>any());
        doNothing().when(order).setOrderNumber(Mockito.<String>any());
        doNothing().when(order).setOrderStatus(Mockito.<String>any());
        doNothing().when(order).setPaymentStatus(Mockito.<String>any());
        doNothing().when(order).setPhoneNumber(Mockito.<String>any());
        doNothing().when(order).setPinCode(Mockito.<String>any());
        doNothing().when(order).setReason(Mockito.<String>any());
        doNothing().when(order).setShippingAddress(Mockito.<String>any());
        doNothing().when(order).setState(Mockito.<String>any());
        doNothing().when(order).setUser(Mockito.<User>any());
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
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);
        doNothing().when(orderRepository)
                .updateOrderStatusByOrderId(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                        Mockito.<LocalDate>any(), Mockito.<String>any());
        ChangeOrderStatusDto changeOrderStatusDto = new ChangeOrderStatusDto("42", "fulfill", "Just cause", "2020-03-01",
                "42");

        orderServiceImpl.updateOrderStatus(changeOrderStatusDto);
        verify(order).getOrderItems();
        verify(order).setCity(eq("Oxford"));
        verify(order).setCreatedAt(isA(Date.class));
        verify(order).setDeliveredDate(isA(LocalDate.class));
        verify(order).setFirstName(eq("Jane"));
        verify(order).setLastName(eq("Doe"));
        verify(order).setOrderAmount(eq(10.0d));
        verify(order).setOrderId(eq("42"));
        verify(order).setOrderItems(isA(List.class));
        verify(order).setOrderName(eq("Order Name"));
        verify(order).setOrderNumber(eq("42"));
        verify(order).setOrderStatus(eq("Order Status"));
        verify(order).setPaymentStatus(eq("Payment Status"));
        verify(order).setPhoneNumber(eq("6625550144"));
        verify(order).setPinCode(eq("Pin Code"));
        verify(order).setReason(eq("Just cause"));
        verify(order).setShippingAddress(eq("42 Main St"));
        verify(order).setState(eq("MD"));
        verify(order).setUser(isA(User.class));
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(orderRepository).updateOrderStatusByOrderId(eq("fulfill"), eq("42"), eq("Just cause"), isA(LocalDate.class),
                eq("COD"));
    }

   
    @Test
    void testUpdateOrderStatus4() {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("NOT PAID");
        user2.setUserId("42");

        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setFirstName("Jane");
        order.setLastName("Doe");
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("NOT PAID");
        order.setOrderNumber("42");
        order.setOrderStatus("NOT PAID");
        order.setPaymentStatus("NOT PAID");
        order.setPhoneNumber("6625550144");
        order.setPinCode("NOT PAID");
        order.setReason("Just cause");
        order.setShippingAddress("42 Main St");
        order.setState("MD");
        order.setUser(user2);

        Product product = new Product();
        product.setBrand("NOT PAID");
        product.setCategory("NOT PAID");
        product.setDiscountedPrice(10.0d);
        product.setImage("NOT PAID");
        product.setProductId("42");
        product.setProductName("NOT PAID");
        product.setShortDescription("NOT PAID");
        product.setStock(1);
        product.setUnitPrice(10.0d);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setOrderItemId(1);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(10.0d);

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        Order order2 = mock(Order.class);
        when(order2.getOrderItems()).thenReturn(orderItemList);
        doNothing().when(order2).setCity(Mockito.<String>any());
        doNothing().when(order2).setCreatedAt(Mockito.<Date>any());
        doNothing().when(order2).setDeliveredDate(Mockito.<LocalDate>any());
        doNothing().when(order2).setFirstName(Mockito.<String>any());
        doNothing().when(order2).setLastName(Mockito.<String>any());
        doNothing().when(order2).setOrderAmount(anyDouble());
        doNothing().when(order2).setOrderId(Mockito.<String>any());
        doNothing().when(order2).setOrderItems(Mockito.<List<OrderItem>>any());
        doNothing().when(order2).setOrderName(Mockito.<String>any());
        doNothing().when(order2).setOrderNumber(Mockito.<String>any());
        doNothing().when(order2).setOrderStatus(Mockito.<String>any());
        doNothing().when(order2).setPaymentStatus(Mockito.<String>any());
        doNothing().when(order2).setPhoneNumber(Mockito.<String>any());
        doNothing().when(order2).setPinCode(Mockito.<String>any());
        doNothing().when(order2).setReason(Mockito.<String>any());
        doNothing().when(order2).setShippingAddress(Mockito.<String>any());
        doNothing().when(order2).setState(Mockito.<String>any());
        doNothing().when(order2).setUser(Mockito.<User>any());
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
        order2.setUser(user);
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order2);
        doNothing().when(orderRepository)
                .updateOrderStatusByOrderId(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                        Mockito.<LocalDate>any(), Mockito.<String>any());
        doNothing().when(productRepository).updateStock(Mockito.<String>any(), anyInt());
        ChangeOrderStatusDto changeOrderStatusDto = new ChangeOrderStatusDto("42", "fulfill", "Just cause", "2020-03-01",
                "42");

        orderServiceImpl.updateOrderStatus(changeOrderStatusDto);
        verify(order2).getOrderItems();
        verify(order2).setCity(eq("Oxford"));
        verify(order2).setCreatedAt(isA(Date.class));
        verify(order2).setDeliveredDate(isA(LocalDate.class));
        verify(order2).setFirstName(eq("Jane"));
        verify(order2).setLastName(eq("Doe"));
        verify(order2).setOrderAmount(eq(10.0d));
        verify(order2).setOrderId(eq("42"));
        verify(order2).setOrderItems(isA(List.class));
        verify(order2).setOrderName(eq("Order Name"));
        verify(order2).setOrderNumber(eq("42"));
        verify(order2).setOrderStatus(eq("Order Status"));
        verify(order2).setPaymentStatus(eq("Payment Status"));
        verify(order2).setPhoneNumber(eq("6625550144"));
        verify(order2).setPinCode(eq("Pin Code"));
        verify(order2).setReason(eq("Just cause"));
        verify(order2).setShippingAddress(eq("42 Main St"));
        verify(order2).setState(eq("MD"));
        verify(order2).setUser(isA(User.class));
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(orderRepository).updateOrderStatusByOrderId(eq("fulfill"), eq("42"), eq("Just cause"), isA(LocalDate.class),
                eq("COD"));
        verify(productRepository).updateStock(eq("42"), eq(0));
    }

   
    @Test
    void testUpdateOrderStatus5() {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("NOT PAID");
        user2.setUserId("42");

        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setFirstName("Jane");
        order.setLastName("Doe");
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("NOT PAID");
        order.setOrderNumber("42");
        order.setOrderStatus("NOT PAID");
        order.setPaymentStatus("NOT PAID");
        order.setPhoneNumber("6625550144");
        order.setPinCode("NOT PAID");
        order.setReason("Just cause");
        order.setShippingAddress("42 Main St");
        order.setState("MD");
        order.setUser(user2);

        Product product = new Product();
        product.setBrand("NOT PAID");
        product.setCategory("NOT PAID");
        product.setDiscountedPrice(10.0d);
        product.setImage("NOT PAID");
        product.setProductId("42");
        product.setProductName("NOT PAID");
        product.setShortDescription("NOT PAID");
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
        OrderItem orderItem = mock(OrderItem.class);
        when(orderItem.getQuantity()).thenReturn(1);
        when(orderItem.getProduct()).thenReturn(product2);
        doNothing().when(orderItem).setOrder(Mockito.<Order>any());
        doNothing().when(orderItem).setOrderItemId(anyInt());
        doNothing().when(orderItem).setProduct(Mockito.<Product>any());
        doNothing().when(orderItem).setQuantity(anyInt());
        doNothing().when(orderItem).setTotalPrice(anyDouble());
        orderItem.setOrder(order);
        orderItem.setOrderItemId(1);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(10.0d);

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        Order order2 = mock(Order.class);
        when(order2.getOrderItems()).thenReturn(orderItemList);
        doNothing().when(order2).setCity(Mockito.<String>any());
        doNothing().when(order2).setCreatedAt(Mockito.<Date>any());
        doNothing().when(order2).setDeliveredDate(Mockito.<LocalDate>any());
        doNothing().when(order2).setFirstName(Mockito.<String>any());
        doNothing().when(order2).setLastName(Mockito.<String>any());
        doNothing().when(order2).setOrderAmount(anyDouble());
        doNothing().when(order2).setOrderId(Mockito.<String>any());
        doNothing().when(order2).setOrderItems(Mockito.<List<OrderItem>>any());
        doNothing().when(order2).setOrderName(Mockito.<String>any());
        doNothing().when(order2).setOrderNumber(Mockito.<String>any());
        doNothing().when(order2).setOrderStatus(Mockito.<String>any());
        doNothing().when(order2).setPaymentStatus(Mockito.<String>any());
        doNothing().when(order2).setPhoneNumber(Mockito.<String>any());
        doNothing().when(order2).setPinCode(Mockito.<String>any());
        doNothing().when(order2).setReason(Mockito.<String>any());
        doNothing().when(order2).setShippingAddress(Mockito.<String>any());
        doNothing().when(order2).setState(Mockito.<String>any());
        doNothing().when(order2).setUser(Mockito.<User>any());
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
        order2.setUser(user);
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order2);
        doNothing().when(orderRepository)
                .updateOrderStatusByOrderId(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                        Mockito.<LocalDate>any(), Mockito.<String>any());
        doNothing().when(productRepository).updateStock(Mockito.<String>any(), anyInt());
        ChangeOrderStatusDto changeOrderStatusDto = new ChangeOrderStatusDto("42", "fulfill", "Just cause", "2020-03-01",
                "42");

        orderServiceImpl.updateOrderStatus(changeOrderStatusDto);
        verify(order2).getOrderItems();
        verify(order2).setCity(eq("Oxford"));
        verify(order2).setCreatedAt(isA(Date.class));
        verify(order2).setDeliveredDate(isA(LocalDate.class));
        verify(order2).setFirstName(eq("Jane"));
        verify(order2).setLastName(eq("Doe"));
        verify(order2).setOrderAmount(eq(10.0d));
        verify(order2).setOrderId(eq("42"));
        verify(order2).setOrderItems(isA(List.class));
        verify(order2).setOrderName(eq("Order Name"));
        verify(order2).setOrderNumber(eq("42"));
        verify(order2).setOrderStatus(eq("Order Status"));
        verify(order2).setPaymentStatus(eq("Payment Status"));
        verify(order2).setPhoneNumber(eq("6625550144"));
        verify(order2).setPinCode(eq("Pin Code"));
        verify(order2).setReason(eq("Just cause"));
        verify(order2).setShippingAddress(eq("42 Main St"));
        verify(order2).setState(eq("MD"));
        verify(order2).setUser(isA(User.class));
        verify(orderItem, atLeast(1)).getProduct();
        verify(orderItem).getQuantity();
        verify(orderItem).setOrder(isA(Order.class));
        verify(orderItem).setOrderItemId(eq(1));
        verify(orderItem).setProduct(isA(Product.class));
        verify(orderItem).setQuantity(eq(1));
        verify(orderItem).setTotalPrice(eq(10.0d));
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(orderRepository).updateOrderStatusByOrderId(eq("fulfill"), eq("42"), eq("Just cause"), isA(LocalDate.class),
                eq("COD"));
        verify(productRepository).updateStock(eq("42"), eq(0));
    }

   
    @Test
    void testUpdateOrderStatus6() {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("NOT PAID");
        user2.setUserId("42");

        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setFirstName("Jane");
        order.setLastName("Doe");
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("NOT PAID");
        order.setOrderNumber("42");
        order.setOrderStatus("NOT PAID");
        order.setPaymentStatus("NOT PAID");
        order.setPhoneNumber("6625550144");
        order.setPinCode("NOT PAID");
        order.setReason("Just cause");
        order.setShippingAddress("42 Main St");
        order.setState("MD");
        order.setUser(user2);

        Product product = new Product();
        product.setBrand("NOT PAID");
        product.setCategory("NOT PAID");
        product.setDiscountedPrice(10.0d);
        product.setImage("NOT PAID");
        product.setProductId("42");
        product.setProductName("NOT PAID");
        product.setShortDescription("NOT PAID");
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
        OrderItem orderItem = mock(OrderItem.class);
        when(orderItem.getQuantity()).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(orderItem.getProduct()).thenReturn(product2);
        doNothing().when(orderItem).setOrder(Mockito.<Order>any());
        doNothing().when(orderItem).setOrderItemId(anyInt());
        doNothing().when(orderItem).setProduct(Mockito.<Product>any());
        doNothing().when(orderItem).setQuantity(anyInt());
        doNothing().when(orderItem).setTotalPrice(anyDouble());
        orderItem.setOrder(order);
        orderItem.setOrderItemId(1);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(10.0d);

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        Order order2 = mock(Order.class);
        when(order2.getOrderItems()).thenReturn(orderItemList);
        doNothing().when(order2).setCity(Mockito.<String>any());
        doNothing().when(order2).setCreatedAt(Mockito.<Date>any());
        doNothing().when(order2).setDeliveredDate(Mockito.<LocalDate>any());
        doNothing().when(order2).setFirstName(Mockito.<String>any());
        doNothing().when(order2).setLastName(Mockito.<String>any());
        doNothing().when(order2).setOrderAmount(anyDouble());
        doNothing().when(order2).setOrderId(Mockito.<String>any());
        doNothing().when(order2).setOrderItems(Mockito.<List<OrderItem>>any());
        doNothing().when(order2).setOrderName(Mockito.<String>any());
        doNothing().when(order2).setOrderNumber(Mockito.<String>any());
        doNothing().when(order2).setOrderStatus(Mockito.<String>any());
        doNothing().when(order2).setPaymentStatus(Mockito.<String>any());
        doNothing().when(order2).setPhoneNumber(Mockito.<String>any());
        doNothing().when(order2).setPinCode(Mockito.<String>any());
        doNothing().when(order2).setReason(Mockito.<String>any());
        doNothing().when(order2).setShippingAddress(Mockito.<String>any());
        doNothing().when(order2).setState(Mockito.<String>any());
        doNothing().when(order2).setUser(Mockito.<User>any());
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
        order2.setUser(user);
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order2);
        ChangeOrderStatusDto changeOrderStatusDto = new ChangeOrderStatusDto("42", "fulfill", "Just cause", "2020-03-01",
                "42");

        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.updateOrderStatus(changeOrderStatusDto));
        verify(order2).getOrderItems();
        verify(order2).setCity(eq("Oxford"));
        verify(order2).setCreatedAt(isA(Date.class));
        verify(order2).setDeliveredDate(isA(LocalDate.class));
        verify(order2).setFirstName(eq("Jane"));
        verify(order2).setLastName(eq("Doe"));
        verify(order2).setOrderAmount(eq(10.0d));
        verify(order2).setOrderId(eq("42"));
        verify(order2).setOrderItems(isA(List.class));
        verify(order2).setOrderName(eq("Order Name"));
        verify(order2).setOrderNumber(eq("42"));
        verify(order2).setOrderStatus(eq("Order Status"));
        verify(order2).setPaymentStatus(eq("Payment Status"));
        verify(order2).setPhoneNumber(eq("6625550144"));
        verify(order2).setPinCode(eq("Pin Code"));
        verify(order2).setReason(eq("Just cause"));
        verify(order2).setShippingAddress(eq("42 Main St"));
        verify(order2).setState(eq("MD"));
        verify(order2).setUser(isA(User.class));
        verify(orderItem).getProduct();
        verify(orderItem).getQuantity();
        verify(orderItem).setOrder(isA(Order.class));
        verify(orderItem).setOrderItemId(eq(1));
        verify(orderItem).setProduct(isA(Product.class));
        verify(orderItem).setQuantity(eq(1));
        verify(orderItem).setTotalPrice(eq(10.0d));
        verify(orderRepository).findOrderByOrderId(eq("42"));
    }

  
    @Test
    void testUpdateOrderStatus7() {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("NOT PAID");
        user2.setUserId("42");

        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setFirstName("Jane");
        order.setLastName("Doe");
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("NOT PAID");
        order.setOrderNumber("42");
        order.setOrderStatus("NOT PAID");
        order.setPaymentStatus("NOT PAID");
        order.setPhoneNumber("6625550144");
        order.setPinCode("NOT PAID");
        order.setReason("Just cause");
        order.setShippingAddress("42 Main St");
        order.setState("MD");
        order.setUser(user2);

        Product product = new Product();
        product.setBrand("NOT PAID");
        product.setCategory("NOT PAID");
        product.setDiscountedPrice(10.0d);
        product.setImage("NOT PAID");
        product.setProductId("42");
        product.setProductName("NOT PAID");
        product.setShortDescription("NOT PAID");
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
        OrderItem orderItem = mock(OrderItem.class);
        when(orderItem.getQuantity()).thenReturn(0);
        when(orderItem.getProduct()).thenReturn(product2);
        doNothing().when(orderItem).setOrder(Mockito.<Order>any());
        doNothing().when(orderItem).setOrderItemId(anyInt());
        doNothing().when(orderItem).setProduct(Mockito.<Product>any());
        doNothing().when(orderItem).setQuantity(anyInt());
        doNothing().when(orderItem).setTotalPrice(anyDouble());
        orderItem.setOrder(order);
        orderItem.setOrderItemId(1);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(10.0d);

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        Order order2 = mock(Order.class);
        when(order2.getOrderItems()).thenReturn(orderItemList);
        doNothing().when(order2).setCity(Mockito.<String>any());
        doNothing().when(order2).setCreatedAt(Mockito.<Date>any());
        doNothing().when(order2).setDeliveredDate(Mockito.<LocalDate>any());
        doNothing().when(order2).setFirstName(Mockito.<String>any());
        doNothing().when(order2).setLastName(Mockito.<String>any());
        doNothing().when(order2).setOrderAmount(anyDouble());
        doNothing().when(order2).setOrderId(Mockito.<String>any());
        doNothing().when(order2).setOrderItems(Mockito.<List<OrderItem>>any());
        doNothing().when(order2).setOrderName(Mockito.<String>any());
        doNothing().when(order2).setOrderNumber(Mockito.<String>any());
        doNothing().when(order2).setOrderStatus(Mockito.<String>any());
        doNothing().when(order2).setPaymentStatus(Mockito.<String>any());
        doNothing().when(order2).setPhoneNumber(Mockito.<String>any());
        doNothing().when(order2).setPinCode(Mockito.<String>any());
        doNothing().when(order2).setReason(Mockito.<String>any());
        doNothing().when(order2).setShippingAddress(Mockito.<String>any());
        doNothing().when(order2).setState(Mockito.<String>any());
        doNothing().when(order2).setUser(Mockito.<User>any());
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
        order2.setUser(user);
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order2);
        doNothing().when(orderRepository)
                .updateOrderStatusByOrderId(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                        Mockito.<LocalDate>any(), Mockito.<String>any());
        doNothing().when(productRepository).updateStock(Mockito.<String>any(), anyInt());
        ChangeOrderStatusDto changeOrderStatusDto = new ChangeOrderStatusDto("42", "fulfill", "Just cause", "2020-03-01",
                "42");

        orderServiceImpl.updateOrderStatus(changeOrderStatusDto);
        verify(order2).getOrderItems();
        verify(order2).setCity(eq("Oxford"));
        verify(order2).setCreatedAt(isA(Date.class));
        verify(order2).setDeliveredDate(isA(LocalDate.class));
        verify(order2).setFirstName(eq("Jane"));
        verify(order2).setLastName(eq("Doe"));
        verify(order2).setOrderAmount(eq(10.0d));
        verify(order2).setOrderId(eq("42"));
        verify(order2).setOrderItems(isA(List.class));
        verify(order2).setOrderName(eq("Order Name"));
        verify(order2).setOrderNumber(eq("42"));
        verify(order2).setOrderStatus(eq("Order Status"));
        verify(order2).setPaymentStatus(eq("Payment Status"));
        verify(order2).setPhoneNumber(eq("6625550144"));
        verify(order2).setPinCode(eq("Pin Code"));
        verify(order2).setReason(eq("Just cause"));
        verify(order2).setShippingAddress(eq("42 Main St"));
        verify(order2).setState(eq("MD"));
        verify(order2).setUser(isA(User.class));
        verify(orderItem, atLeast(1)).getProduct();
        verify(orderItem).getQuantity();
        verify(orderItem).setOrder(isA(Order.class));
        verify(orderItem).setOrderItemId(eq(1));
        verify(orderItem).setProduct(isA(Product.class));
        verify(orderItem).setQuantity(eq(1));
        verify(orderItem).setTotalPrice(eq(10.0d));
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(orderRepository).updateOrderStatusByOrderId(eq("fulfill"), eq("42"), eq("Just cause"), isA(LocalDate.class),
                eq("COD"));
        verify(productRepository).updateStock(eq("42"), eq(1));
    }

   
    @Test
    void testGetOrderItemById() {
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
        when(orderItemRepository.findById(anyInt())).thenReturn(orderItem);
        int orderItemId = 1;
        OrderItem actualOrderItemById = orderServiceImpl.getOrderItemById(orderItemId);
        verify(orderItemRepository).findById(eq(1));
        assertSame(orderItem, actualOrderItemById);
    }

 
    @Test
    void testGetOrderItemById2() {
        when(orderItemRepository.findById(anyInt())).thenThrow(new ResourceNotFoundException("An error occurred"));
        int orderItemId = 1;
        assertThrows(ResourceNotFoundException.class, () -> orderServiceImpl.getOrderItemById(orderItemId));
        verify(orderItemRepository).findById(eq(1));
    }
}
