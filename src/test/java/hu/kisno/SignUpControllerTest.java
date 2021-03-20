package hu.kisno;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class SignUpControllerTest {
    @Mock
    Label registrationMassageLabel;
    @Mock
    TextField sgnUpFisrtName;
    @Mock
    TextField signUpLastName;
    @Mock
    TextField signUpUsername;
    @Mock
    PasswordField signUpPasswird;
    @Mock
    Button signUpButton;
    @Mock
    Label errorSignUpMassageLabel;
    @InjectMocks
    SignUpController signUpController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSignUpButtonOnAction() {
        signUpController.signUpButtonOnAction(null);
    }

    @Test
    void testClose() {
        signUpController.close(null);
    }
}
