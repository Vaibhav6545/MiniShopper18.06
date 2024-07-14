package minishopper.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import minishopper.exception.ResourceNotFoundException;

class ResourceNotFoundExceptionTest {
    
    @Test
    void testNewResourceNotFoundException() {
        ResourceNotFoundException actualResourceNotFoundException = new ResourceNotFoundException();
        assertEquals("Resourcce Not Found On Server", actualResourceNotFoundException.getLocalizedMessage());
        assertEquals("Resourcce Not Found On Server", actualResourceNotFoundException.getMessage());
        assertNull(actualResourceNotFoundException.getCause());
        assertEquals(0, actualResourceNotFoundException.getSuppressed().length);
    }

   
    @Test
    void testNewResourceNotFoundException2() {
        ResourceNotFoundException actualResourceNotFoundException = new ResourceNotFoundException("An error occurred");
        assertEquals("An error occurred", actualResourceNotFoundException.getLocalizedMessage());
        assertEquals("An error occurred", actualResourceNotFoundException.getMessage());
        assertNull(actualResourceNotFoundException.getCause());
        assertEquals(0, actualResourceNotFoundException.getSuppressed().length);
    }
}
 