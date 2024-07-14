package minishopper.Controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import minishopper.dtos.AddressDto;
import minishopper.dtos.LoginDto;
import minishopper.dtos.UserDto;
import minishopper.Entity.User;
import minishopper.security.JwtHelper;
import minishopper.Service.CustomUserDetailsService;
import minishopper.Service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LoginController.class, AuthenticationManager.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LoginControllerTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtHelper jwtHelper;

    @Autowired
    private LoginController loginController;

    @MockBean
    private UserService userService;

    @Test
   
    void testCheckUserId() {
        
        String userId = "42";
        loginController.checkUserId(userId);
    }

    
    @Test
    void testAddAddressDetails() throws Exception {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");
        UserDto buildResult = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("ok")
                .role("Role")
                .userId("42")
                .build();
        when(userService.addAddress(Mockito.<String>any(), Mockito.<AddressDto>any())).thenReturn(buildResult);
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user);

        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(1);
        addressDto.setAddressLine("42 Main St");
        addressDto.setAddressType("42 Main St");
        addressDto.setCity("Oxford");
        addressDto.setPinCode("Pin Code");
        addressDto.setState("MD");
        addressDto.setStreet("Street");
        String content = (new ObjectMapper()).writeValueAsString(addressDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/addAddress/{userId}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult2 = MockMvcBuilders.standaloneSetup(loginController).build();
        ResultActions actualPerformResult = buildResult2.perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":\"42\",\"password\":\"ok\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org"
                                        + "\",\"address\":null,\"role\":\"Role\"}"));
    }

  
    @Test

    void testGetUserById() throws Exception {
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/{userId}", "42");
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(loginController).build();
     //   buildResult.perform(requestBuilder);
    }

   
    @Test
   
    void testLoginUser() throws Exception {
       
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("ok");
        loginDto.setRole("Role");
        loginDto.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(loginDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/loginUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(loginController).build();
      //  buildResult.perform(requestBuilder);
    }

   
    @Test
  
    void testUpdateUserDetails() throws Exception {
       
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(1);
        addressDto.setAddressLine("42 Main St");
        addressDto.setAddressType("42 Main St");
        addressDto.setCity("Oxford");
        addressDto.setPinCode("Pin Code");
        addressDto.setState("MD");
        addressDto.setStreet("Street");
        String content = (new ObjectMapper()).writeValueAsString(addressDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{userId}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvc buildResult = MockMvcBuilders.standaloneSetup(loginController).build();
       // buildResult.perform(requestBuilder);
    }
}
