package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import quiz.Game;
import quiz.Score;

import java.io.IOException;
import java.util.List;

public class BestScoresScreenController {

    @FXML
    private Label bestScoresLabel;

    @FXML
    void initialize(){
        Game game = new Game();
        List<Score> scores = game.showBestScores();
        System.out.println(scores);

        if(scores.isEmpty()){
            bestScoresLabel.setText("\nBrak wyników");
        }
        else{
            int i = 1;
            for(Score score: scores){
                bestScoresLabel.setText(bestScoresLabel.getText() + "\n" + i + ".   " + score.getScore() + "  -  " + score.getName());
                i++;
            }
        }
    }

    public void switchToMenuScreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuScreen.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
