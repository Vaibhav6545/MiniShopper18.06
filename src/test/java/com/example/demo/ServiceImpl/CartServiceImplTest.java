package minishopper.Service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import minishopper.dtos.AddItemToCartDto;
import minishopper.dtos.CartDto;
import minishopper.dtos.CartItemDto;
import minishopper.dtos.UserDto;
import minishopper.Entity.Cart;
import minishopper.Entity.CartItem;
import minishopper.Entity.Product;
import minishopper.Entity.User;
import minishopper.exception.ResourceNotFoundException;
import minishopper.Repository.CartItemRepository;
import minishopper.Repository.CartRepository;
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

@ContextConfiguration(classes = {CartServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CartServiceImplTest {
    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private CartServiceImpl cartServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserRepository userRepository;

   
    @Test
    void testAddItemToCart() throws ResourceNotFoundException {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("iloveyou");
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
        user2.setPassword("iloveyou");
        user2.setRole("Role");
        user2.setUserId("42");

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart);
        when(cartRepository.findByUser(Mockito.<User>any())).thenReturn(cart2);
        CartDto.CartDtoBuilder cartIdResult = CartDto.builder().cartId("42");
        ArrayList<CartItemDto> items = new ArrayList<>();
        CartDto.CartDtoBuilder itemsResult = cartIdResult.items(items);
        UserDto userDto = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("iloveyou")
                .role("Role")
                .userId("42")
                .build();
        CartDto buildResult = itemsResult.userDto(userDto).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CartDto>>any())).thenReturn(buildResult);

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
        user3.setPassword("iloveyou");
        user3.setRole("Role");
        user3.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user3);
        String userId = "42";
        AddItemToCartDto item = new AddItemToCartDto("42", 1);

        CartDto actualAddItemToCartResult = cartServiceImpl.addItemToCart(userId, item);
        verify(cartRepository).findByUser(isA(User.class));
        verify(productRepository).findByProductId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(cartRepository).save(isA(Cart.class));
        assertEquals("42", actualAddItemToCartResult.getCartId());
        UserDto userDto2 = actualAddItemToCartResult.getUserDto();
        assertEquals("42", userDto2.getUserId());
        assertEquals("Doe", userDto2.getLastName());
        assertEquals("Jane", userDto2.getFirstName());
        assertEquals("Role", userDto2.getRole());
        assertEquals("iloveyou", userDto2.getPassword());
        assertEquals("jane.doe@example.org", userDto2.getEmail());
        assertNull(userDto2.getAddress());
        assertSame(items, actualAddItemToCartResult.getItems());
    }

   @Test
    void testAddItemToCart2() throws ResourceNotFoundException {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        CartDto.CartDtoBuilder cartIdResult = CartDto.builder().cartId("42");
        CartDto.CartDtoBuilder itemsResult = cartIdResult.items(new ArrayList<>());
        UserDto userDto = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("iloveyou")
                .role("Role")
                .userId("42")
                .build();
        CartDto buildResult = itemsResult.userDto(userDto).build();
        
    }

  
    @Test
    void testAddItemToCart3() throws ResourceNotFoundException {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("iloveyou");
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
        user2.setPassword("iloveyou");
        user2.setRole("Role");
        user2.setUserId("42");

        Cart cart2 = new Cart();
        cart2.setCartId("42");
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);
        when(cartRepository.save(Mockito.<Cart>any())).thenReturn(cart);
        when(cartRepository.findByUser(Mockito.<User>any())).thenReturn(cart2);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CartDto>>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

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
        user3.setPassword("iloveyou");
        user3.setRole("Role");
        user3.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user3);
        String userId = "42";
        AddItemToCartDto item = AddItemToCartDto.builder().productId("42").quantity(1).build();
        assertThrows(ResourceNotFoundException.class, () -> cartServiceImpl.addItemToCart(userId, item));
        verify(cartRepository).findByUser(isA(User.class));
        verify(productRepository).findByProductId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(cartRepository).save(isA(Cart.class));
    }

  
    @Test
    void testFetCartbyUser() throws ResourceNotFoundException {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUserId("42");

        Cart cart = new Cart();
        cart.setCartId("42");
        cart.setItems(new ArrayList<>());
        cart.setUser(user);
        when(cartRepository.findByUser(Mockito.<User>any())).thenReturn(cart);
        CartDto.CartDtoBuilder cartIdResult = CartDto.builder().cartId("42");
        ArrayList<CartItemDto> items = new ArrayList<>();
        CartDto.CartDtoBuilder itemsResult = cartIdResult.items(items);
        UserDto userDto = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("iloveyou")
                .role("Role")
                .userId("42")
                .build();
        CartDto buildResult = itemsResult.userDto(userDto).build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CartDto>>any())).thenReturn(buildResult);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setRole("Role");
        user2.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user2);
        String userId = "42";
        CartDto actualFetCartbyUserResult = cartServiceImpl.fetCartbyUser(userId);
        verify(cartRepository).findByUser(isA(User.class));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        assertEquals("42", actualFetCartbyUserResult.getCartId());
        UserDto userDto2 = actualFetCartbyUserResult.getUserDto();
        assertEquals("42", userDto2.getUserId());
        assertEquals("Doe", userDto2.getLastName());
        assertEquals("Jane", userDto2.getFirstName());
        assertEquals("Role", userDto2.getRole());
        assertEquals("iloveyou", userDto2.getPassword());
        assertEquals("jane.doe@example.org", userDto2.getEmail());
        assertNull(userDto2.getAddress());
        assertSame(items, actualFetCartbyUserResult.getItems());
    }

  
   @Test
    void testFetCartbyUser2() throws ResourceNotFoundException {
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        CartDto.CartDtoBuilder cartIdResult = CartDto.builder().cartId("42");
        CartDto.CartDtoBuilder itemsResult = cartIdResult.items(new ArrayList<>());
        UserDto userDto = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("iloveyou")
                .role("Role")
                .userId("42")
                .build();
        CartDto buildResult = itemsResult.userDto(userDto).build();
       
    }

  
    @Test
    void testDeleteItemFromCart() {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("iloveyou");
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
        product.setStock(1);
        product.setUnitPrice(10.0d);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setCartItemId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotalPrice(10.0d);
        when(cartItemRepository.findByCartItemId(anyInt())).thenReturn(cartItem);
        doNothing().when(cartItemRepository).delete(Mockito.<CartItem>any());
        String userId = "42";
        int cartItemId = 1;
        cartServiceImpl.deleteItemFromCart(userId, cartItemId);
        verify(cartItemRepository).findByCartItemId(eq(1));
        verify(cartItemRepository).delete(isA(CartItem.class));
    }

   
    @Test
    void testDeleteItemFromCart2() {
        when(cartItemRepository.findByCartItemId(anyInt())).thenThrow(new ResourceNotFoundException("An error occurred"));
        String userId = "42";
        int cartItemId = 1;
        assertThrows(ResourceNotFoundException.class, () -> cartServiceImpl.deleteItemFromCart(userId, cartItemId));
        verify(cartItemRepository).findByCartItemId(eq(1));
    }

   
    @Test
    void testGetCartItemById() {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("iloveyou");
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
        product.setStock(1);
        product.setUnitPrice(10.0d);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setCartItemId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotalPrice(10.0d);
        when(cartItemRepository.findByCartItemId(anyInt())).thenReturn(cartItem);
        int cartItemId = 1;
        CartItem actualCartItemById = cartServiceImpl.getCartItemById(cartItemId);
        verify(cartItemRepository).findByCartItemId(eq(1));
        assertSame(cartItem, actualCartItemById);
    }

   
    @Test
    void testGetCartItemById2() {
        when(cartItemRepository.findByCartItemId(anyInt())).thenThrow(new ResourceNotFoundException("An error occurred"));
        int cartItemId = 1;
        assertThrows(ResourceNotFoundException.class, () -> cartServiceImpl.getCartItemById(cartItemId));
        verify(cartItemRepository).findByCartItemId(eq(1));
    }
}
 