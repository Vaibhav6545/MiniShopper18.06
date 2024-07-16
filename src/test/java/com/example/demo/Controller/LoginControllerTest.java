package minishopper.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import minishopper.Entity.User;
import minishopper.Repository.UserRepository;
import minishopper.Service.CustomUserDetailsService;
import minishopper.Service.UserService;
import minishopper.Service.impl.UserServiceImpl;
import minishopper.dtos.AddressDto;
import minishopper.dtos.JwtResponseDto;
import minishopper.dtos.LoginDto;
import minishopper.dtos.UserDto;
import minishopper.exception.InvalidInputException;
import minishopper.security.JwtHelper;

@ContextConfiguration(classes = {LoginController.class, AuthenticationManager.class})
@ExtendWith(SpringExtension.class)

@SpringBootTest
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
    
   @Mock
    private UserRepository userRepository; 
    
    @InjectMocks 
    private UserServiceImpl userServiceImpl;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
       
    }

    @Test
    public void testCheckUserId_UserExists1() {

        String userId = "existingUser";

        User user = new User();

        when(userService.checkUserId(userId)).thenReturn(user);

        boolean result = loginController.checkUserId(userId);
        assertTrue(result);

    }

    @Test

    public void testCheckUserId_UserDoesNotExist2() {

        String userId = "nonExistingUser";
        when(userService.checkUserId(userId)).thenReturn(null);
        boolean result = loginController.checkUserId(userId);
        assertFalse(result);

    }

  
    @Test

      public void testAddAddressDetails_InvalidUserId() {

          // Arrange

          String userId = "invalidUser";

          AddressDto address = new AddressDto();

          when(userService.checkUserId(userId)).thenReturn(null);

          // Act & Assert

          assertThrows(InvalidInputException.class, () -> {

              loginController.addAddressDetails(userId, address);

          });

      }

      @Test

      public void testAddAddressDetails_ValidUserId() throws Exception {

          // Arrange

          String userId = "validUser";

          AddressDto address = new AddressDto();

          User user = new User();

          user.setUserId(userId);

          UserDto userDto = new UserDto();

          userDto.setUserId(userId);

          when(userService.checkUserId(userId)).thenReturn(user);

          when(userService.addAddress(userId, address)).thenReturn(userDto);

          // Act

          ResponseEntity<UserDto> response = loginController.addAddressDetails(userId, address);

          // Assert

          assertEquals(HttpStatus.OK, response.getStatusCode());

          assertEquals(userDto, response.getBody());

      }

  
   
  
 
    
    

    @Test

        public void testGetUserById_InvalidUserId() {

            // Arrange

            String userId = "invalidUser";

            when(userService.checkUserId(userId)).thenReturn(null);

            // Act & Assert

            assertThrows(InvalidInputException.class, () -> {

                loginController.getUserById(userId);

            });

        }

        @Test

        public void testGetUserById_ValidUserId() throws Exception {

            // Arrange

            String userId = "validUser";

            User user = new User();

            user.setUserId(userId);

            UserDto userDto = new UserDto();

            userDto.setUserId(userId);

            when(userService.checkUserId(userId)).thenReturn(user);

            when(userService.fetchUserDetailsById(userId)).thenReturn(userDto);

            // Act

            ResponseEntity<UserDto> response = loginController.getUserById(userId);

            // Assert

            assertEquals(HttpStatus.OK, response.getStatusCode());

            assertEquals(userDto, response.getBody());

        }

    
  
    @Test
     public void testLoginUser_InvalidUserId() {

         // Mock data

         LoginDto loginDto = new LoginDto();

         loginDto.setUserId("invalidUser");

         loginDto.setPassword("password");

         // Define mock behavior

         when(userService.checkUserId(loginDto.getUserId())).thenReturn(null);

         // Invoke method and assert exception

         BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {

             loginController.loginUser(loginDto);

         });

         // Verify exception message

         assertEquals("Invalid UserId!", exception.getMessage());

         // Verify interactions

         verify(userService, times(1)).checkUserId(loginDto.getUserId());

         verify(customUserDetailsService, never()).loadUserByUsername(anyString());

         verify(jwtHelper, never()).generateToken(any());

         verify(jwtHelper, never()).generateRefreshToken(any());

     }

     @Test

     public void testLoginUser_InvalidCredentials() {

         // Mock data

         String userId = "validUser";

         LoginDto loginDto = new LoginDto();

         loginDto.setUserId(userId);

         loginDto.setPassword("password");

         loginDto.setRole("USER");

         UserDto mockUserDto = new UserDto();

         mockUserDto.setRole("ADMIN"); // Different role to trigger invalid credentials

         UserDetails userDetails = mock(UserDetails.class);

         when(userDetails.getUsername()).thenReturn(userId);

         // Define mock behaviors

         when(userService.checkUserId(loginDto.getUserId())).thenReturn(new User());

         when(customUserDetailsService.loadUserByUsername(loginDto.getUserId())).thenReturn(userDetails);

         when(userService.fetchUserDetailsById(userId)).thenReturn(mockUserDto);

         // Invoke method and assert exception

         BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {

             loginController.loginUser(loginDto);

         });

         // Verify exception message

         assertEquals("Invalid Credentials!", exception.getMessage());

         // Verify interactions

         verify(userService, times(1)).checkUserId(loginDto.getUserId());

         verify(customUserDetailsService, times(1)).loadUserByUsername(loginDto.getUserId());

       //  verify(jwtHelper, never()).generateToken(any());

        // verify(jwtHelper, never()).generateRefreshToken(any());

     }

     @Test

     public void testLoginUser_Success() throws BadCredentialsException {

         // Mock data

         String userId = "validUser";

         LoginDto loginDto = new LoginDto();

         loginDto.setUserId(userId);

         loginDto.setPassword("password");

         loginDto.setRole("USER");

         UserDto mockUserDto = new UserDto();

         mockUserDto.setRole("USER"); // Matching role

         UserDetails userDetails = mock(UserDetails.class);

         when(userDetails.getUsername()).thenReturn(userId);

         String jwtToken = "jwtToken";

         String refreshToken = "refreshToken";

         // Define mock behaviors

         when(userService.checkUserId(loginDto.getUserId())).thenReturn(new User());

         when(customUserDetailsService.loadUserByUsername(loginDto.getUserId())).thenReturn(userDetails);

         when(jwtHelper.generateToken(userDetails)).thenReturn(jwtToken);

         when(jwtHelper.generateRefreshToken(userDetails)).thenReturn(refreshToken);

         when(userService.fetchUserDetailsById(userId)).thenReturn(mockUserDto);

         // Invoke method

         ResponseEntity<JwtResponseDto> response = loginController.loginUser(loginDto);

         // Verify result

         assertEquals(HttpStatus.OK, response.getStatusCode());

         JwtResponseDto responseBody = response.getBody();

         assertNotNull(responseBody);

         assertEquals(jwtToken, responseBody.getAccessToken());

         assertEquals(refreshToken, responseBody.getRefreshToken());

         assertEquals(mockUserDto, responseBody.getUser());

         // Verify interactions

         verify(userService, times(1)).checkUserId(loginDto.getUserId());

         verify(customUserDetailsService, times(1)).loadUserByUsername(loginDto.getUserId());

         verify(jwtHelper, times(1)).generateToken(userDetails);

         verify(jwtHelper, times(1)).generateRefreshToken(userDetails);

         verify(userService, times(1)).fetchUserDetailsById(userId);

     }

 
  
  
    @Test
    public void testUpdateUserDetails_InvalidUserId() {
        // Arrange
        String userId = "invalidUser";
        AddressDto address = new AddressDto();
        when(userService.checkUserId(userId)).thenReturn(null);
        // Act & Assert
        assertThrows(InvalidInputException.class, () -> {
            loginController.updateUserDetails(userId, address);
        });
    }
    @Test
    public void testUpdateUserDetails_ValidUserId() throws Exception {
        // Arrange
        String userId = "validUser";
        AddressDto address = new AddressDto();
        User user = new User();
        user.setUserId(userId);
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        when(userService.checkUserId(userId)).thenReturn(user);
        when(userService.updateUser(userId, address)).thenReturn(userDto);
        // Act
        ResponseEntity<UserDto> response = loginController.updateUserDetails(userId, address);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }
 
}
