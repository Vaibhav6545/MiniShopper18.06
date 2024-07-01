package minishopper.Service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import minishopper.Entity.Cart;
import minishopper.Entity.CartItem;
import minishopper.Entity.Order;
import minishopper.Entity.OrderItem;
import minishopper.Entity.Product;
import minishopper.Entity.User;
import minishopper.Repository.CartRepository;
import minishopper.Repository.OrderItemRepository;
import minishopper.Repository.OrderRepository;
import minishopper.Repository.ProductRepository;
import minishopper.Repository.UserRepository;
import minishopper.dtos.CreateOrderRequest;
import minishopper.dtos.OrderDto;
import minishopper.dtos.OrderItemDto;
import minishopper.dtos.UpdateOrderItem;
import minishopper.dtos.UserDto;
import minishopper.exception.ResourceNotFoundException;
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
  void testCreateOrder() {
    // Arrange
    User user = new User();
    user.setAddress("42 Main St");
    user.setCity("nashik");
    user.setEmail("vishalkumar123@gmail.com");
    user.setFirstName("vishal");
    user.setLastName("kumar");
    user.setPassword("ok");
    user.setPinCode("422103");
    user.setState("MD");
    user.setStreet("Street");
    user.setUserId("42");

    Cart cart = new Cart();
    cart.setCartId("42");
    cart.setItems(new ArrayList<>());
    cart.setUser(user);

    when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart);
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
    UserDto user1 = UserDto.builder()
        .city("nashik")
        .email("vishalkumar123@gmail.com")
        .firstName("vishal")
        .image("Image")
        .lastName("Doe")
        .pinCode("422103")
        .state("MD")
        .street("Street")
        .userId("42")
        .build();
    OrderDto buildResult = stateResult.user(user1).build();
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

  }
 
    @Test
  void testCreateOrderSingleProduct() {
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
        .city("nashik")
        .email("vishalkumar123@gmail.com")
        .firstName("vishal")
        .image("Image")
        .lastName("kumar")
        .pinCode("422103")
        .state("MD")
        .street("Street")
        .userId("42")
        .build();
    OrderDto buildResult = stateResult.user(user).build();
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

    User user2 = new User();
    user2.setAddress("42 Main St");
    user2.setCity("nashik");
    user2.setEmail("vishalkumar123@gmail.com");
    user2.setFirstName("vishal");
    user2.setLastName("kumar");
    user2.setPassword("ok");
    user2.setPinCode("422103");
    user2.setState("MD");
    user2.setStreet("Street");
    user2.setUserId("42");

    Order order = new Order();
    order.setCity("nashik");
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

 
    orderServiceImpl.createOrderSingleProduct(new CreateOrderRequest("42", "42", "Order Status", "Payment Status",
        "Order Name", "42 Main St", "Postal Code", "nashik", "MD", "6625550144"));

  
    verify(productRepository).findByProductId(isNull());
    verify(userRepository).findByUserId(eq("42"));
    verify(modelMapper).map(isA(Object.class), isA(Class.class));
    verify(orderRepository).save(isA(Order.class));
  }

  
  
  @Test
  void testCreateOrderByExcelSheet() throws ResourceNotFoundException {
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
        .city("nashik")
        .email("vishalkumar123@gmail.com")
        .firstName("vishal")
        .image("Image")
        .lastName("kumar")
        .pinCode("422103")
        .state("MD")
        .street("Street")
        .userId("42")
        .build();
    OrderDto buildResult = stateResult.user(user).build();
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

    User user2 = new User();
    user2.setAddress("42 Main St");
    user2.setCity("nashik");
    user2.setEmail("vishalkumar123@gmail.com");
    user2.setFirstName("vishal");
    user2.setLastName("kumar");
    user2.setPassword("ok");
    user2.setPinCode("422103");
    user2.setState("MD");
    user2.setStreet("Street");
    user2.setUserId("42");

    Order order = new Order();
    order.setCity("nashik");
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
    when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);

    User user3 = new User();
    user3.setAddress("42 Main St");
    user3.setCity("nashik");
    user3.setEmail("vishalkumar123@gmail.com");
    user3.setFirstName("vishal");
    user3.setLastName("kumar");
    user3.setPassword("ok");
    user3.setPinCode("422103");
    user3.setState("MD");
    user3.setStreet("Street");
    user3.setUserId("42");
    when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user3);

    CreateOrderRequest orderRequest = new CreateOrderRequest("42", "42", "Order Status", "Payment Status", "Order Name",
        "42 Main St", "Postal Code", "nashik", "MD", "6625550144");
    orderRequest.setProducts(new ArrayList<>());

 
    orderServiceImpl.createOrderByExcelSheet(orderRequest);

  
    verify(userRepository).findByUserId(eq("42"));
    verify(modelMapper).map(isA(Object.class), isA(Class.class));
    verify(orderRepository).save(isA(Order.class));
  }

 
 
  @Test
  void testFetchOrderByUser() {
    // Arrange
    when(orderRepository.findByUser(Mockito.<User>any())).thenReturn(new ArrayList<>());

    User user = new User();
    user.setAddress("42 Main St");
    user.setCity("nashik");
    user.setEmail("vishalkumar123@gmail.com");
    user.setFirstName("vishal");
    user.setLastName("kumar");
    user.setPassword("ok");
    user.setPinCode("422103");
    user.setState("MD");
    user.setStreet("Street");
    user.setUserId("42");
    when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user);


    List<OrderDto> actualFetchOrderByUserResult = orderServiceImpl.fetchOrderByUser("42");

  
    verify(orderRepository).findByUser(isA(User.class));
    verify(userRepository).findByUserId(eq("42"));
    assertTrue(actualFetchOrderByUserResult.isEmpty());
  }

  
  
  @Test
  void testFetchOrderByOrderId() {
 
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
        .city("nashik")
        .email("vishalkumar123@gmail.com")
        .firstName("vishal")
        .image("Image")
        .lastName("kumar")
        .pinCode("422103")
        .state("MD")
        .street("Street")
        .userId("42")
        .build();
    OrderDto buildResult = stateResult.user(user).build();
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderDto>>any())).thenReturn(buildResult);

  }
  
  @Test
  void testRemoveOrderItemByOrderItemId() {
    // Arrange
    User user = new User();
    user.setAddress("42 Main St");
    user.setCity("nashik");
    user.setEmail("vishalkumar123@gmail.com");
    user.setFirstName("vishal");
    user.setLastName("kumar");
    user.setPassword("ok");
    user.setPinCode("422103");
    user.setState("MD");
    user.setStreet("Street");
    user.setUserId("42");

    Order order = new Order();
    order.setCity("nashik");
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
    doNothing().when(orderItemRepository).deleteById(Mockito.<Integer>any());


    OrderItem actualRemoveOrderItemByOrderItemIdResult = orderServiceImpl.removeOrderItemByOrderItemId(1);

    
    verify(orderItemRepository).findById(eq(1));
    verify(orderItemRepository).deleteById(eq(1));
    assertSame(orderItem, actualRemoveOrderItemByOrderItemIdResult);
  }

  
 
  @Test
  void testUpdateTotalPrice() {
    // Arrange
    User user = new User();
    user.setAddress("42 Main St");
    user.setCity("nashik");
    user.setEmail("vishalkumar123@gmail.com");
    user.setFirstName("vishal");
    user.setLastName("kumar");
    user.setPassword("ok");
    user.setPinCode("Pin Code");
    user.setState("MD");
    user.setStreet("Street");
    user.setUserId("42");

    Order order = new Order();
    order.setCity("nashik");
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
    order.setUser(user);

   
    when(orderRepository.save(Mockito.<Order>any())).thenReturn(order);
    doNothing().when(orderRepository).deleteById(Mockito.<String>any());
    when(orderRepository.findOrderByOrderId(Mockito.<String>any())).thenReturn(order);

  }
 
 
  @Test
  void testUpdateOrderItem() {
  
    OrderItemDto orderItemDto = new OrderItemDto();
    when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<OrderItemDto>>any())).thenReturn(orderItemDto);

    User user = new User();
    user.setAddress("42 Main St");
    user.setCity("nashik");
    user.setEmail("vishalkumar123@gmail.com");
    user.setFirstName("vishal");
    user.setLastName("kumar");
    user.setPassword("ok");
    user.setPinCode("422103");
    user.setState("MD");
    user.setStreet("Street");
    user.setUserId("42");

    Order order = new Order();
    order.setCity("nashik");
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
    user2.setAddress("42 Main St");
    user2.setCity("nashik");
    user2.setEmail("vishalkumar123@gmail.com");
    user2.setFirstName("vishal");
    user2.setLastName("kumar");
    user2.setPassword("ok");
    user2.setPinCode("422103");
    user2.setState("MD");
    user2.setStreet("Street");
    user2.setUserId("42");

    Order order2 = new Order();
    order2.setCity("nashik");
    order2.setCreatedAt(mock(Date.class));
    order2.setDeliveredDate(LocalDate.of(1970, 1, 1));
    order2.setOrderAmount(10.0d);
    order2.setOrderId("42");
    order2.setOrderItems(new ArrayList<>());
    order2.setOrderName("Order Name");
    order2.setOrderNumber("42");
    order2.setOrderStatus("Order Status");
    order2.setPaymentStatus("Payment Status");
    order2.setPostalCode("Postal Code");
    order2.setShippingAddress("42 Main St");
    order2.setShippingPhone("6625550144");
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
    user3.setAddress("42 Main St");
    user3.setCity("nashik");
    user3.setEmail("vishalkumar123@gmail.com");
    user3.setFirstName("vishal");
    user3.setLastName("kumar");
    user3.setPassword("ok");
    user3.setPinCode("422103");
    user3.setState("MD");
    user3.setStreet("Street");
    user3.setUserId("42");

    Order order3 = new Order();
    order3.setCity("Oxford");
    order3.setCreatedAt(mock(Date.class));
    order3.setDeliveredDate(LocalDate.of(1970, 1, 1));
    order3.setOrderAmount(10.0d);
    order3.setOrderId("42");
    order3.setOrderItems(new ArrayList<>());
    order3.setOrderName("Order Name");
    order3.setOrderNumber("42");
    order3.setOrderStatus("Order Status");
    order3.setPaymentStatus("Payment Status");
    order3.setPostalCode("Postal Code");
    order3.setShippingAddress("42 Main St");
    order3.setShippingPhone("6625550144");
    order3.setState("MD");
    order3.setUser(user3);

    User user4 = new User();
    user4.setAddress("42 Main St");
    user4.setCity("nashik");
    user4.setEmail("vishalkumar123@gmail.com");
    user4.setFirstName("vishal");
    user4.setLastName("kumar");
    user4.setPassword("ok");
    user4.setPinCode("422103");
    user4.setState("MD");
    user4.setStreet("Street");
    user4.setUserId("42");

    Order order4 = new Order();
    order4.setCity("nashik");
    order4.setCreatedAt(mock(Date.class));
    order4.setDeliveredDate(LocalDate.of(1970, 1, 1));
    order4.setOrderAmount(10.0d);
    order4.setOrderId("42");
    order4.setOrderItems(new ArrayList<>());
    order4.setOrderName("Order Name");
    order4.setOrderNumber("42");
    order4.setOrderStatus("Order Status");
    order4.setPaymentStatus("Payment Status");
    order4.setPostalCode("Postal Code");
    order4.setShippingAddress("42 Main St");
    order4.setShippingPhone("6625550144");
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

   
    OrderItemDto actualUpdateOrderItemResult = orderServiceImpl.updateOrderItem(new UpdateOrderItem(1, 1, "42"));


    verify(orderItemRepository).findById(eq(1));
    verify(orderRepository).findOrderByOrderId(eq("42"));
    verify(productRepository).findByProductId(eq("42"));
    verify(modelMapper).map(isA(Object.class), isA(Class.class));
    verify(orderRepository).save(isA(Order.class));
    verify(orderItemRepository).save(isA(OrderItem.class));
    assertSame(orderItemDto, actualUpdateOrderItemResult);
  }

}
  
