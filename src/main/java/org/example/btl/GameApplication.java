package org.example.btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {

    public static final double maxWidth = 600;
    public static final double maxHeight = 800;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/btl/Game.fxml"));
        Scene scene = new Scene(root,maxHeight, maxWidth, Color.LIGHTSKYBLUE);

        Image icon = new Image(getClass().getResource("/org/example/btl/images/arkanoid_512.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Arkanoid!");
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }
}
