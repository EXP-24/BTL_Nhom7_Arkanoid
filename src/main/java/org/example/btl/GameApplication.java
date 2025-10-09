package org.example.btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class GameApplication extends Application {

    public static final double maxWidth = 800;
    public static final double maxHeight = 600;

    @Override
    public void start(Stage stage) throws IOException {

        URL fxmlUrl = Objects.requireNonNull(
                getClass().getResource("/org/example/btl/Game.fxml"),
                "Cannot find Game.fxml"
        );
        
        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root, maxWidth, maxHeight, Color.LIGHTSKYBLUE);

        URL iconUrl = Objects.requireNonNull(
                getClass().getResource("/org/example/btl/images/arkanoid_512.png"),
                "Cannot find arkanoid_512.png"
        );

        Image icon = new Image(iconUrl.toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Arkanoid!");
        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }
}
