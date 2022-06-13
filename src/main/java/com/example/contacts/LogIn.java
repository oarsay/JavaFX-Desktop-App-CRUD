package com.example.contacts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

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
    void onLoginButtonClick(ActionEvent event) throws IOException, SQLException {
        checkLogin();
    }

    private void checkLogin() throws IOException, SQLException {
        boolean validation = false;

        if(Objects.equals(tfUserId.getText(), "") || Objects.equals(pfPassword.getText(), "")){
            System.out.println("Kullanici adi ve/veya sifre bos birakilamaz!");
        }
        else{
            try{
                User user = getUserFromDB(Integer.parseInt(tfUserId.getText()));

                if(user == null){
                    System.out.println("Kullanici bulunamadi!");
                }
                else if(user.getId() == Integer.parseInt(tfUserId.getText()) &&
                        user.getPassword().equals(pfPassword.getText())){
                    System.out.println("Giris basarili!");
                    validation = true;
                }
                else{
                    System.out.println("Yanlis kullanici adi ve/veya sifre!");
                }
            } catch (NumberFormatException e) {
                System.out.println("User ID, integer bir deger olmalidir!");
            }
        }

        if(validation){
            Main main = new Main();
            main.changeSceneTo("Main.fxml");
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
