
package com.example.demo.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
class BaseResponseTest {
    /**
     * Methods under test:
     * <ul>
     *   <li>{@link BaseResponse#BaseResponse()}
     *   <li>{@link BaseResponse#setMessage(String)}
     *   <li>{@link BaseResponse#setStatus(String)}
     *   <li>{@link BaseResponse#setStatusMessage(String)}
     *   <li>{@link BaseResponse#toString()}
     *   <li>{@link BaseResponse#getMessage()}
     *   <li>{@link BaseResponse#getStatus()}
     *   <li>{@link BaseResponse#getStatusMessage()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange and Act
        BaseResponse actualBaseResponse = new BaseResponse();
        actualBaseResponse.setMessage("Not all who wander are lost");
        actualBaseResponse.setStatus("Status");
        actualBaseResponse.setStatusMessage("Status Message");
        String actualToStringResult = actualBaseResponse.toString();
        String actualMessage = actualBaseResponse.getMessage();
        String actualStatus = actualBaseResponse.getStatus();
        // Assert that nothing has changed
        assertEquals("BaseResponse [status=Status, statusMessage=Status Message, message=Not all who wander are lost]",
                actualToStringResult);
        assertEquals("Not all who wander are lost", actualMessage);
        assertEquals("Status Message", actualBaseResponse.getStatusMessage());
        assertEquals("Status", actualStatus);
    }
    /**
     * Methods under test:
     * <ul>
     *   <li>{@link BaseResponse#BaseResponse(String, String, String)}
     *   <li>{@link BaseResponse#setMessage(String)}
     *   <li>{@link BaseResponse#setStatus(String)}
     *   <li>{@link BaseResponse#setStatusMessage(String)}
     *   <li>{@link BaseResponse#toString()}
     *   <li>{@link BaseResponse#getMessage()}
     *   <li>{@link BaseResponse#getStatus()}
     *   <li>{@link BaseResponse#getStatusMessage()}
     * </ul>
     */
    @Test
    void testGettersAndSetters2() {
        // Arrange and Act
        BaseResponse actualBaseResponse = new BaseResponse("Status", "Status Message", "Not all who wander are lost");
        actualBaseResponse.setMessage("Not all who wander are lost");
        actualBaseResponse.setStatus("Status");
        actualBaseResponse.setStatusMessage("Status Message");
        String actualToStringResult = actualBaseResponse.toString();
        String actualMessage = actualBaseResponse.getMessage();
        String actualStatus = actualBaseResponse.getStatus();
        // Assert that nothing has changed
        assertEquals("BaseResponse [status=Status, statusMessage=Status Message, message=Not all who wander are lost]",
                actualToStringResult);
        assertEquals("Not all who wander are lost", actualMessage);
        assertEquals("Status Message", actualBaseResponse.getStatusMessage());
        assertEquals("Status", actualStatus);
    }
}
 