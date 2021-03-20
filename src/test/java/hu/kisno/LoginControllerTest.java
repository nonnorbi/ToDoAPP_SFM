package hu.kisno;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class LoginControllerTest {
    @Mock
    Button loginButton;
    @Mock
    Label loginMassageLabel;
    @Mock
    TextField loginUsername;
    @Mock
    PasswordField loginPassword;
    @Mock
    Button loginSignUpButton;
    @Mock
    AnchorPane rootAPane;
    @InjectMocks
    LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testClose() {
        loginController.close(null);
    }

    @Test
    void testLoginSignUpButtonOnAction() {
        loginController.loginSignUpButtonOnAction(null);
    }

    @Test
    void testInitialize() {
        loginController.initialize(null, null);
    }

    @Test
    void testLoginButtonOnAction() {
        loginController.loginButtonOnAction(null);
    }

    @Test
    void testValidateLogin() {
        loginController.validateLogin();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme