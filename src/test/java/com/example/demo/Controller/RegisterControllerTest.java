
package minishopper.Controller;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import minishopper.dtos.UserDto;
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
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("ok");
        user.setRole("Role");
        user.setUserId("42");
        User user2 = new User();
        user2.setAddresses(new ArrayList<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("ok");
        user2.setRole("Role");
        user2.setUserId("42");
        when(userService.checkUserId(Mockito.<String>any())).thenReturn(user);
        when(userService.saveUser(Mockito.<UserDto>any())).thenReturn(user2);
        UserDto userDto = new UserDto();
        userDto.setAddress(new ArrayList<>());
        userDto.setEmail("jane.doe@example.org");
        userDto.setFirstName("Jane");
        userDto.setLastName("Doe");
        userDto.setPassword("ok");
        userDto.setRole("Role");
        userDto.setUserId("42");
        String content = (new ObjectMapper()).writeValueAsString(userDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/newUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(registerController)
                .build()
                .perform(requestBuilder);
        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(208));
    }
}
 
 