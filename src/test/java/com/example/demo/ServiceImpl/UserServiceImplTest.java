
package minishopper.Service.impl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import minishopper.dtos.AddressDto;
import minishopper.dtos.UserDto;
import minishopper.Entity.Address;
import minishopper.Entity.User;
import minishopper.exception.ResourceNotFoundException;
import minishopper.Repository.AddressRepository;
import minishopper.Repository.UserRepository;
import minishopper.Service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ContextConfiguration(classes = {UserServiceImpl.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
     
 @Autowired
   private UserService userService; 
    
 
    @Test
    void checkUserId()
    {
    	
    	String userId = "testUser";
    	User user = new User();
    	user.setUserId(userId);
    	user.setFirstName("Test User");
    	
  
    	when(userRepository.findByUserId(userId)).thenReturn(user);

    	        // Act

    	        User result = userService.checkUserId(userId);

    	        // Assert

    	        assertThat(result).isNotNull();

    	        assertThat(result.getUserId()).isEqualTo(userId);

    	        assertThat(result.getFirstName()).isEqualTo("Test User");

    	    }
 
    @Test

       void testSaveUser() {

           // Arrange

           UserDto userDto = new UserDto();

           userDto.setUserId("testUser");

           userDto.setFirstName("Test User");

           userDto.setPassword("plainPassword");

           AddressDto addressDto = new AddressDto();

           addressDto.setStreet("123 Test Street");

           userDto.setAddress(Collections.singletonList(addressDto));

           Address address = new Address();

           address.setStreet("123 Test Street");

           User user = new User();

           user.setUserId("testUser");

           user.setFirstName("Test User");

           user.setPassword("encodedPassword");

           when(modelMapper.map(userDto.getAddress().get(0), Address.class)).thenReturn(address);

           when(modelMapper.map(userDto, User.class)).thenReturn(user);

           when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

           when(userRepository.save(user)).thenReturn(user);

           when(addressRepository.save(address)).thenReturn(address);

           // Act

           User savedUser = userService.saveUser(userDto);

           // Assert

           assertThat(savedUser).isNotNull();

           assertThat(savedUser.getUserId()).isEqualTo("testUser");

           assertThat(savedUser.getFirstName()).isEqualTo("Test User");

          // assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");

       }
    @Test
    void testFetchUserDetailsById_UserNotFound() {
        // Arrange
        String userId = "nonexistentUser";
        when(userRepository.findByUserId(userId)).thenReturn(null);
        // Act & Assert
        assertThatThrownBy(() -> userService.fetchUserDetailsById(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");
    }
    @Test
    void testFetchUserDetailsById_Success() {
        // Arrange
        String userId = "existingUser";
        User user = new User();
        user.setUserId(userId);
        user.setFirstName("Test User");
        Address address1 = new Address();
        address1.setStreet("123 Test Street");
        Address address2 = new Address();
        address2.setStreet("456 Test Avenue");
        List<Address> allAddress = new ArrayList<>();
        allAddress.add(address1);
        allAddress.add(address2);
        AddressDto addressDto1 = new AddressDto();
        addressDto1.setStreet("123 Test Street");
        AddressDto addressDto2 = new AddressDto();
        addressDto2.setStreet("456 Test Avenue");
        List<AddressDto> allAddressDto = new ArrayList<>();
        allAddressDto.add(addressDto1);
        allAddressDto.add(addressDto2);
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setFirstName("Test User");
        userDto.setAddress(allAddressDto);
        // Mocking the UserRepository behavior
        when(userRepository.findByUserId(userId)).thenReturn(user);
        // Mocking the AddressRepository behavior
        when(addressRepository.findByUser(user)).thenReturn(allAddress);
        // Mocking the ModelMapper behavior
        when(modelMapper.map(address1, AddressDto.class)).thenReturn(addressDto1);
        when(modelMapper.map(address2, AddressDto.class)).thenReturn(addressDto2);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        // Act
        UserDto result = userService.fetchUserDetailsById(userId);
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getFirstName()).isEqualTo("Test User");
        assertThat(result.getAddress()).hasSize(2);
        assertThat(result.getAddress().get(0).getStreet()).isEqualTo("123 Test Street");
        assertThat(result.getAddress().get(1).getStreet()).isEqualTo("456 Test Avenue");
    }

   // @Test
    void testUpdateUser() throws ResourceNotFoundException {
        doNothing().when(addressRepository)
                .updateAddressById(anyInt(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                        Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        UserDto buildResult = UserDto.builder()
                .email("jane.doe@example.org")
                .firstName("Jane")
                .lastName("Doe")
                .password("iloveyou")
                .role("Role")
                .userId("42")
                .build();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any())).thenReturn(buildResult);
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user);
        String userId = "42";
        AddressDto addressDto = new AddressDto(1, "42 Main St", "42 Main St", "Street", "Oxford", "MD", "Pin Code");
        UserDto actualUpdateUserResult = userServiceImpl.updateUser(userId, addressDto);
        verify(addressRepository).updateAddressById(eq(1), eq("42 Main St"), eq("42 Main St"), eq("Oxford"), eq("Pin Code"),
                eq("MD"), eq("Street"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        assertEquals("42", actualUpdateUserResult.getUserId());
        assertEquals("Doe", actualUpdateUserResult.getLastName());
        assertEquals("Jane", actualUpdateUserResult.getFirstName());
        assertEquals("Role", actualUpdateUserResult.getRole());
        assertEquals("iloveyou", actualUpdateUserResult.getPassword());
        assertEquals("jane.doe@example.org", actualUpdateUserResult.getEmail());
        List<AddressDto> address = actualUpdateUserResult.getAddress();
        assertEquals(1, address.size());
        assertSame(addressDto, address.get(0));
    }
    /**
     * Method under test: {@link UserServiceImpl#updateUser(String, AddressDto)}
     */
  //  @Test
    void testUpdateUser2() throws ResourceNotFoundException {
        doNothing().when(addressRepository)
                .updateAddressById(anyInt(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                        Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<UserDto>>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setOrders(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUserId("42");
        when(userRepository.findByUserId(Mockito.<String>any())).thenReturn(user);
        String userId = "42";
        AddressDto addressDto = new AddressDto(1, "42 Main St", "42 Main St", "Street", "Oxford", "MD", "Pin Code");
        assertThrows(ResourceNotFoundException.class, () -> userServiceImpl.updateUser(userId, addressDto));
        verify(addressRepository).updateAddressById(eq(1), eq("42 Main St"), eq("42 Main St"), eq("Oxford"), eq("Pin Code"),
                eq("MD"), eq("Street"));
        verify(userRepository).findByUserId(eq("42"));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
    }
    /**
     * Method under test: {@link UserServiceImpl#addAddress(String, AddressDto)}
     */
    
    @Test
    void testUpdateUser_UserNotFound() {
        // Arrange
        String userId = "nonexistentUser";
        AddressDto addressDto = new AddressDto();
        when(userRepository.findByUserId(userId)).thenReturn(null);
        // Act & Assert
        assertThatThrownBy(() -> userService.updateUser(userId, addressDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");
    }
    @Test
    void testUpdateUser_Success() {
        // Arrange
        String userId = "existingUser";
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(1);
        addressDto.setAddressLine("New Address Line");
        addressDto.setAddressType("Home");
        addressDto.setCity("Test City");
        addressDto.setPinCode("12345");
        addressDto.setState("Test State");
        addressDto.setStreet("Test Street");
        User user = new User();
        user.setUserId(userId);
        user.setFirstName("Test User");
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setFirstName("Test User");
        List<AddressDto> listOfAddress = new ArrayList<>();
        listOfAddress.add(addressDto);
        userDto.setAddress(listOfAddress);
        // Mocking the UserRepository behavior
        when(userRepository.findByUserId(userId)).thenReturn(user);
        // Mocking the AddressRepository behavior
        doNothing().when(addressRepository).updateAddressById(
            addressDto.getAddressId(),
            addressDto.getAddressLine(),
            addressDto.getAddressType(),
            addressDto.getCity(),
            addressDto.getPinCode(),
            addressDto.getState(),
            addressDto.getStreet()
        );
        // Mocking the ModelMapper behavior
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        // Act
        UserDto result = userService.updateUser(userId, addressDto);
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getFirstName()).isEqualTo("Test User");
        assertThat(result.getAddress()).hasSize(1);
        assertThat(result.getAddress().get(0).getAddressLine()).isEqualTo("New Address Line");
        assertThat(result.getAddress().get(0).getAddressType()).isEqualTo("Home");
        assertThat(result.getAddress().get(0).getCity()).isEqualTo("Test City");
        assertThat(result.getAddress().get(0).getPinCode()).isEqualTo("12345");
        assertThat(result.getAddress().get(0).getState()).isEqualTo("Test State");
        assertThat(result.getAddress().get(0).getStreet()).isEqualTo("Test Street");
    }
 
   
    @Test
    void testAddAddress_UserNotFound() {
        // Arrange
        String userId = "nonexistentUser";
        AddressDto addressDto = new AddressDto();
        when(userRepository.findByUserId(userId)).thenReturn(null);
        // Act & Assert
        assertThatThrownBy(() -> userService.addAddress(userId, addressDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");
    }
    @Test
    void testAddAddress_Success() {
        // Arrange
        String userId = "existingUser";
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("123 Test Street");
        User user = new User();
        user.setUserId(userId);
        user.setFirstName("Test User");
        Address address = new Address();
        address.setStreet("123 Test Street");
        address.setUser(user);
        Address addedAddress = new Address();
        addedAddress.setStreet("123 Test Street");
        AddressDto addedAddressDto = new AddressDto();
        addedAddressDto.setStreet("123 Test Street");
        UserDto savedUser = new UserDto();
        savedUser.setUserId(userId);
        savedUser.setFirstName("Test User");
        savedUser.setAddress(Collections.singletonList(addedAddressDto));
        // Mocking the UserRepository behavior
        when(userRepository.findByUserId(userId)).thenReturn(user);
        // Mocking the ModelMapper behavior
        when(modelMapper.map(addressDto, Address.class)).thenReturn(address);
        when(modelMapper.map(addedAddress, AddressDto.class)).thenReturn(addedAddressDto);
        when(modelMapper.map(user, UserDto.class)).thenReturn(savedUser);
        // Mocking the AddressRepository behavior
        when(addressRepository.save(any(Address.class))).thenReturn(addedAddress);
        // Act
        UserDto result = userService.addAddress(userId, addressDto);
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getFirstName()).isEqualTo("Test User");
        assertThat(result.getAddress()).hasSize(1);
        assertThat(result.getAddress().get(0).getStreet()).isEqualTo("123 Test Street");
    }
 }

 
 