package com.example.contacts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogIn {

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUserId;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfUserId;

    @FXML
    void onLoginButtonClick(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException{
        Main main = new Main();
        if(true){
            main.changeSceneTo("Main.fxml");
        }
    }
}
