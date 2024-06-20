package com.example.demo.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.demo.exception.LoginException;
class LoginExceptionTest {
    /**
     * Methods under test:
     * <ul>
     *   <li>{@link LoginException#LoginException()}
     *   <li>{@link LoginException#setMessage(String)}
     *   <li>{@link LoginException#setStatus(String)}
     *   <li>{@link LoginException#toString()}
     *   <li>{@link LoginException#getMessage()}
     *   <li>{@link LoginException#getStatus()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        LoginException actualLoginException = new LoginException();
        actualLoginException.setMessage("An error occurred");        actualLoginException.setStatus("Status");
        String actualToStringResult = actualLoginException.toString();
        String actualMessage = actualLoginException.getMessage();
        // Assert that nothing has changed
        assertEquals("An error occurred", actualMessage);
        assertEquals("LoginException [status=Status, message=An error occurred]", actualToStringResult);
        assertEquals("Status", actualLoginException.getStatus());
    }
    /**
     * Methods under test:
     * <ul>
     *   <li>{@link LoginException#LoginException(String, String)}
     *   <li>{@link LoginException#setMessage(String)}
     *   <li>{@link LoginException#setStatus(String)}
     *   <li>{@link LoginException#toString()}
     *   <li>{@link LoginException#getMessage()}
     *   <li>{@link LoginException#getStatus()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange and Act
        LoginException actualLoginException = new LoginException("Status", "An error occurred");
        actualLoginException.setMessage("An error occurred");
        actualLoginException.setStatus("Status");
        String actualToStringResult = actualLoginException.toString();
        String actualMessage = actualLoginException.getMessage();
        // Assert that nothing has changed
        assertEquals("An error occurred", actualMessage);
        assertEquals("LoginException [status=Status, message=An error occurred]", actualToStringResult);
        assertEquals("Status", actualLoginException.getStatus());
    }
}
 