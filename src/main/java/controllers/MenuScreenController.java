package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quiz.DialogWindows;
import quiz.Player;

import java.io.IOException;
import java.util.Optional;

public class MenuScreenController{

    @FXML
    void switchToGameScreen(ActionEvent event) throws IOException {

        // Player has to give his name to play
        if(Player.name == null){
            Optional<String> result = DialogWindows.dialogSetName();

            if (result.isPresent() && !result.get().equals("")){
                // Set player name
                Player player = new Player(result.get());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameScreenOptions.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }else{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameScreenOptions.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    void switchToBestScoresScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BestScoresScreen.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void switchToInfoScreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InfoScreen.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void exit() {
        DialogWindows.dialogExit();
    }


}
