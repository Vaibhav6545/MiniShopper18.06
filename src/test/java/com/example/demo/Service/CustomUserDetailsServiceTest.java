
package minishopper.Servicemain;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import minishopper.Entity.User;
import minishopper.Repository.UserRepository;
import minishopper.Service.CustomUserDetailsService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ContextConfiguration(classes = {CustomUserDetailsService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomUserDetailsServiceTest {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private UserRepository userRepository;
  
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
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
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user);
        // Act
        UserDetails actualLoadUserByUsernameResult = customUserDetailsService.loadUserByUsername("janedoe");
        // Assert
        verify(userRepository).findByUserId(eq("janedoe"));
        assertEquals("42", actualLoadUserByUsernameResult.getUsername());
        assertEquals("ok", actualLoadUserByUsernameResult.getPassword());
        assertTrue(actualLoadUserByUsernameResult.getAuthorities().isEmpty());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
    }
}
 
 