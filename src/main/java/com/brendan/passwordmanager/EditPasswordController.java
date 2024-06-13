package com.brendan.passwordmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class EditPasswordController {

    @FXML private TextField currentUserName;

    @FXML private TextField currentId;

    @FXML
    private TextField currentName;

    @FXML
    private TextField currentPassword;

    @FXML
    private TextField currentUserId;

    @FXML
    private TextField currentDescription;

    @FXML
    private javafx.scene.control.Button cancelButton;

    /**
     * Save password after editing
     * @throws IOException
     * @throws SQLException
     */
    public void savePassword() throws IOException, SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        String userName = currentUserName.getText();

        if (DatabaseHandler.handler.savePassword(Integer.valueOf(currentId.getText()), currentName.getText(), currentUserId.getText(), currentPassword.getText(), currentDescription.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Password was Successfully Saved!");
            alert.setHeaderText("Success");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Edit the Password was Not Successful!");
            alert.setHeaderText("Fail!");
            alert.showAndWait();
        }
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
        //Set Welcome Message
        TextField userNameTxt = (TextField) root.lookup("#currentUserName");
        userNameTxt.setText(userName);

        // Display the stage
        newWindow.show();

    }


    /**
     * Cancel function
     * @throws IOException
     */
    public void cancel() throws IOException {

        String userName = currentUserName.getText();

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

        Stage newWindow = new Stage();
        newWindow.setTitle("Welcome " + userName);

        // Load the FXML document
        Parent root = FXMLLoader.load(getClass().getResource("myPasswords.fxml"));

        // Set the scene
        Scene scene = new Scene(root);
        newWindow.setScene(scene);

        //Set Welcome Message
        TextField userNameTxt = (TextField) root.lookup("#currentUserName");
        userNameTxt.setText(userName);

        // Display the stage
        newWindow.show();

    }


}

