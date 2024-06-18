package com.example.demo.Controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.demo.Service.CartService;
import com.example.demo.dtos.AddItemToCart;
import com.example.demo.dtos.CartDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CartController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CartControllerDiffblueTest {
    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

    /**
     * Method under test:
     * {@link CartController#addItemToCart(String, AddItemToCart)}
     */
    @Test
    void testAddItemToCart() throws Exception {
        // Arrange
        when(cartService.addItemToCart(Mockito.<String>any(), Mockito.<AddItemToCart>any())).thenReturn(new CartDto());

        AddItemToCart addItemToCart = new AddItemToCart();
        addItemToCart.setProductId("42");
        addItemToCart.setQuantity(1);
        String content = (new ObjectMapper()).writeValueAsString(addItemToCart);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/carts/{userId}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"cartId\":null,\"userDto\":null,\"items\":[]}"));
    }

    /**
     * Method under test: {@link CartController#deleteItem(String, int)}
     */
    @Test
    void testDeleteItem() throws Exception {
        // Arrange
        doNothing().when(cartService).deleteItemFromCart(Mockito.<String>any(), anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/carts/{userId}/item/{itemId}", "42",
                1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("carts/42/item/1"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("carts/42/item/1"));
    }

    /**
     * Method under test: {@link CartController#deleteItem(String, int)}
     */
    @Test
    void testDeleteItem2() throws Exception {
        // Arrange
        doNothing().when(cartService).deleteItemFromCart(Mockito.<String>any(), anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/carts/{userId}/item/{itemId}", "42",
                1);
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("carts/42/item/1"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("carts/42/item/1"));
    }

    /**
     * Method under test: {@link CartController#getCartByUserId(String)}
     */
    @Test
    void testGetCartByUserId() throws Exception {
        // Arrange
        when(cartService.fetCartbyUser(Mockito.<String>any())).thenReturn(new CartDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/carts/user/{userId}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"cartId\":null,\"userDto\":null,\"items\":[]}"));
    }

    /**
     * Method under test: {@link CartController#getCartByUserId(String)}
     */
    @Test
    void testGetCartByUserId2() throws Exception {
        // Arrange
        when(cartService.fetCartbyUser(Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/carts/user/{userId}", "42");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(cartController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
