
package com.example.demo.Service.impl;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.demo.Entity.LoginData;
import com.example.demo.Repository.LoginDataRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ContextConfiguration(classes = {LoginDataServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LoginDataServiceImplTest {
    @MockBean
    private LoginDataRepository loginDataRepository;
    @Autowired
    private LoginDataServiceImpl loginDataServiceImpl;
    /**
     * Method under test: {@link LoginDataServiceImpl#saveLoginData(LoginData)}
     */
    @Test
    void testSaveLoginData() {
        // Arrange
        LoginData loginData = new LoginData();
        loginData.setDate(LocalDate.of(1970, 1, 1));
        loginData.setLoginStatus("Login Status");
        loginData.setRecord(1);
        loginData.setTime(LocalTime.MIDNIGHT);
        loginData.setUserId("42");
        when(loginDataRepository.save(Mockito.<LoginData>any())).thenReturn(loginData);
        LoginData loginData2 = new LoginData();
        loginData2.setDate(LocalDate.of(1970, 1, 1));
        loginData2.setLoginStatus("Login Status");
        loginData2.setRecord(1);
        loginData2.setTime(LocalTime.MIDNIGHT);
        loginData2.setUserId("42");
        // Act
        LoginData actualSaveLoginDataResult = loginDataServiceImpl.saveLoginData(loginData2);
        // Assert
        verify(loginDataRepository).save(isA(LoginData.class));
        assertSame(loginData, actualSaveLoginDataResult);
    }
}
 