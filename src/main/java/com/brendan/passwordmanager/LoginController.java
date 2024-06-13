package com.brendan.passwordmanager;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;



import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLException;


public class LoginController {

        @FXML
        private TextField password;

        @FXML
        private TextField welcome;

        @FXML
        private TextField userName;

        @FXML
        private TextField currentUserName;

        public void exitSystem() {
            System.exit(1);
        }

    /**
     * Logs user in with given id and password
     * @throws IOException
     * @throws SQLException
     */
        public void login() throws IOException, SQLException {
            if (DatabaseHandler.handler.checkLogin(userName.getText(), password.getText())) {
                System.out.println("Password is correct!");
                createNewWindow(userName.getText());
            } else {
                System.out.println("Bad!!!!!!!!!!");
            }
        }

    /**
     * Pop up welcome window
     * @param userName
     * @throws IOException
     */
    public void createNewWindow(String userName) throws IOException {
            // Create a new stage (window)
            Stage newWindow = new Stage();
            newWindow.setTitle("Welcome " + userName);

            // Load the FXML document
            Parent root = FXMLLoader.load(getClass().getResource("myPasswords.fxml"));

            // Set the scene
            Scene scene = new Scene(root);
            newWindow.setScene(scene);

        TextField currentUserNameTxt = (TextField) root.lookup("#currentUserName");
        currentUserNameTxt.setText(userName);

            // Display the stage
            newWindow.show();
        }
}