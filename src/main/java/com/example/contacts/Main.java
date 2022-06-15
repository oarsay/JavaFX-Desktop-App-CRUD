package com.example.contacts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class Main implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, Integer> colPages;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, Integer> colYear;

    @FXML
    private TextField tfAuthor;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfPages;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfYear;

    @FXML
    private TableView<Book> tvBooks;

    @FXML
    void onDeleteButtonClick(ActionEvent event) throws URISyntaxException {
        if(!tfId.getText().equals("")){
            showDialog();
        }
    }

    @FXML
    void onInsertButtonClick(ActionEvent event) {
        if(!tfId.getText().equals("")){
            insertRecord();
            clearTextFields();
        }
    }

    @FXML
    void onUpdateButtonClick(ActionEvent event) {
        if(!tfId.getText().equals("")){
            updateRecord();
            clearTextFields();
        }

    }

    @FXML
    void onTableClick(MouseEvent event) throws IOException {
        Book book = tvBooks.getSelectionModel().getSelectedItem();

        tfId.setText("" + book.getId());
        tfTitle.setText(book.getTitle());
        tfAuthor.setText(book.getAuthor());
        tfYear.setText("" + book.getYear());
        tfPages.setText("" + book.getPages());

        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            onGoToBookDetail(book);
        }
    }

    private void onGoToBookDetail(Book book) throws IOException {
        BookDetail.book = book;
        Launcher main = new Launcher();
        main.changeSceneTo("BookDetail.fxml");
    }

    @FXML
    void onEmptyAreaClick(MouseEvent event) {
        clearTextFields();
        tvBooks.getSelectionModel().select(null);
    }

    @FXML
    void onMouseEntered(MouseEvent event) {
        Launcher m = new Launcher();
        m.setCursorTo(Cursor.HAND);

        switch (((Control) event.getSource()).getId()) {
            case "btnInsert" -> btnInsert.setEffect(new DropShadow());
            case "btnUpdate" -> btnUpdate.setEffect(new DropShadow());
            case "btnDelete" -> btnDelete.setEffect(new DropShadow());
        }
    }

    @FXML
    void onMouseExited(MouseEvent event) {
        Launcher m = new Launcher();
        m.setCursorTo(Cursor.DEFAULT);

        switch (((Control) event.getSource()).getId()) {
            case "btnInsert" -> btnInsert.setEffect(null);
            case "btnUpdate" -> btnUpdate.setEffect(null);
            case "btnDelete" -> btnDelete.setEffect(null);
        }
    }

    public Connection getConnection(){
        DbFunctions dbFunctions = new DbFunctions();
        return dbFunctions.connect_to_db("JavaDesktopAppDB", "postgres", "oelkapmis");
    }

    public ObservableList<Book> getBookList(){
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        Statement st;
        ResultSet rs;

        try {
            st = getConnection().createStatement();
            rs = st.executeQuery("SELECT * FROM \"Books\"");
            Book book;
            while(rs.next()){
                book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getInt("pages"),
                        rs.getString("description"));
                bookList.add(book);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookList;
    }

    public void showBookList(){
        ObservableList<Book> bookList = getBookList();
/*
        These are IDs of the TableView                          These are properties defined
        columns in the Main.fxml file.                          in the Book.java class.
          ||                                                                    ||
          ||                                                                    ||
          \/                                                                    \/
 */
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<>("pages"));

        tvBooks.setItems(bookList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBookList();
    }

    private void insertRecord(){

        String description;
        {
            description = """
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

                            At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.
                    """;
        }
        String query = "INSERT INTO \"Books\" VALUES ("
                + tfId.getText() + ",'"
                + tfTitle.getText() + "','"
                + tfAuthor.getText() + "',"
                + tfYear.getText() + ","
                + tfPages.getText() + ",'"
                + description + "')";
        executeQuery(query);
        showBookList();
    }

    private void updateRecord(){
        String query = "UPDATE \"Books\" SET title = '"
                + tfTitle.getText() + "', author = '"
                + tfAuthor.getText() + "', year = "
                + tfYear.getText() + ", pages = "
                + tfPages.getText()
                + "WHERE id = " + tfId.getText() + ";";
        executeQuery(query);
        showBookList();
    }

    private void deleteRecord(){
        String query = "DELETE from \"Books\" WHERE id = " + tfId.getText();
        executeQuery(query);
        showBookList();
    }

    private void showDialog() throws URISyntaxException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete record");
        alert.setHeaderText("Are you sure you want to delete this record?");
        String s ="This record will be deleted permanently... ";
        alert.setContentText(s);

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            deleteRecord();
            clearTextFields();
        }
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

    private void clearTextFields(){
        tfId.setText("");
        tfTitle.setText("");
        tfAuthor.setText("");
        tfYear.setText("");
        tfPages.setText("");
    }
}
