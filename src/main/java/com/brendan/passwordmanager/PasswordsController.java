package com.brendan.passwordmanager;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;


public class PasswordsController {
        @FXML private TableView<Passwords> tableView;
        @FXML private TableColumn<Passwords, String> id;
        @FXML private TableColumn<Passwords, String> appName;
        @FXML private TableColumn<Passwords, String> userId;
        @FXML private TableColumn<Passwords, String> password;
        @FXML private TableColumn<Passwords, String> description;

        @FXML private TextField currentUserName;
        @FXML private TextField currentID;
        @FXML private TextField currentName;
        @FXML private TextField currentUserId;
        @FXML private PasswordField currentPassword;
        @FXML private TextField currentDescription;

        @FXML
        private javafx.scene.control.Button exitButton;



        public void initialize() throws SQLException {
            //This only works for one user now but could be changed in the future by passing in the username of the person that signs in
            ObservableList<Passwords> listPasswords = DatabaseHandler.handler.getPasswords("brendan");

            // Bind data to TableView columns
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            appName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
            password.setCellValueFactory(new PropertyValueFactory<>("password"));
            password.setVisible(false);
            description.setCellValueFactory(new PropertyValueFactory<>("description"));

   
            tableView.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                    try {
                        editPassword();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            // Set data to TableView
            tableView.setItems(listPasswords);
            tableView.getSelectionModel().selectFirst();
            //tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }

    /**
     * Add Password
     * @throws IOException
     * @throws SQLException
     */
    public void addPassword () throws IOException, SQLException {
        // Create a new stage (window)
        Stage newWindow = new Stage();
        newWindow.setTitle("Add Password");

        String currentUser = (String)currentUserName.getText();
        // Load the FXML document
        Parent root = FXMLLoader.load(getClass().getResource("add-password.fxml"));

        // Set the scene
        Scene scene = new Scene(root);
        newWindow.setScene(scene);

        TextField currentUserNameTxt = (TextField) root.lookup("#currentUserName");
        currentUserNameTxt.setText(currentUser);

        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
        // Display the stage
        newWindow.show();
    }

    /**
     * Edit Password in database
     * @throws IOException
     * @throws SQLException
     */
    public void editPassword () throws IOException, SQLException {
         //Create a new stage (window)
        Stage newWindow = new Stage();
        newWindow.setTitle("Edit Password");

       String currentUser = (String)currentUserName.getText();
        // Load the FXML document
        Parent root = FXMLLoader.load(getClass().getResource("edit-password.fxml"));


        // Set the scene
        Scene scene = new Scene(root);
        newWindow.setScene(scene);

        TextField currentUserNameTxt = (TextField) root.lookup("#currentUserName");
        currentUserNameTxt.setText(currentUser);

        Passwords selectedRow = tableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            TextField idTxt = (TextField) root.lookup("#currentId");
            idTxt.setText(selectedRow.getId());

            TextField nameTxt = (TextField) root.lookup("#currentName");
            nameTxt.setText(selectedRow.getName());

            TextField useridTxt = (TextField) root.lookup("#currentUserId");
            useridTxt.setText(selectedRow.getUserId());

            TextField passwordTxt = (TextField) root.lookup("#currentPassword");
            passwordTxt.setText(selectedRow.getPassword());

            TextField descriptionTxt = (TextField) root.lookup("#currentDescription");
            descriptionTxt.setText(selectedRow.getDescription());

            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.close();
            // Display the stage
            newWindow.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please select a row first");
            alert.setHeaderText("Alert!");
            alert.showAndWait();
        }
    }

    /**
     * Delete password from database
     * @throws IOException
     * @throws SQLException
     */
    public void deletePassword () throws IOException, SQLException {
            String currentUser = (String)currentUserName.getText();

        Parent root = FXMLLoader.load(getClass().getResource("edit-password.fxml"));

        Passwords selectedRow = tableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            TextField idTxt = (TextField) root.lookup("#currentId");
            idTxt.setText(selectedRow.getId());

            Alert confirmAlert =
                    new Alert(Alert.AlertType.WARNING,
                            "Are you sure?",
                            ButtonType.OK,
                            ButtonType.CANCEL);
            confirmAlert.setTitle("Delete");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.get() == ButtonType.OK) {

                if (DatabaseHandler.handler.deletePassword(Integer.valueOf(selectedRow.getId()))) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Password was Successfully Deleted!");
                    alert.setHeaderText("Success");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Deleting the Password was Not Successful!");
                    alert.setHeaderText("Fail!");
                    alert.showAndWait();
                }
            }
        }


        String userName = currentUserName.getText();

       Stage stage = (Stage) currentUserName.getScene().getWindow();
       stage.close();
        // Create a new stage (window)
        Stage newWindow = new Stage();
        newWindow.setTitle("Welcome " + userName);


        // Load the FXML document
        Parent myPasswordsRoot = FXMLLoader.load(getClass().getResource("myPasswords.fxml"));


        // Set the scene
        Scene scene = new Scene(myPasswordsRoot);
        newWindow.setScene(scene);
        TextField userNameTxt = (TextField) myPasswordsRoot.lookup("#currentUserName");
        userNameTxt.setText(userName);
        // Display the stage
        newWindow.show();


    }


    public void exit() throws IOException {

        String userName = currentUserName.getText();

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();



    }

}
