package com.brendan.passwordmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class    PasswordApplication extends Application {
    public static DatabaseHandler handler;

    /**
     * Start Application
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PasswordApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 425, 240);
        stage.setTitle("Brendan's IT");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args)  {
        handler = DatabaseHandler.getHandler();
        loadData();
        launch();
    }
    
    /**
     * Pre-Load the user database with one record
     * Future would allow multiple users
     */
    public static void loadData()  {
        handler.addUser("brendan", "1234") ;
    }
}
