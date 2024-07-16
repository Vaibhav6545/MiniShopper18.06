package minishopper.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import minishopper.Entity.CartItem;
import minishopper.Entity.User;
import minishopper.Service.CartService;
import minishopper.Service.UserService;
import minishopper.dtos.AddItemToCartDto;
import minishopper.dtos.CartDto;
import minishopper.exception.InvalidInputException;
import minishopper.exception.ResourceNotFoundException;

@ContextConfiguration(classes = { CartController.class })
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CartControllerTest {
	@Autowired
	private CartController cartController;

	@MockBean
	private CartService cartService;

	@MockBean
	private UserService userService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void checkUserId() {
		String userId = "existingUser";
		User mockUser = new User();
		mockUser.setUserId(userId);
		when(userService.checkUserId(userId)).thenReturn(mockUser);
		boolean result = cartController.checkUserId(userId);
		assertTrue(result);
	}

	@Test
	public void testCheckUserId2() {
		String userId = "nonExistentUser";
		when(userService.checkUserId(userId)).thenReturn(null);
		boolean result = cartController.checkUserId(userId);
		assertFalse(result);
	}

	@Test

	public void testAddItemToCart_ValidUser() throws InvalidInputException, ResourceNotFoundException {
		String userId = "validUser";
		User mockUser = new User();
		mockUser.setUserId(userId);
		AddItemToCartDto mockItem = new AddItemToCartDto();
		CartDto mockCart = new CartDto();
		when(userService.checkUserId(userId)).thenReturn(mockUser);
		when(cartService.addItemToCart(userId, mockItem)).thenReturn(mockCart);
		ResponseEntity<CartDto> response = cartController.addItemToCart(userId, mockItem);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockCart, response.getBody());
	}

	@Test

	public void testAddItemToCart_InvalidUser() {
		String userId = "invalidUser";
		AddItemToCartDto mockItem = new AddItemToCartDto();
		when(userService.checkUserId(userId)).thenReturn(null);
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
			cartController.addItemToCart(userId, mockItem);

		});

		assertEquals("Invalid UserId !", exception.getMessage());
	}

	@Test
	public void testAddItemToCart_ResourceNotFound() throws InvalidInputException {

		String userId = "validUser";
		User mockUser = new User();
		mockUser.setUserId(userId);
		AddItemToCartDto mockItem = new AddItemToCartDto();
		when(userService.checkUserId(userId)).thenReturn(mockUser);
		when(cartService.addItemToCart(userId, mockItem)).thenThrow(new ResourceNotFoundException("Item not found"));
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			cartController.addItemToCart(userId, mockItem);
		});
		assertEquals("Item not found", exception.getMessage());

	}

	
	@Test

	public void testDeleteItem_Success() throws InvalidInputException, ResourceNotFoundException {
		String userId = "validUser";
		int itemId = 1;
		when(userService.checkUserId(userId)).thenReturn(new User());
		when(cartService.getCartItemById(itemId)).thenReturn(new CartItem());
		ResponseEntity<String> response = cartController.deleteItem(userId, itemId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Deleted Successfully", response.getBody());
		verify(cartService, times(1)).deleteItemFromCart(userId, itemId);

	}

	@Test
	public void testDeleteItem_InvalidUserId() {
		String userId = "invalidUser";
		int itemId = 1;
		when(userService.checkUserId(userId)).thenReturn(null);
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
			cartController.deleteItem(userId, itemId);
		});
		assertEquals("Invalid UserId !", exception.getMessage());
		verify(cartService, never()).deleteItemFromCart(anyString(), anyInt());
	}

	@Test
	public void testDeleteItem_InvalidItemId() {
		String userId = "validUser";
		int itemId = 0;
		when(userService.checkUserId(userId)).thenReturn(new User());
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {

			cartController.deleteItem(userId, itemId);
		});

		assertEquals("Invalid ItemId !", exception.getMessage());

		verify(cartService, never()).deleteItemFromCart(anyString(), anyInt());
	}

	@Test
	public void testDeleteItem_ItemNotFound() throws InvalidInputException {

		String userId = "validUser";
		int itemId = 1;
		when(userService.checkUserId(userId)).thenReturn(new User());

		when(cartService.getCartItemById(itemId)).thenReturn(null);
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			cartController.deleteItem(userId, itemId);

		});

		assertEquals("Cart Item Not Found", exception.getMessage());
		verify(cartService, never()).deleteItemFromCart(anyString(), anyInt());

	}

	@Test
	public void getCartByUserId() throws InvalidInputException, ResourceNotFoundException {

		String userId = "validUser";
		User mockUser = new User();
		mockUser.setUserId(userId);
		CartDto mockCart = new CartDto();
		when(userService.checkUserId(userId)).thenReturn(mockUser);
		when(cartService.fetCartbyUser(userId)).thenReturn(mockCart);
		ResponseEntity<CartDto> response = cartController.getCartByUserId(userId);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockCart, response.getBody());
	}

	@Test
	public void getCartByUserId2() {
		String userId = "invalidUser";
		when(userService.checkUserId(userId)).thenReturn(null);
		InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
			cartController.getCartByUserId(userId);
		});
		assertEquals("Invalid UserId !", exception.getMessage());
	}

	@Test
	public void getCartByUserId3() throws InvalidInputException {
		String userId = "validUser";
		User mockUser = new User();
		mockUser.setUserId(userId);
		when(userService.checkUserId(userId)).thenReturn(mockUser);
		when(cartService.fetCartbyUser(userId)).thenThrow(new ResourceNotFoundException("Cart not found"));
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			cartController.getCartByUserId(userId);
		});
		assertEquals("Cart not found", exception.getMessage());
	}
}