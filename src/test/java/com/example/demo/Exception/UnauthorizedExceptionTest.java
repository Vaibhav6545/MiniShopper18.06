package minishopper.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import minishopper.exception.UnauthorizedException;

class UnauthorizedExceptionTest {
	
	@Test
	void testNewUnauthorizedException() {
		UnauthorizedException actualUnauthorizedException = new UnauthorizedException();
		assertEquals("Unauthorized", actualUnauthorizedException.getLocalizedMessage());
		assertEquals("Unauthorized", actualUnauthorizedException.getMessage());
		assertNull(actualUnauthorizedException.getCause());
		assertEquals(0, actualUnauthorizedException.getSuppressed().length);
	}

	@Test
	void testNewUnauthorizedException2() {
	    // Arrange and Act
	    UnauthorizedException actualUnauthorizedException = new UnauthorizedException("An error occurred");

	    // Assert
	    assertEquals("An error occurred", actualUnauthorizedException.getMessage());
	    assertNull(actualUnauthorizedException.getCause());
	    assertEquals(0, actualUnauthorizedException.getSuppressed().length);
	}
}
