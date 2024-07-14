package minishopper.Controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import minishopper.dtos.AddItemToCartDto;
import minishopper.dtos.CartDto;
import minishopper.dtos.UserDto;
import minishopper.Entity.Cart;
import minishopper.Entity.CartItem;
import minishopper.Entity.Product;
import minishopper.Entity.User;
import minishopper.exception.InvalidInputException;
import minishopper.Service.CartService;
import minishopper.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CartController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CartControllerTest {
    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    
    @Test
    void testCheckUserId() {
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
        String userId = "42";
        boolean actualCheckUserIdResult = cartController.checkUserId(userId);
        verify(userService).checkUserId(eq("42"));
        assertTrue(actualCheckUserIdResult);
    }

    @Test
    void testCheckUserId2() {
        when(userService.checkUserId(Mockito.<String>any())).thenThrow(new InvalidInputException("An error occurred"));
        String userId = "42";
        assertThrows(InvalidInputException.class, () -> cartController.checkUserId(userId));
        verify(userService).checkUserId(eq("42"));
    }

   
    @Test
    void testAddItemToCart() throws Exception {
        CartDto.CartDtoBuilder cartIdResult = CartDto.builder().cartId("42");
        CartDto.CartDtoBuilder itemsResult = cartIdResult.items(new ArrayList<>());
        UserDto userDto = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        CartDto buildResult = itemsResult.userDto(userDto).build();
        when(cartService.addItemToCart(Mockito.<String>any(), Mockito.<AddItemToCartDto>any())).thenReturn(buildResult);

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

        AddItemToCartDto addItemToCartDto = new AddItemToCartDto();
        addItemToCartDto.setProductId("42");
        addItemToCartDto.setQuantity(1);
        String content = (new ObjectMapper()).writeValueAsString(addItemToCartDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/carts/{userId}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult2 = MockMvcBuilders.standaloneSetup(cartController).build();
        ResultActions actualPerformResult = buildResult2.perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"cartId\":\"42\",\"userDto\":{\"userId\":\"42\",\"password\":\"ok\",\"firstName\":\"Jane\",\"lastName\":\"Doe\","
                                        + "\"email\":\"jane.doe@example.org\",\"address\":null,\"role\":\"Role\"},\"items\":[]}"));
    }

    @Test
    void testDeleteItem() throws Exception {
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
        product.setStock(1);
        product.setUnitPrice(10.0d);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setCartItemId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setTotalPrice(10.0d);
        doNothing().when(cartService).deleteItemFromCart(Mockito.<String>any(), anyInt());
        when(cartService.getCartItemById(anyInt())).thenReturn(cartItem);

        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/carts/{userId}/item/{itemId}", "42",
                1);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(cartController).build();
        ResultActions actualPerformResult = buildResult.perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Deleted Successfully"));
    }

    @Test
    void testGetCartByUserId() throws Exception {
        CartDto.CartDtoBuilder cartIdResult = CartDto.builder().cartId("42");
        CartDto.CartDtoBuilder itemsResult = cartIdResult.items(new ArrayList<>());
        UserDto userDto = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        CartDto buildResult = itemsResult.userDto(userDto).build();
        when(cartService.fetCartbyUser(Mockito.<String>any())).thenReturn(buildResult);

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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/carts/user/{userId}", "42");
        MockMvc buildResult2 = MockMvcBuilders.standaloneSetup(cartController).build();
        ResultActions actualPerformResult = buildResult2.perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"cartId\":\"42\",\"userDto\":{\"userId\":\"42\",\"password\":\"ok\",\"firstName\":\"Jane\",\"lastName\":\"Doe\","
                                        + "\"email\":\"jane.doe@example.org\",\"address\":null,\"role\":\"Role\"},\"items\":[]}"));
    }
}
