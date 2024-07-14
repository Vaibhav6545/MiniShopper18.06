package minishopper.security;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtAuthenticationEntryPoint.class})
@ExtendWith(SpringExtension.class)
class JwtAuthenticationEntryPointTest {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    
    @Test
    void testCommence() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponseWrapper response = mock(HttpServletResponseWrapper.class);
        doNothing().when(response).sendError(anyInt(), Mockito.<String>any());
        AccountExpiredException authException = new AccountExpiredException("Msg");
        jwtAuthenticationEntryPoint.commence(request, response, authException);
        verify(response).sendError(eq(401), eq("JWT token Expired"));
    }
}
 