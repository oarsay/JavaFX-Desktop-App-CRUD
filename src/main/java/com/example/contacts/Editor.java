package com.example.contacts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Editor {
    public static String description;

    @FXML
    private Button btnChooseFile;

    @FXML
    private Button btnOk;

    @FXML
    private ImageView ivEditorIcon;

    @FXML
    private TextArea taDescription;

    @FXML
    void onChooseFileButtonClick(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        //fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        //System.out.println(file.getAbsolutePath());
        //description = Files.readString(Paths.get(file.toString() + ".txt"));

        //taDescription.setText(readFile(file));
        readFile(file);
    }

    private void readFile(File file) {

        taDescription.setText("");
        try {
            // Create a buffered stream
            Scanner input = new Scanner(file);

            // Read a line and append the line to the text area
            while (input.hasNext()) {
                String temp = input.nextLine();
                // single quotes in the postgresql queries broke the process
                // to escape single quotes, just add another one
                temp = temp.replaceAll("'", "''");
                taDescription.appendText(temp + '\n');
            }
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void onMouseEntered(MouseEvent event) {
        Main m = new Main();
        m.setCursorTo(Cursor.HAND);

        switch (((Control) event.getSource()).getId()) {
            case "btnChooseFile" -> btnChooseFile.setEffect(new DropShadow());
            case "btnOk" -> btnOk.setEffect(new DropShadow());
        }
    }

    @FXML
    void onMouseExited(MouseEvent event) {
        Main m = new Main();
        m.setCursorTo(Cursor.DEFAULT);

        switch (((Control) event.getSource()).getId()) {
            case "btnChooseFile" -> btnChooseFile.setEffect(null);
            case "btnOk" -> btnOk.setEffect(null);
        }
    }

    @FXML
    void onOkButtonClick(ActionEvent event) {
        // Save description text
        description = taDescription.getText();

        // Close the editor window
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize(){
        taDescription.setText(description);
    }

}
