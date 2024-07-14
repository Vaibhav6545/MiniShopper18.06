
package minishopper.security;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import minishopper.Service.CustomUserDetailsService;
import org.apache.catalina.connector.Response;
import org.apache.catalina.session.StandardSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
@ContextConfiguration(classes = {JwtAuthenticationFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class JwtAuthenticationFilterTest {
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private HandlerExceptionResolver handlerExceptionResolver;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private JwtHelper jwtHelper;
   
    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    }
   
    @Test
    void testDoFilterInternal2() throws ServletException, IOException {
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
        verify(request).getHeader(eq("Authorization"));
    }
   
    @Test
    void testDoFilterInternal3() throws ServletException, IOException, UsernameNotFoundException {
        when(jwtHelper.validateToken(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(true);
        when(jwtHelper.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(customUserDetailsService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getSession(anyBoolean())).thenReturn(new MockHttpSession());
        when(request.getRemoteAddr()).thenReturn("42 Main St");
        when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
        verify(request).getRemoteAddr();
        verify(request).getHeader(eq("Authorization"));
        verify(request).getSession(eq(false));
        verify(jwtHelper).getUsernameFromToken(eq("https://example.org/example"));
        verify(jwtHelper).validateToken(eq("https://example.org/example"), isA(UserDetails.class));
        verify(customUserDetailsService).loadUserByUsername(eq("janedoe"));
    }
    
    @Test
    void testDoFilterInternal4() throws ServletException, IOException, UsernameNotFoundException {
        when(jwtHelper.validateToken(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(false);
        when(jwtHelper.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(customUserDetailsService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        doNothing().when(filterChain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
        verify(request).getHeader(eq("Authorization"));
        verify(jwtHelper).getUsernameFromToken(eq("https://example.org/example"));
        verify(jwtHelper).validateToken(eq("https://example.org/example"), isA(UserDetails.class));
        verify(customUserDetailsService).loadUserByUsername(eq("janedoe"));
    }
  
    @Test
    void testDoFilterInternal5() throws ServletException, IOException, UsernameNotFoundException {
        when(jwtHelper.validateToken(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(true);
        when(jwtHelper.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(customUserDetailsService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
        when(handlerExceptionResolver.resolveException(Mockito.<HttpServletRequest>any(),
                Mockito.<HttpServletResponse>any(), Mockito.<Object>any(), Mockito.<Exception>any()))
                .thenReturn(new ModelAndView("View Name"));
        StandardSession standardSession = mock(StandardSession.class);
        //DefaultHeader<Object> header = new DefaultHeader<>();
      //  when(standardSession.getId()).thenThrow(new ExpiredJwtException(header, new DefaultClaims(), "An error occurred"));
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getSession(anyBoolean())).thenReturn(standardSession);
        when(request.getRemoteAddr()).thenReturn("42 Main St");
        when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(request).getRemoteAddr();
        verify(request).getHeader(eq("Authorization"));
        verify(request).getSession(eq(false));
        verify(jwtHelper).getUsernameFromToken(eq("https://example.org/example"));
        verify(jwtHelper).validateToken(eq("https://example.org/example"), isA(UserDetails.class));
        verify(customUserDetailsService).loadUserByUsername(eq("janedoe"));
        verify(standardSession).getId();
     //   verify(handlerExceptionResolver).resolveException(isA(HttpServletRequest.class), isA(HttpServletResponse.class),
         //       isNull(), isA(Exception.class));
    }
    
   @Test
    void testDoFilterInternal6() throws ServletException, IOException, UsernameNotFoundException {
        when(jwtHelper.validateToken(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(true);
        when(jwtHelper.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(customUserDetailsService.loadUserByUsername(Mockito.<String>any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
      //  DefaultHeader<Object> header = new DefaultHeader<>();
       // when(handlerExceptionResolver.resolveException(Mockito.<HttpServletRequest>any(),
        //        Mockito.<HttpServletResponse>any(), Mockito.<Object>any(), Mockito.<Exception>any()))
         //       .thenThrow(new ExpiredJwtException(header, new DefaultClaims(), "An error occurred"));
      //  StandardSession standardSession = mock(StandardSession.class);
      //  DefaultHeader<Object> header2 = new DefaultHeader<>();
      //  when(standardSession.getId()).thenThrow(new ExpiredJwtException(header2, new DefaultClaims(), "An error occurred"));
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
      //  when(request.getSession(anyBoolean())).thenReturn(standardSession);
        when(request.getRemoteAddr()).thenReturn("42 Main St");
        when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer https://example.org/example");
        Response response = new Response();
        FilterChain filterChain = mock(FilterChain.class);
     //   assertThrows(ExpiredJwtException.class,
     //           () -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));
   }
   
   @Test
    void testNewJwtAuthenticationFilter() {
        HandlerExceptionResolver exceptionResolver = mock(HandlerExceptionResolver.class);
        JwtAuthenticationFilter actualJwtAuthenticationFilter = new JwtAuthenticationFilter(exceptionResolver);
        Environment environment = actualJwtAuthenticationFilter.getEnvironment();
        assertTrue(((StandardServletEnvironment) environment).getConversionService() instanceof DefaultConversionService);
        assertTrue(environment instanceof StandardServletEnvironment);
        Map<String, Object> systemProperties = ((StandardServletEnvironment) environment).getSystemProperties();
   }
}
 
 