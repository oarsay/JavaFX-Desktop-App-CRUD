package com.example.contacts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class SignUp {

    @FXML
    private Button btnSignUp;

    @FXML
    private Label lblAlreadyHaveAnAccount;

    @FXML
    private Label lblLoginMessag;

    @FXML
    private Label lblPasswor;

    @FXML
    private Label lblPasswor1;

    @FXML
    private Label lblUserI;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfPasswordConfirm;

    @FXML
    private TextField tfUserId;

    @FXML
    private Text txtSignU;

    @FXML
    void onGoToLogIn(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeSceneTo("LogIn.fxml");
    }

    @FXML
    void onMouseEntered(MouseEvent event) {
        Main m = new Main();
        m.setCursorTo(Cursor.HAND);
    }

    @FXML
    void onMouseExited(MouseEvent event) {
        Main m = new Main();
        m.setCursorTo(Cursor.DEFAULT);
    }

    @FXML
    void onSignUpButtonClick(ActionEvent event) {

    }

}
