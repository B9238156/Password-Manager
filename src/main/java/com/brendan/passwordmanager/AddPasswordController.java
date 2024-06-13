package com.brendan.passwordmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.jfr.Description;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.jar.Attributes;

public class AddPasswordController {


    @FXML
    private TextField currentUserName;

    @FXML
    private TextField Name;

    @FXML
    private TextField Password;

    @FXML
    private TextField ID;

    @FXML
    private TextField Description;

    @FXML
    private javafx.scene.control.Button cancelButton;

    /**
     * Add a password to database
     * @throws IOException
     * @throws SQLException
     */
    public void addPassword() throws IOException, SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        String userName = currentUserName.getText();
  //          if (DatabaseHandler.handler.addPassword("brendan", Name.getText(), ID.getText(), Password.getText(), Description.getText())) {
        if (DatabaseHandler.handler.addPassword(userName, Name.getText(), ID.getText(), Password.getText(), Description.getText())) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Password was Successfully Added!");
                alert.setHeaderText("Success");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Adding the Password was Not Successful!");
                alert.setHeaderText("Fail!");
                alert.showAndWait();
            }
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

            // Create a new stage (window)
            Stage newWindow = new Stage();
            newWindow.setTitle("Welcome " + currentUserName);

            // Load the FXML document
            Parent root = FXMLLoader.load(getClass().getResource("myPasswords.fxml"));

            // Set the scene
            Scene scene = new Scene(root);
            newWindow.setScene(scene);
            TextField userNameTxt = (TextField) root.lookup("#currentUserName");
            userNameTxt.setText(userName);

            // Display the stage
            newWindow.show();

    }


    /**
     * cancel action
     * @throws IOException
     */
    public void cancel() throws IOException {

        String userName = currentUserName.getText();

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        // Create a new stage (window)
        Stage newWindow = new Stage();
        newWindow.setTitle("Welcome " + userName);


        // Load the FXML document
        Parent root = FXMLLoader.load(getClass().getResource("myPasswords.fxml"));


        // Set the scene
        Scene scene = new Scene(root);
        newWindow.setScene(scene);
        TextField userNameTxt = (TextField) root.lookup("#currentUserName");
        userNameTxt.setText(userName);
        // Display the stage
        newWindow.show();
    }
}
