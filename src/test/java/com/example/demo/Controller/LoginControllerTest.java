package com.example.demo.Controller;

import static org.mockito.Mockito.when;

import com.example.demo.Entity.LoginData;
import com.example.demo.Entity.User;
import com.example.demo.Service.LoginDataService;
import com.example.demo.Service.UserService;
import com.example.demo.dtos.LoginDto;
import com.example.demo.dtos.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LoginController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LoginControllerDiffblueTest {
    @Autowired
    private LoginController loginController;

    @MockBean
    private LoginDataService loginDataService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link LoginController#getUserById(String)}
     */
    @Test
    void testGetUserById() throws Exception {
        // Arrange
        UserDto buildResult = UserDto.builder()
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
        when(userService.fetchUserDetailsById(Mockito.<String>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/{userId}", "42");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":\"42\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,"
                                        + "\"street\":\"Street\",\"city\":\"Oxford\",\"state\":\"MD\",\"pinCode\":\"Pin Code\",\"image\":\"Image\"}"));
    }

    /**
     * Method under test: {@link LoginController#loginUser(LoginDto)}
     */
    @Test
    void testLoginUser() throws Exception {
        // Arrange
        LoginData loginData = new LoginData();
        loginData.setDate(LocalDate.of(1970, 1, 1));
        loginData.setLoginStatus("Login Status");
        loginData.setRecord(1);
        loginData.setTime(LocalTime.MIDNIGHT);
        loginData.setUserId("42");
        when(loginDataService.saveLoginData(Mockito.<LoginData>any())).thenReturn(loginData);
        UserDto buildResult = UserDto.builder()
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
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any())).thenReturn(buildResult);

        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPinCode("Pin Code");
        user.setState("MD");
        user.setStreet("Street");
        user.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user);

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("iloveyou");
        loginDto.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(loginDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/loginUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"200\",\"statusMessage\":\"OK\",\"message\":\"Login Success\",\"user\":{\"userId\":\"42\",\"firstName\":\"Jane"
                                        + "\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,\"street\":\"Street\",\"city\":\"Oxford\","
                                        + "\"state\":\"MD\",\"pinCode\":\"Pin Code\",\"image\":\"Image\"}}"));
    }

    /**
     * Method under test: {@link LoginController#updateUserDetails(String, UserDto)}
     */
    @Test
    void testUpdateUserDetails() throws Exception {
        // Arrange
        UserDto buildResult = UserDto.builder()
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
        when(userService.updateUser(Mockito.<String>any(), Mockito.<UserDto>any())).thenReturn(buildResult);

        UserDto userDto = new UserDto();
        userDto.setAddress("42 Main St");
        userDto.setCity("Oxford");
        userDto.setEmail("jane.doe@example.org");
        userDto.setFirstName("Jane");
        userDto.setImage("Image");
        userDto.setLastName("Doe");
        userDto.setPinCode("Pin Code");
        userDto.setState("MD");
        userDto.setStreet("Street");
        userDto.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(userDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{userId}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(loginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userId\":\"42\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"address\":null,"
                                        + "\"street\":\"Street\",\"city\":\"Oxford\",\"state\":\"MD\",\"pinCode\":\"Pin Code\",\"image\":\"Image\"}"));
    }
}
