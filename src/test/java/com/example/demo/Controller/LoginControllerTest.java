package minishopper.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import minishopper.Service.CustomUserDetailsService;
import minishopper.Service.LoginDataService;
import minishopper.Service.UserService;
import minishopper.dtos.LoginDto;
import minishopper.dtos.UserDto;
import minishopper.security.JwtHelper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
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
    private LoginDataService loginDataService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserService userService;    


   @Test
   void testGetUserById() throws Exception {
     
       Object[] uriVariables = new Object[]{"42"};
       MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/{userId}", uriVariables);
       Object[] controllers = new Object[]{loginController};
       MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

      
       ResultActions actualPerformResult = buildResult.perform(requestBuilder);

   
   }

 /* 
   @Test
   void testLoginUser() throws Exception {
       // Arrange
       // TODO: Populate arranged inputs
       Object[] uriVariables = new Object[]{};
       MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/users/loginUser", uriVariables)
               .contentType(MediaType.APPLICATION_JSON);

       LoginDto loginDto = new LoginDto();
       loginDto.setPassword("ok");
       loginDto.setUserId("42");

       ObjectMapper objectMapper = new ObjectMapper();
       MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(objectMapper.writeValueAsString(loginDto));
       Object[] controllers = new Object[]{loginController};
       MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

       // Act
       ResultActions actualPerformResult = buildResult.perform(requestBuilder);

       // Assert
       // TODO: Add assertions on result
   }
*/
  
   @Test
   void testUpdateUserDetails() throws Exception {
    
       Object[] uriVariables = new Object[]{"42"};
       MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/users/{userId}", uriVariables)
               .contentType(MediaType.APPLICATION_JSON);

       UserDto userDto = new UserDto();
       userDto.setAddress("42 Main St");
       userDto.setCity("nashik");
       userDto.setEmail("vishalkumar@gmail.com");
       userDto.setFirstName("vishal");
       userDto.setImage("Image");
       userDto.setLastName("kumar");
       userDto.setPinCode("Pin Code");
       userDto.setState("MD");
       userDto.setStreet("Street");
       userDto.setUserId("42");

       ObjectMapper objectMapper = new ObjectMapper();
       MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(objectMapper.writeValueAsString(userDto));
       Object[] controllers = new Object[]{loginController};
       MockMvc buildResult = MockMvcBuilders.standaloneSetup(controllers).build();

   
       ResultActions actualPerformResult = buildResult.perform(requestBuilder);

   }
}
