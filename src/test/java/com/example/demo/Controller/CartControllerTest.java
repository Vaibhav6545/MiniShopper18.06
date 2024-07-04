package minishopper.Controller;


import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import minishopper.Controller.CartController;
import minishopper.Service.CartService;
import minishopper.dtos.AddItemToCart;
import minishopper.dtos.CartDto;

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
class CartControllerTest {
    @Autowired
    private CartController cartController;

    @MockBean
    private CartService cartService;

  
    @Test
    void testAddItemToCart() throws Exception {
      
        when(cartService.addItemToCart(Mockito.<String>any(), Mockito.<AddItemToCart>any())).thenReturn(new CartDto());

        AddItemToCart addItemToCart = new AddItemToCart();
        addItemToCart.setProductId("42");
        addItemToCart.setQuantity(1);
        String content = (new ObjectMapper()).writeValueAsString(addItemToCart);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/carts/{userId}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

       
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"cartId\":null,\"userDto\":null,\"items\":[]}"));
    }

   
    @Test
    void testGetCartByUserId() throws Exception {
     
        when(cartService.fetCartbyUser(Mockito.<String>any())).thenReturn(new CartDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/carts/user/{userId}", "42");

        
        MockMvcBuilders.standaloneSetup(cartController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"cartId\":null,\"userDto\":null,\"items\":[]}"));
    }

  
    
}
