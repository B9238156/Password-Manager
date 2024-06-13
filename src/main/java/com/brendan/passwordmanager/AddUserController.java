package com.brendan.passwordmanager;

import javafx.fxml.FXML;
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

public class AddUserController {

    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    @FXML
    private javafx.scene.control.Button cancelButton;

    /**
     * Add user to database for app login
     * @throws IOException
     * @throws SQLException
     */
    public void addUser() throws IOException, SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
            if (DatabaseHandler.handler.addUser(userName.getText(), password.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User was Successfully Added!");
                alert.setHeaderText("Success");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Adding the User was Not Successful!");
                alert.setHeaderText("Fail!!!!");
                alert.showAndWait();
            }


    }



    public void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
