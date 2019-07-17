package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import quiz.DialogWindows;
import quiz.Game;

import java.io.IOException;

public class GameScreenPlayController {

    private Game game;
    private String answer;
    private String category;

    @FXML
    private Button answer1Button;

    @FXML
    private Button answer2Button;

    @FXML
    private Button answer3Button;

    @FXML
    private Button answer4Button;

    @FXML
    private Label questionLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label roundLabel;

    @FXML
    private Button nextButton;

    public String getAnswer() {
        return answer;
    }

    public String getCategory() {
        return category;
    }


    @FXML
    void initialize(String category){
        game = new Game();
        roundLabel.setText("Runda: " + game.getRound());
        scoreLabel.setText("Wynik: " + game.getScore());
        nextButton.setDisable(true);
        this.category = category;

        // Initialization with first question
        if(category.equals("books")){
            game.getRandomQuestion(category, game.getRowsInBooks(), this);
        }
        else if(category.equals("people")){
            game.getRandomQuestion(category, game.getRowsInPeople(), this);
        }

    }

    public void setScoreLabelText(String score) {
        scoreLabel.setText(score);
    }

    public void setQuestionLabelText(String info) {
        questionLabel.setText(info);
    }


    @FXML
    void nextQuestion(ActionEvent event) throws IOException {

        // Set css of answer buttons for new round
        answer1Button.setDisable(false);
        answer1Button.setStyle("-fx-normal-background-color: linear-gradient(to top,#f39c12, #f1c40f); -fx-hover-background-color: linear-gradient(to top,#f39c12, #f1c40f);");

        answer2Button.setDisable(false);
        answer2Button.setStyle("-fx-normal-background-color: linear-gradient(to top,#f39c12, #f1c40f); -fx-hover-background-color: linear-gradient(to top,#f39c12, #f1c40f);");

        answer3Button.setDisable(false);
        answer3Button.setStyle("-fx-normal-background-color: linear-gradient(to top,#f39c12, #f1c40f); -fx-hover-background-color: linear-gradient(to top,#f39c12, #f1c40f);");

        answer4Button.setDisable(false);
        answer4Button.setStyle("-fx-normal-background-color: linear-gradient(to top,#f39c12, #f1c40f); -fx-hover-background-color: linear-gradient(to top,#f39c12, #f1c40f);");

        // Game has 5 round/questions
        if(game.getRound() < 5){

            if(category.equals("books")){
                game.getRandomQuestion(category, game.getRowsInBooks(), this);
            }
            else if(category.equals("people")){
                game.getRandomQuestion(category, game.getRowsInPeople(), this);
            }

            nextButton.setDisable(true);
            game.setRound(game.getRound() + 1);
            roundLabel.setText("Runda: " + game.getRound());

        }else if(game.getRound() == 5){
            game.saveScore();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuScreen.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }


    // Method for answers buttons. Set css for button with good or bad answer and call checkAnswer method from Game class.
    @FXML
    void answer(ActionEvent event) {
        Button b = (Button) event.getSource();
        nextButton.setDisable(false);
        answer = b.getText();

        if(game.getRound() < 5){
            boolean isCorrect = game.checkAnswer(answer, this);
            if(isCorrect){
                b.setStyle("-fx-background-color: linear-gradient(to top,green, #f1c40f);");
            }
            else{
                b.setStyle("-fx-background-color: linear-gradient(to top,red, #f1c40f);");
            }

        }else if(game.getRound() == 5){
            boolean isCorrect = game.checkAnswer(answer, this);
            if(isCorrect){
                b.setStyle("-fx-background-color: linear-gradient(to top,green, #f1c40f);");
            }
            else{
                b.setStyle("-fx-background-color: linear-gradient(to top,red, #f1c40f);");
            }
            nextButton.setPrefWidth(120);
            nextButton.setText("Powrót do menu");
        }

        answer1Button.setDisable(true);
        answer2Button.setDisable(true);
        answer3Button.setDisable(true);
        answer4Button.setDisable(true);
    }

    // Method to set questionLabel and answerButtons texts.
    public void showQuestion(String quote, String ans1, String ans2, String ans3, String ans4){
        if(getCategory().equals("books")){
            questionLabel.setText("Z jakiej książki pochodzi ten cytat?\n\n" + quote);
        }
        else if(getCategory().equals("people")) {
            questionLabel.setText("Kto to powiedział?\n\n" + quote);
        }

        answer1Button.setText(ans1);
        answer2Button.setText(ans2);
        answer3Button.setText(ans3);
        answer4Button.setText(ans4);
    }

    @FXML
    void exit() {
        DialogWindows.dialogExit();
    }
}
