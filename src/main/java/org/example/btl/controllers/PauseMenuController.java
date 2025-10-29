package org.example.btl.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static org.example.btl.Config.*;


public class PauseMenuController {
    @FXML
    private ImageView restartButtom;

    @FXML
    private  ImageView resumeButtom;

    @FXML
    private ImageView quitButtom;

    private Image restartButtomImage,restartButtomHover;
    private Image resumeButtomImage,resumeButtomHover;
    private Image quitButtomImage,quitButtomHover;
    private Image mouseImage;
    private GameController gameController;

    @FXML
    public void initialize() {
        mouseImage = loadImage("mouse");
        Platform.runLater(() -> {
            resumeButtom.getScene().setCursor(new ImageCursor(mouseImage));
        });
        restartButtomImage = loadImage("restart");
        restartButtomHover = loadImage("restartHover");
        quitButtomImage = loadImage("quit");
        quitButtomHover = loadImage("quitHover");
        resumeButtomImage = loadImage("resume");
        resumeButtomHover = loadImage("resumeHover");

        setHoverEffect(resumeButtom, resumeButtomImage, resumeButtomHover);
        setHoverEffect(quitButtom, quitButtomImage, quitButtomHover);
        setHoverEffect(restartButtom, restartButtomImage, restartButtomHover);

        restartButtom.setOnMouseClicked(e-> onRestart());
        quitButtom.setOnMouseClicked(e -> onQuit());
        resumeButtom.setOnMouseClicked(e -> onResume());

    }


    private Image loadImage(String filename) {
        return new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/btl/images/texts/" + filename + ".png")));
    }

    /** Hiệu ứng đổi hình khi hover */
    private void setHoverEffect(ImageView button, Image normal, Image hover) {
        button.setOnMouseEntered(e -> button.setImage(hover));
        button.setOnMouseExited(e -> button.setImage(normal));
    }
    private void onRestart(){
        try{
            Parent gameRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Game.fxml")));
            Stage stage = (Stage) restartButtom.getScene().getWindow();
            stage.setScene(new Scene(gameRoot,MAX_WIDTH,MAX_HEIGHT));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onQuit(){
        try{
            Parent gameRoot = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/org/example/btl/Menu.fxml")));
            Stage stage = (Stage) restartButtom.getScene().getWindow();
            stage.setScene(new Scene(gameRoot,MAX_WIDTH,MAX_HEIGHT));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    private void onResume() {
        gameController.resumeGame();
    }
}