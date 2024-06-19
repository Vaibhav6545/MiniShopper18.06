
package com.example.demo.Service.impl;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.demo.Entity.Cart;
import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dtos.AddItemToCart;
import com.example.demo.dtos.CartDto;
import java.util.ArrayList;
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
    /**
     * Method under test:
     * {@link CartServiceImpl#addItemToCart(String, AddItemToCart)}
     */
    @Test
    void testAddItemToCart() {
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
        when(cartRepository.findByUser(Mockito.<User>any())).thenReturn(cart);
        CartDto cartDto = new CartDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CartDto>>any())).thenReturn(cartDto);
        Product product = new Product();
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDiscountedPrice(10.0d);
        product.setProductId("42");
        product.setProductName("Product Name");
        product.setShortDescription("Short Description");
        product.setStock(1);
        product.setUnitPrice(10.0d);
        when(productRepository.findByProductId(Mockito.<String>any())).thenReturn(product);
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
        CartDto actualAddItemToCartResult = cartServiceImpl.addItemToCart("42", new AddItemToCart("42", 1));
        // Assert
        verify(cartRepository).findByUser(isA(User.class));
        verify(productRepository).findByProductId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(cartRepository).save(isA(Cart.class));
        assertSame(cartDto, actualAddItemToCartResult);
    }
    /**
     * Method under test:
     * {@link CartServiceImpl#addItemToCart(String, AddItemToCart)}
     */
    @Test
    void testAddItemToCart2() {
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
        product.setStock(1);
        product.setUnitPrice(10.0d);
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setCartItemId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
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
        when(cartRepository.findByUser(Mockito.<User>any())).thenReturn(cart2);
        CartDto cartDto = new CartDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CartDto>>any())).thenReturn(cartDto);
        Product product2 = new Product();
        product2.setBrand("Brand");
        product2.setCategory("Category");
        product2.setDiscountedPrice(10.0d);
        product2.setProductId("42");
        product2.setProductName("Product Name");
        product2.setShortDescription("Short Description");
        product2.setStock(1);
        product2.setUnitPrice(10.0d);
        when(productRepository.findByProductId(Mockito.<String>any())).thenReturn(product2);
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
        CartDto actualAddItemToCartResult = cartServiceImpl.addItemToCart("42", new AddItemToCart("42", 1));
        // Assert
        verify(cartRepository).findByUser(isA(User.class));
        verify(productRepository).findByProductId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(cartRepository).save(isA(Cart.class));
        assertSame(cartDto, actualAddItemToCartResult);
    }
    /**
     * Method under test:
     * {@link CartServiceImpl#addItemToCart(String, AddItemToCart)}
     */
    @Test
    void testAddItemToCart3() {
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
        product.setStock(1);
        product.setUnitPrice(10.0d);
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setCartItemId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotalPrice(10.0d);
        User user2 = new User();
        user2.setAddress("17 High St");
        user2.setCity("London");
        user2.setEmail("john.smith@example.org");
        user2.setFirstName("John");
        user2.setLastName("Smith");
        user2.setPassword("42");
        user2.setPinCode("Pin Code");
        user2.setState("42");
        user2.setStreet("Street");
        user2.setUserId("User Id");
        Cart cart2 = new Cart();
        cart2.setCartId("Cart Id");
        cart2.setItems(new ArrayList<>());
        cart2.setUser(user2);
        Product product2 = new Product();
        product2.setBrand("Brand");
        product2.setCategory("Category");
        product2.setDiscountedPrice(0.0d);
        product2.setProductId("Product Id");
        product2.setProductName("Product Name");
        product2.setShortDescription("Short Description");
        product2.setStock(0);
        product2.setUnitPrice(0.0d);
        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart2);
        cartItem2.setCartItemId(2);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(0);
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
        when(cartRepository.findByUser(Mockito.<User>any())).thenReturn(cart3);
        CartDto cartDto = new CartDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CartDto>>any())).thenReturn(cartDto);
        Product product3 = new Product();
        product3.setBrand("Brand");
        product3.setCategory("Category");
        product3.setDiscountedPrice(10.0d);
        product3.setProductId("42");
        product3.setProductName("Product Name");
        product3.setShortDescription("Short Description");
        product3.setStock(1);
        product3.setUnitPrice(10.0d);
        when(productRepository.findByProductId(Mockito.<String>any())).thenReturn(product3);
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
        CartDto actualAddItemToCartResult = cartServiceImpl.addItemToCart("42", new AddItemToCart("42", 1));
        // Assert
        verify(cartRepository).findByUser(isA(User.class));
        verify(productRepository).findByProductId(eq("42"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(cartRepository).save(isA(Cart.class));
        assertSame(cartDto, actualAddItemToCartResult);
    }
    /**
     * Method under test: {@link CartServiceImpl#fetCartbyUser(String)}
     */
    @Test
    void testFetCartbyUser() {
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
        when(cartRepository.findByUser(Mockito.<User>any())).thenReturn(cart);
        CartDto cartDto = new CartDto();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<CartDto>>any())).thenReturn(cartDto);
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
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user2);
        // Act
        CartDto actualFetCartbyUserResult = cartServiceImpl.fetCartbyUser("42");
        // Assert
        verify(cartRepository).findByUser(isA(User.class));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        assertSame(cartDto, actualFetCartbyUserResult);
    }
    /**
     * Method under test: {@link CartServiceImpl#deleteItemFromCart(String, int)}
     */
    @Test
    void testDeleteItemFromCart() {
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
        // Act
        cartServiceImpl.deleteItemFromCart("42", 1);
        // Assert that nothing has changed
        verify(cartItemRepository).findByCartItemId(eq(1));
        verify(cartItemRepository).delete(isA(CartItem.class));
    }
}
 