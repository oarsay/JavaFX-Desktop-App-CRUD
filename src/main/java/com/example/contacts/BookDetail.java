package com.example.contacts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookDetail {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView btnBack;

    @FXML
    private Label lblAuthor;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblTitle;

    public static Book book;

    @FXML
    void onBackButtonClick(MouseEvent event) throws IOException {
        Launcher main = new Launcher();
        main.changeSceneTo("Main.fxml");
    }
    @FXML
    void onMouseEntered(MouseEvent event) {
        Launcher m = new Launcher();
        m.setCursorTo(Cursor.HAND);
        btnBack.setEffect(new DropShadow());
    }

    @FXML
    void onMouseExited(MouseEvent event) {
        Launcher m = new Launcher();
        m.setCursorTo(Cursor.DEFAULT);
        btnBack.setEffect(null);
    }

    @FXML
    public void initialize() {
        lblTitle.setText(book.getTitle());
        lblAuthor.setText("by " + book.getAuthor());
        lblDescription.setText(book.getDescription());
        lblDescription.setAlignment(Pos.TOP_LEFT);
    }
}
