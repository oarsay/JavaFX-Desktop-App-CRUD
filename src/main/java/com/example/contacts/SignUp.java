package com.example.contacts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class SignUp {

    @FXML
    private Button btnSignUp;

    @FXML
    private Label lblAlreadyHaveAnAccount;

    @FXML
    private Label lblLoginMessag;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblPasswordConfirm;

    @FXML
    private Label lblSignUpMessage;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblLogIn;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private PasswordField pfPasswordConfirm;

    @FXML
    private TextField tfUserId;

    @FXML
    private Text txtLogIn;

    @FXML
    void onGoToLogIn(MouseEvent event) throws IOException {
        Main main = new Main();
        main.changeSceneTo("LogIn.fxml");
    }

    @FXML
    void onMouseEntered(MouseEvent event) {
        Main m = new Main();
        m.setCursorTo(Cursor.HAND);

        switch (((Control) event.getSource()).getId()) {
            case "btnSignUp" -> btnSignUp.setEffect(new DropShadow());
            case "lblLogIn" -> lblLogIn.setEffect(new DropShadow());
        }
    }

    @FXML
    void onMouseExited(MouseEvent event) {
        Main m = new Main();
        m.setCursorTo(Cursor.DEFAULT);

        switch (((Control) event.getSource()).getId()) {
            case "btnSignUp" -> btnSignUp.setEffect(null);
            case "lblLogIn" -> lblLogIn.setEffect(null);
        }
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER)
            onSignUpButtonClick();
    }

    @FXML
    void onSignUpButtonClick() {

        if(Objects.equals(tfUserId.getText(), "") ||
                Objects.equals(pfPassword.getText(), "") ||
                Objects.equals(pfPasswordConfirm.getText(), "")){
            lblSignUpMessage.setText("All fields are required.");
        }
        else{
            try{
                User user = getUserFromDB(Integer.parseInt(tfUserId.getText()));

                if(user != null){
                    lblSignUpMessage.setText("This user ID is already taken.");
                }
                else if(!pfPassword.getText().equals(pfPasswordConfirm.getText())){
                    lblSignUpMessage.setText("Passwords do not match.");
                }
                else{
                    saveUserToDB(tfUserId.getText(), pfPassword.getText());

                    Main main = new Main();
                    main.changeSceneTo("Main.fxml");
                }
            } catch (NumberFormatException e) {
                lblSignUpMessage.setText("User ID must be an integer.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveUserToDB(String id, String password) {
        try {
            String query = "INSERT INTO public.\"Users\"(id, password) VALUES ("
                    + Integer.parseInt(id) + ", '"
                    + password + "');";
            executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private User getUserFromDB(int id){
        Statement st;
        ResultSet rs;
        User user = null;

        try {
            st = getConnection().createStatement();
            rs = st.executeQuery("SELECT * FROM public.\"Users\" " +
                    "WHERE id = " + id);

            while(rs.next()){
                user = new User(
                        rs.getInt("id"),
                        rs.getString("password"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public Connection getConnection(){
        DbFunctions dbFunctions = new DbFunctions();
        return dbFunctions.connect_to_db("JavaDesktopAppDB", "postgres", "oelkapmis");
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
