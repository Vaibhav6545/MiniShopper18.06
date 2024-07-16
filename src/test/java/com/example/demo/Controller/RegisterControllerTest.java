
package minishopper.Controller;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import minishopper.Entity.User;
import minishopper.Service.UserService;
import minishopper.dtos.UserDto;
@ContextConfiguration(classes = {RegisterController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RegisterControllerTest {
    @Autowired
    private RegisterController registerController;
    @MockBean
    private UserService userService;
   
  

@Test

  public void testSaveUser_NewUser_Success() {

   
      UserDto userDto = new UserDto();

      userDto.setUserId("testuser@example.com");


      when(userService.checkUserId("testuser@example.com")).thenReturn(null);

      ResponseEntity<String> response = registerController.saveUser(userDto);

      assertEquals(HttpStatus.CREATED, response.getStatusCode());

      assertEquals("User Created Successfully", response.getBody());
      verify(userService, times(1)).saveUser(userDto);

  }

  @Test

  public void testSaveUser_ExistingUser_Failure() {

      UserDto userDto = new UserDto();

      userDto.setUserId("existinguser@example.com");

      when(userService.checkUserId("existinguser@example.com")).thenReturn(new User());


      ResponseEntity<String> response = registerController.saveUser(userDto);


      assertEquals(HttpStatus.ALREADY_REPORTED, response.getStatusCode());


  }

}

 
 