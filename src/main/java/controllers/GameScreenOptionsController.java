package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameScreenOptionsController {

    @FXML
    void switchToGameScreenPlayBooks(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameScreenPlay.fxml"));
        Parent root = loader.load();

        // Pass info about category to GameScreenPlayController
        GameScreenPlayController controller = loader.getController();
        controller.initialize("books");

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }


    @FXML
    void switchToGameScreenPlayPeople(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameScreenPlay.fxml"));
        Parent root = loader.load();

        // Pass info about category to GameScreenPlayController
        GameScreenPlayController controller = loader.getController();
        controller.initialize("people");

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    public void switchToMenuScreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuScreen.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
