package org.example.btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static org.example.btl.Config.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class GameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        URL fxmlUrl = Objects.requireNonNull(
                getClass().getResource("/org/example/btl/Menu.fxml"),
                "Cannot find Game.fxml"
        );

        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root, MAX_WIDTH, MAX_HEIGHT);

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
