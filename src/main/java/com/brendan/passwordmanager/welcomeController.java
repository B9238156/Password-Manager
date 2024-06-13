package com.brendan.passwordmanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class welcomeController {
    /**
     * Pop up addUser prompt
     * @throws IOException
     * @throws SQLException
     */
    public void addUser() throws IOException, SQLException {
        // Create a new stage (window)
        Stage newWindow = new Stage();
        newWindow.setTitle("Add User");

        // Load the FXML document
        Parent root = FXMLLoader.load(getClass().getResource("add-user.fxml"));

        // Set the scene
        Scene scene = new Scene(root);
        newWindow.setScene(scene);

        // Display the stage
        newWindow.show();
    }

    /**
     * Prompt to view passwords
     * @throws IOException
     * @throws SQLException
     */
    public void viewPasswords() throws IOException, SQLException {
        // Create a new stage (window)
        Stage newWindow = new Stage();
        newWindow.setTitle("My Passwords");

        // Load the FXML document
        Parent root = FXMLLoader.load(getClass().getResource("myPasswords.fxml"));

        // Set the scene
        Scene scene = new Scene(root);
        newWindow.setScene(scene);

        // Display the stage
        newWindow.show();
    }

}
