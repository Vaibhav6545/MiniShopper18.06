
package com.example.demo.Service.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.demo.Entity.Cart;
import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderItem;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dtos.CreateOrderRequest;
import com.example.demo.dtos.OrderDto;
import com.example.demo.dtos.UserDto;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private OrderRepository orderRepository;
    @Autowired
    private OrderServiceImpl orderServiceImpl;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private UserRepository userRepository;
    /**
     * Method under test: {@link OrderServiceImpl#createOrder(CreateOrderRequest)}
     */
    @Test
    void testCreateOrder() {
        // Arrange
        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("ok");
        user.setPinCode("Pin Code");
        user.setState("MD");
        user.setStreet("Street");
        user.setUserId("42");
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setUser(user);
        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("ok");
        user2.setPinCode("Pin Code");
        user2.setState("MD");
        user2.setStreet("Street");
        user2.setUserId("42");
        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart2);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
        UserDto user3 = UserDto.builder()
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
        OrderDto buildResult = stateResult.user(user3).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);
        User user4 = new User();
        user4.setAddress("42 Main St");
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setLastName("Doe");
        user4.setPassword("ok");
        user4.setPinCode("Pin Code");
        user4.setState("MD");
        user4.setStreet("Street");
        user4.setUserId("42");
        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("Order Name");
        order.setOrderNumber("42");
        order.setOrderStatus("Order Status");
        order.setPaymentStatus("Payment Status");
        order.setPostalCode("Postal Code");
        order.setShippingAddress("42 Main St");
        order.setShippingPhone("6625550144");
        order.setState("MD");
        order.setUser(user4);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);
        User user5 = new User();
        user5.setAddress("42 Main St");
        user5.setCity("Oxford");
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setLastName("Doe");
        user5.setPassword("ok");
        user5.setPinCode("Pin Code");
        user5.setState("MD");
        user5.setStreet("Street");
        user5.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user5);
        // Act
        orderServiceImpl.createOrder(new CreateOrderRequest("42", "42", "Order Status", "Payment Status", "Order Name",
                "42 Main St", "Postal Code", "Oxford", "MD", "6625550144"));
        // Assert
        verify(cartRepository).findByCartId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(cartRepository).save(isA(Cart.class));
        verify(orderRepository).save(isA(Order.class));
    }
    /**
     * Method under test: {@link OrderServiceImpl#createOrder(CreateOrderRequest)}
     */
    @Test
    void testCreateOrder2() {
        // Arrange
        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("ok");
        user.setPinCode("Pin Code");
        user.setState("MD");
        user.setStreet("Street");
        user.setUserId("42");
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setUser(user);
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
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
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("ok");
        user2.setPinCode("Pin Code");
        user2.setState("MD");
        user2.setStreet("Street");
        user2.setUserId("42");
        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(items);
        cart2.setUser(user2);
        User user3 = new User();
        user3.setAddress("42 Main St");
        user3.setCity("Oxford");
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setPassword("ok");
        user3.setPinCode("Pin Code");
        user3.setState("MD");
        user3.setStreet("Street");
        user3.setUserId("42");
        Cart cart3 = new Cart();
        cart3.setCartId("42");
        cart3.setItems(new ArrayList<>());
        cart3.setUser(user3);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart3);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart2);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
        UserDto user4 = UserDto.builder()
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
        OrderDto buildResult = stateResult.user(user4).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);
        User user5 = new User();
        user5.setAddress("42 Main St");
        user5.setCity("Oxford");
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setLastName("Doe");
        user5.setPassword("ok");
        user5.setPinCode("Pin Code");
        user5.setState("MD");
        user5.setStreet("Street");
        user5.setUserId("42");
        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("Order Name");
        order.setOrderNumber("42");
        order.setOrderStatus("Order Status");
        order.setPaymentStatus("Payment Status");
        order.setPostalCode("Postal Code");
        order.setShippingAddress("42 Main St");
        order.setShippingPhone("6625550144");
        order.setState("MD");
        order.setUser(user5);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);
        Product product2 = new Product();
        product2.setBrand("Brand");
        product2.setCategory("Category");
        product2.setDiscountedPrice(10.0d);
        product2.setProductId("42");
        product2.setProductName("Product Name");
        product2.setShortDescription("Short Description");
        product2.setStock(1);
        product2.setUnitPrice(10.0d);
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product2);
        User user6 = new User();
        user6.setAddress("42 Main St");
        user6.setCity("Oxford");
        user6.setEmail("jane.doe@example.org");
        user6.setFirstName("Jane");
        user6.setLastName("Doe");
        user6.setPassword("ok");
        user6.setPinCode("Pin Code");
        user6.setState("MD");
        user6.setStreet("Street");
        user6.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user6);
        // Act
        orderServiceImpl.createOrder(new CreateOrderRequest("42", "42", "Order Status", "Payment Status", "Order Name",
                "42 Main St", "Postal Code", "Oxford", "MD", "6625550144"));
        // Assert
        verify(cartRepository).findByCartId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(cartRepository).save(isA(Cart.class));
        verify(orderRepository).save(isA(Order.class));
        verify(productRepository).save(isA(Product.class));
    }
    /**
     * Method under test: {@link OrderServiceImpl#createOrder(CreateOrderRequest)}
     */
    @Test
    void testCreateOrder3() {
        // Arrange
        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("ok");
        user.setPinCode("Pin Code");
        user.setState("MD");
        user.setStreet("Street");
        user.setUserId("42");
        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setUser(user);
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
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
        user2.setAddress("17 High St");
        user2.setCity("London");
        user2.setEmail("john.smith@example.org");
        user2.setFirstName("John");
        user2.setLastName("Smith");
        user2.setPassword("Password");
        user2.setPinCode("42");
        user2.setState("State");
        user2.setStreet("42");
        user2.setUserId("User Id");
        Cart cart2 = new Cart();
        cart2.setCartId("Cart Id");
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);
        Product product2 = new Product();
        product2.setBrand("42");
        product2.setCategory("42");
        product2.setDiscountedPrice(0.0d);
        product2.setProductId("Product Id");
        product2.setProductName("42");
        product2.setShortDescription("42");
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
        user3.setAddress("42 Main St");
        user3.setCity("Oxford");
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setPassword("ok");
        user3.setPinCode("Pin Code");
        user3.setState("MD");
        user3.setStreet("Street");
        user3.setUserId("42");
        Cart cart3 = new Cart();
        cart3.setCartId("42");
        cart3.setItems(items);
        cart3.setUser(user3);
        User user4 = new User();
        user4.setAddress("42 Main St");
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setLastName("Doe");
        user4.setPassword("ok");
        user4.setPinCode("Pin Code");
        user4.setState("MD");
        user4.setStreet("Street");
        user4.setUserId("42");
        Cart cart4 = new Cart();
        cart4.setCartId("42");
        cart4.setItems(new ArrayList<>());
        cart4.setUser(user4);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart4);
        when(cartRepository.findByCartId(Mockito.<String>any())).thenReturn(cart3);
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
        UserDto user5 = UserDto.builder()
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
        OrderDto buildResult = stateResult.user(user5).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);
        User user6 = new User();
        user6.setAddress("42 Main St");
        user6.setCity("Oxford");
        user6.setEmail("jane.doe@example.org");
        user6.setFirstName("Jane");
        user6.setLastName("Doe");
        user6.setPassword("ok");
        user6.setPinCode("Pin Code");
        user6.setState("MD");
        user6.setStreet("Street");
        user6.setUserId("42");
        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("Order Name");
        order.setOrderNumber("42");
        order.setOrderStatus("Order Status");
        order.setPaymentStatus("Payment Status");
        order.setPostalCode("Postal Code");
        order.setShippingAddress("42 Main St");
        order.setShippingPhone("6625550144");
        order.setState("MD");
        order.setUser(user6);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);
        Product product3 = new Product();
        product3.setBrand("Brand");
        product3.setCategory("Category");
        product3.setDiscountedPrice(10.0d);
        product3.setProductId("42");
        product3.setProductName("Product Name");
        product3.setShortDescription("Short Description");
        product3.setStock(1);
        product3.setUnitPrice(10.0d);
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product3);
        User user7 = new User();
        user7.setAddress("42 Main St");
        user7.setCity("Oxford");
        user7.setEmail("jane.doe@example.org");
        user7.setFirstName("Jane");
        user7.setLastName("Doe");
        user7.setPassword("ok");
        user7.setPinCode("Pin Code");
        user7.setState("MD");
        user7.setStreet("Street");
        user7.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user7);
        // Act
        orderServiceImpl.createOrder(new CreateOrderRequest("42", "42", "Order Status", "Payment Status", "Order Name",
                "42 Main St", "Postal Code", "Oxford", "MD", "6625550144"));
        // Assert
        verify(cartRepository).findByCartId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(cartRepository).save(isA(Cart.class));
        verify(orderRepository).save(isA(Order.class));
        verify(productRepository, atLeast(1)).save(Mockito.<Product>any());
    }
    /**
     * Method under test: {@link OrderServiceImpl#fetchOrderByUser(String)}
     */
    @Test
    void testFetchOrderByUser() {
        // Arrange
        when(orderRepository.findByUser(Mockito.<User>any())).thenReturn(new ArrayList<>());
        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("ok");
        user.setPinCode("Pin Code");
        user.setState("MD");
        user.setStreet("Street");
        user.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user);
        // Act
        List<OrderDto> actualFetchOrderByUserResult = orderServiceImpl.fetchOrderByUser("42");
        // Assert
        verify(orderRepository).findByUser(isA(User.class));
        verify(userRepository).findByUserId(eq("42"));
        assertTrue(actualFetchOrderByUserResult.isEmpty());
    }
    /**
     * Method under test: {@link OrderServiceImpl#fetchOrderByUser(String)}
     */
    @Test
    void testFetchOrderByUser2() {
        // Arrange
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);
        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("ok");
        user2.setPinCode("Pin Code");
        user2.setState("MD");
        user2.setStreet("Street");
        user2.setUserId("42");
        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("Order Name");
        order.setOrderNumber("42");
        order.setOrderStatus("Order Status");
        order.setPaymentStatus("Payment Status");
        order.setPostalCode("Postal Code");
        order.setShippingAddress("42 Main St");
        order.setShippingPhone("6625550144");
        order.setState("MD");
        order.setUser(user2);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findByUser(Mockito.<User>any())).thenReturn(orderList);
        User user3 = new User();
        user3.setAddress("42 Main St");
        user3.setCity("Oxford");
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setLastName("Doe");
        user3.setPassword("ok");
        user3.setPinCode("Pin Code");
        user3.setState("MD");
        user3.setStreet("Street");
        user3.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user3);
        // Act
        List<OrderDto> actualFetchOrderByUserResult = orderServiceImpl.fetchOrderByUser("42");
        // Assert
        verify(orderRepository).findByUser(isA(User.class));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        assertEquals(1, actualFetchOrderByUserResult.size());
    }
    /**
     * Method under test: {@link OrderServiceImpl#fetchOrderByUser(String)}
     */
    @Test
    void testFetchOrderByUser3() {
        // Arrange
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);
        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("ok");
        user2.setPinCode("Pin Code");
        user2.setState("MD");
        user2.setStreet("Street");
        user2.setUserId("42");
        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("Order Name");
        order.setOrderNumber("42");
        order.setOrderStatus("Order Status");
        order.setPaymentStatus("Payment Status");
        order.setPostalCode("Postal Code");
        order.setShippingAddress("42 Main St");
        order.setShippingPhone("6625550144");
        order.setState("MD");
        order.setUser(user2);
        User user3 = new User();
        user3.setAddress("17 High St");
        user3.setCity("London");
        user3.setEmail("john.smith@example.org");
        user3.setFirstName("John");
        user3.setLastName("Smith");
        user3.setPassword("Password");
        user3.setPinCode("42");
        user3.setState("State");
        user3.setStreet("42");
        user3.setUserId("User Id");
        Order order2 = new Order();
        order2.setCity("London");
        order2.setCreatedAt(mock(Date.class));
        order2.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order2.setOrderAmount(0.5d);
        order2.setOrderId("Order Id");
        order2.setOrderItems(new ArrayList<>());
        order2.setOrderName("42");
        order2.setOrderNumber("Order Number");
        order2.setOrderStatus("42");
        order2.setPaymentStatus("42");
        order2.setPostalCode("42");
        order2.setShippingAddress("17 High St");
        order2.setShippingPhone("8605550118");
        order2.setState("State");
        order2.setUser(user3);
        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order2);
        orderList.add(order);
        when(orderRepository.findByUser(Mockito.<User>any())).thenReturn(orderList);
        User user4 = new User();
        user4.setAddress("42 Main St");
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setLastName("Doe");
        user4.setPassword("ok");
        user4.setPinCode("Pin Code");
        user4.setState("MD");
        user4.setStreet("Street");
        user4.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user4);
        // Act
        List<OrderDto> actualFetchOrderByUserResult = orderServiceImpl.fetchOrderByUser("42");
        // Assert
        verify(orderRepository).findByUser(isA(User.class));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), isA(Class.class));
        assertEquals(2, actualFetchOrderByUserResult.size());
    }
    /**
     * Method under test: {@link OrderServiceImpl#fetchOrderByOrderId(String)}
     */
    @Test
    void testFetchOrderByOrderId() {
        // Arrange
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);
        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("ok");
        user2.setPinCode("Pin Code");
        user2.setState("MD");
        user2.setStreet("Street");
        user2.setUserId("42");
        Order order = new Order();
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("Order Name");
        order.setOrderNumber("42");
        order.setOrderStatus("Order Status");
        order.setPaymentStatus("Payment Status");
        order.setPostalCode("Postal Code");
        order.setShippingAddress("42 Main St");
        order.setShippingPhone("6625550144");
        order.setState("MD");
        order.setUser(user2);
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);
        // Act
        orderServiceImpl.fetchOrderByOrderId("42");
        // Assert
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }
    /**
     * Method under test: {@link OrderServiceImpl#fetchOrderByOrderId(String)}
     */
    @Test
    void testFetchOrderByOrderId2() {
        // Arrange
        OrderDto.OrderDtoBuilder createdAtResult = OrderDto.builder().city("Oxford").createdAt(mock(Date.class));
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);
        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("ok");
        user2.setPinCode("Pin Code");
        user2.setState("MD");
        user2.setStreet("Street");
        user2.setUserId("42");
        Order order = mock(Order.class);
        when(order.getOrderItems()).thenReturn(new ArrayList<>());
        doNothing().when(order).setCity(Mockito.<String>any());
        doNothing().when(order).setCreatedAt(Mockito.<Date>any());
        doNothing().when(order).setDeliveredDate(Mockito.<LocalDate>any());
        doNothing().when(order).setOrderAmount(anyDouble());
        doNothing().when(order).setOrderId(Mockito.<String>any());
        doNothing().when(order).setOrderItems(Mockito.<List<OrderItem>>any());
        doNothing().when(order).setOrderName(Mockito.<String>any());
        doNothing().when(order).setOrderNumber(Mockito.<String>any());
        doNothing().when(order).setOrderStatus(Mockito.<String>any());
        doNothing().when(order).setPaymentStatus(Mockito.<String>any());
        doNothing().when(order).setPostalCode(Mockito.<String>any());
        doNothing().when(order).setShippingAddress(Mockito.<String>any());
        doNothing().when(order).setShippingPhone(Mockito.<String>any());
        doNothing().when(order).setState(Mockito.<String>any());
        doNothing().when(order).setUser(Mockito.<User>any());
        order.setCity("Oxford");
        order.setCreatedAt(mock(Date.class));
        order.setDeliveredDate(LocalDate.of(1970, 1, 1));
        order.setOrderAmount(10.0d);
        order.setOrderId("42");
        order.setOrderItems(new ArrayList<>());
        order.setOrderName("Order Name");
        order.setOrderNumber("42");
        order.setOrderStatus("Order Status");
        order.setPaymentStatus("Payment Status");
        order.setPostalCode("Postal Code");
        order.setShippingAddress("42 Main St");
        order.setShippingPhone("6625550144");
        order.setState("MD");
        order.setUser(user2);
        when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);
        // Act
        orderServiceImpl.fetchOrderByOrderId("42");
        // Assert
        verify(order).getOrderItems();
        verify(order).setCity(eq("Oxford"));
        verify(order).setCreatedAt(isA(Date.class));
        verify(order).setDeliveredDate(isA(LocalDate.class));
        verify(order).setOrderAmount(eq(10.0d));
        verify(order).setOrderId(eq("42"));
        verify(order).setOrderItems(isA(List.class));
        verify(order).setOrderName(eq("Order Name"));
        verify(order).setOrderNumber(eq("42"));
        verify(order).setOrderStatus(eq("Order Status"));
        verify(order).setPaymentStatus(eq("Payment Status"));
        verify(order).setPostalCode(eq("Postal Code"));
        verify(order).setShippingAddress(eq("42 Main St"));
        verify(order).setShippingPhone(eq("6625550144"));
        verify(order).setState(eq("MD"));
        verify(order).setUser(isA(User.class));
        verify(orderRepository).findOrderByOrderId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }
}
 