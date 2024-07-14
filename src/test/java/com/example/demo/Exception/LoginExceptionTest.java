package minishopper.Exception;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import minishopper.exception.LoginException;

public class LoginExceptionTest {

	@Test
	   public void testNoArgsConstructor() {
	       LoginException exception = new LoginException();
	       assertNotNull(exception);
	   }
	   @Test
	   public void testAllArgsConstructor() {
	       String status = "ERROR";
	       String message = "Invalid login attempt";
	       LoginException exception = new LoginException(status, message);
	       assertEquals(status, exception.getStatus());
	       assertEquals(message, exception.getMessage());
	   }
	   @Test
	   public void testSetMessage() {
	       String newMessage = "New error message";
	       LoginException exception = new LoginException();
	       exception.setMessage(newMessage);
	       assertEquals(newMessage, exception.getMessage());
	   }
	   @Test
	   public void testToString() {
	       String status = "ERROR";
	       String message = "Invalid login attempt";
	       LoginException exception = new LoginException(status, message);
	       String expected = "LoginException [status=" + status + ", message=" + message + "]";
	       assertEquals(expected, exception.toString());
	   }
	
	}
