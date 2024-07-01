package minishopper.Controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import minishopper.Entity.User;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RegisterController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RegisterControllerTest {
    @Autowired
    private RegisterController registerController;

    @MockBean
    private UserService userService;

   
    @Test
    void testSaveUser() throws Exception {
        // Arrange
        User user = new User();
        user.setAddress("42 Main St");
        user.setCity("nashik");
        user.setEmail("satish123@gmail.com");
        user.setFirstName("Satish");
        user.setLastName("Kumar");
        user.setPassword("ok");
        user.setPinCode("422103");
        user.setState("MD");
        user.setStreet("Street");
        user.setUserId("42");

        User user2 = new User();
        user2.setAddress("42 Main St");
        user2.setCity("nashik");
        user2.setEmail("satish123@gmail.com");
        user2.setFirstName("satish");
        user2.setLastName("kumar");
        user2.setPassword("ok");
        user2.setPinCode("422103");
        user2.setState("MD");
        user2.setStreet("Street");
        user2.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user);
        when(userService.saveUser(Mockito.<User>any())).thenReturn(user2);

        User user3 = new User();
        user3.setAddress("42 Main St");
        user3.setCity("Oxford");
        user3.setEmail("satish123@gmail.com");
        user3.setFirstName("satish");
        user3.setLastName("kumar");
        user3.setPassword("ok");
        user3.setPinCode("422103");
        user3.setState("MD");
        user3.setStreet("Street");
        user3.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(user3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

      
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(registerController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(208))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"208\",\"statusMessage\":\"Already Reported\",\"message\":\"Entered Email address already exist,"
                                        + " please try again with other Email address\"}"));
    }
}
